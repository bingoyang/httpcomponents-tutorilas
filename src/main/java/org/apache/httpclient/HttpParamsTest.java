package org.apache.httpclient;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParamBean;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParamBean;
import org.apache.http.util.EntityUtils;

public class HttpParamsTest {

    public static void main(String[] args) {
        try {
            userHttpConnectionParam();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testCoreProtocolPNames() {
        HttpParams params = new BasicHttpParams();
        HttpProtocolParamBean paramsBean = new HttpProtocolParamBean(params);
        paramsBean.setVersion(HttpVersion.HTTP_1_1);
        paramsBean.setContentCharset("UTF-8");
        paramsBean.setUseExpectContinue(true);

        System.out.println(params.getParameter(CoreProtocolPNames.PROTOCOL_VERSION));
        System.out.println(params.getParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET));
        System.out.println(params.getParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE));
        System.out.println(params.getParameter(CoreProtocolPNames.USER_AGENT));
    }

    public static void testCoreConnectionPNames() {
        HttpParams params = new BasicHttpParams();
        HttpConnectionParamBean paramsBean = new HttpConnectionParamBean(params);
        paramsBean.setConnectionTimeout(10);
        paramsBean.setSoTimeout(10);
        paramsBean.setTcpNoDelay(true);
        paramsBean.setSocketBufferSize(1024);

        System.out.println(params.getParameter(CoreConnectionPNames.CONNECTION_TIMEOUT));
        System.out.println(params.getParameter(CoreConnectionPNames.SO_TIMEOUT));
        System.out.println(params.getParameter(CoreConnectionPNames.TCP_NODELAY));
        System.out.println(params.getParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE));
    }

    public static void userHttpConnectionParamBean() throws IOException {
        HttpParams params = new BasicHttpParams();
        HttpConnectionParamBean paramsBean = new HttpConnectionParamBean(params);
        paramsBean.setConnectionTimeout(10000);
        paramsBean.setSoTimeout(1000);
        paramsBean.setTcpNoDelay(true);
        paramsBean.setSocketBufferSize(1024);

        HttpClient httpClient = new DefaultHttpClient(params);

        HttpGet httpGet = new HttpGet("http://www.baidu.com");
        HttpResponse httpResponse = httpClient.execute(httpGet);
        System.out.println(EntityUtils.toString(httpResponse.getEntity()));
    }

    public static void userHttpConnectionParam() throws IOException {

        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 1000);
        httpClient.getParams().setParameter(CoreConnectionPNames.TCP_NODELAY, true);
        httpClient.getParams().setParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, 1024);

        HttpGet httpGet = new HttpGet("http://www.baidu.com");
        HttpResponse httpResponse = httpClient.execute(httpGet);
        System.out.println(EntityUtils.toString(httpResponse.getEntity()));
    }
}
