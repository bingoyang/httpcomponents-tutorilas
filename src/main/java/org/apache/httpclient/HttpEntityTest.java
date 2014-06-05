package org.apache.httpclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpEntityTest {

    public static void main(String[] args) throws IOException {
        testUrlEncodedFormEntity();
    }

    public static void testStringEntity() throws IOException {
        StringEntity myEntity = new StringEntity("important message", ContentType.create("text/plain", "UTF-8"));
        System.out.println(myEntity.getContentType());
        System.out.println(myEntity.getContentLength());
        System.out.println(EntityUtils.toString(myEntity));
        System.out.println(EntityUtils.toByteArray(myEntity).length);
    }

    public static void testUrlEncodedFormEntity() throws IOException {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", "zhangsan"));
        params.add(new BasicNameValuePair("password", "123456"));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, Consts.UTF_8);
        System.out.println(EntityUtils.toString(entity));
    }

    public static void testBasicManagedEntity() throws IOException {

    }
}
