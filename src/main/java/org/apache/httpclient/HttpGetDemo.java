package org.apache.httpclient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpGetDemo {

    public static void main(String[] args) throws Exception {
        testHttpGet();
    }

    public static void testFluent() throws Exception {
        Content content = Request.Get("http://www.suning.com").execute().returnContent();
        System.out.println(content.asString());
    }

    public static void testHttpGet() throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://www.suning.com");
        HttpResponse response = httpClient.execute(httpGet);
//        HttpEntity entity = response.getEntity();
        System.out.println(response.getStatusLine());
//        String str = EntityUtils.toString(entity);
//        System.out.println(str);
        httpClient.getConnectionManager().shutdown();
    }

    public static void testURLBuilder() throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("http").setHost("www.suning.com").setParameter("username", "test");
        URI uri = uriBuilder.build();
        System.out.println(uri.toASCIIString());
    }

    public static void testResponseHandler() throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://www.suning.com");
        String responseString = httpClient.execute(httpGet, new ResponseHandler<String>() {

            public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                return EntityUtils.toString(response.getEntity());
            }
        });
        System.out.println(responseString);
    }
}
