package org.apache.httpclient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class HttpContextTest {

    public static void main(String[] args) throws IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpGet httpget = new HttpGet("http://www.google.com/");
        HttpResponse response = httpClient.execute(httpget, localContext);
        HttpHost target = (HttpHost) localContext.getAttribute(ExecutionContext.HTTP_TARGET_HOST);

        HttpResponse httpResponse = (HttpResponse) localContext.getAttribute(ExecutionContext.HTTP_RESPONSE);

        System.out.println("Final target: " + target);
        HttpEntity entity = response.getEntity();
        EntityUtils.consume(entity);
        System.out.println(httpResponse);

    }
}
