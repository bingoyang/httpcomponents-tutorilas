package org.apache.httpclient;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.BasicClientConnectionManager;
import org.apache.http.impl.conn.PoolingClientConnectionManager;

public class HttpConnectionManager {

    public static void main(String[] args) throws InterruptedException, IOException {
        testBasicClientConnectionManager();
    }

    public static void testBasicClientConnectionManager() throws InterruptedException, IOException {
        Scheme http = new Scheme("http", 80, PlainSocketFactory.getSocketFactory());
        SchemeRegistry sr = new SchemeRegistry();
        sr.register(http);
        ClientConnectionManager connMrg = new BasicClientConnectionManager(sr);

        // Request new connection. This can be a long process
        ClientConnectionRequest connRequest = connMrg.requestConnection(new HttpRoute(new HttpHost("localhost", 80)),
                null);

        // Wait for connection up to 10 sec
        ManagedClientConnection conn = connRequest.getConnection(10, TimeUnit.SECONDS);
        try {
            // Do useful things with the connection.
            // Release it when done.
            conn.releaseConnection();
        } catch (IOException ex) {
            // Abort connection upon an I/O error.
            conn.abortConnection();
            throw ex;
        }
    }

    public static void testPoolingClientConnectionManager() throws ClientProtocolException, IOException {
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
        schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));

        PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
        // Increase max total connection to 200
        cm.setMaxTotal(200);
        // Increase default max connection per route to 20
        cm.setDefaultMaxPerRoute(20);
        // Increase max connections for httpHost:80 to 50
        HttpHost httpHost = new HttpHost("http://www.suning.com",80);
        cm.setMaxPerRoute(new HttpRoute(httpHost), 50);
        HttpGet httpGet = new HttpGet("http://www.suning.com");
        HttpClient httpClient = new DefaultHttpClient(cm);

        HttpResponse httpResponse = httpClient.execute(httpGet);
        System.out.println(httpResponse.getStatusLine());
    }

}
