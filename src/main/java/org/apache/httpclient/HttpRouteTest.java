package org.apache.httpclient;

import java.io.IOException;
import java.net.ProxySelector;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.ProxySelectorRoutePlanner;
import org.apache.http.protocol.HttpContext;

public class HttpRouteTest {
    public static void main(String[] args) throws Exception {
        proxy();
    }

    public static void test() throws ClientProtocolException, IOException {
        HttpGet httpGet = new HttpGet("http://www.baidu.com");
        DefaultHttpClient httpclient = new DefaultHttpClient();
        ProxySelectorRoutePlanner routePlanner = new ProxySelectorRoutePlanner(httpclient.getConnectionManager()
                .getSchemeRegistry(), ProxySelector.getDefault());
        httpclient.setRoutePlanner(routePlanner);
        HttpResponse response = httpclient.execute(httpGet);
        System.out.println(response.getStatusLine());
    }

    public static void routeByProxy() throws ClientProtocolException, IOException {
        HttpGet httpGet = new HttpGet("http://www.baidu.com");

        DefaultHttpClient httpclient = new DefaultHttpClient();
        httpclient.setRoutePlanner(new HttpRoutePlanner() {

            public HttpRoute determineRoute(HttpHost target, HttpRequest request, HttpContext context)
                    throws HttpException {
                return new HttpRoute(target, null, new HttpHost("10.23.21.202", 7812), false);
            }

        });
        HttpResponse response = httpclient.execute(httpGet);
        System.out.println(response.getStatusLine());
    }

    public static void proxy() throws ClientProtocolException, IOException {
        HttpGet httpGet = new HttpGet("http://www.baidu.com");

        DefaultHttpClient httpclient = new DefaultHttpClient();

        HttpHost proxy = new HttpHost("10.23.21.202", 7812);
        httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        HttpResponse response = httpclient.execute(httpGet);
        System.out.println(response.getStatusLine());
    }

}
