package com.huanke.sshshell.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpUtils {

    private static final ThreadLocal<HttpClient> clients = new ThreadLocal<HttpClient>();

    public static String get(String url, Map<String, String> param,
            Map<String, String> headers) {
        HttpClient httpClient = getClient();
        // HttpClient httpClient = new DefaultHttpClient();
        HttpGet get = null;
        get = new HttpGet(url);
        appendHeaders(get, headers);
        appendParams(get, param);
        try {
            HttpResponse httpResponse = httpClient.execute(get);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = httpResponse.getEntity();
                return EntityUtils.toString(entity);
            } else {
                httpResponse.getEntity().getContent().close();
                System.out.println("return status code:"
                        + httpResponse.getStatusLine().getStatusCode());
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long e = System.currentTimeMillis();
        return null;
    }

    private static HttpClient getClient() {
        HttpClient hc = clients.get();
        if (hc == null) {
            hc = ThreadSafeHttpclientGetter.getNewInstance(1000, 5000, 40000);
            clients.set(hc);
        }
        return hc;
    }

    private static void appendHeaders(HttpRequestBase get,
            Map<String, String> headers) {
        if (headers != null) {
            for (Entry<String, String> e : headers.entrySet()) {
                get.addHeader(e.getKey(), e.getValue());
            }
        }
    }

    private static void appendParams(HttpRequestBase get,
            Map<String, String> params) {
        if (params != null) {
            HttpParams hp = new BasicHttpParams();
            for (Entry<String, String> e : params.entrySet()) {
                hp.setParameter(e.getKey(), e.getValue());
            }
            get.setParams(hp);
        }
    }

    public static String doPost(String url, Map<String, String> param,
            Map<String, String> headers, String body) {
        long s = System.currentTimeMillis();
        HttpClient httpClient = getClient();// ThreadSafeHttpclientGetter.getHttpClient();
        // HttpClient httpClient = new DefaultHttpClient();
        BufferedReader in = null;
        HttpPost post = null;
        System.out.println("url:" + url);
        System.out.println("content :" + body);
        post = new HttpPost(url);
        appendHeaders(post, headers);
        appendParams(post, param);
        post.setHeader("Content-Type", "application/json;charset=UTF-8");
        // System.out.println("headers:"+Arrays.toString(post.getAllHeaders()));
        if (body != null) {
            post.setEntity(new StringEntity(body, HTTP.UTF_8)); // 设置字符集的状态，不设置会出现乱码
        }
        // 添加要传递的参数 NameValuePair
        // 使用HttpPost对象来设置UrlEncodedFormEntity的Entity
        try {
            HttpResponse response = null;
            response = httpClient.execute(post);
            in = new BufferedReader(new InputStreamReader(response.getEntity()
                    .getContent()));

            StringBuffer string = new StringBuffer("");
            String lineStr = "";
            while ((lineStr = in.readLine()) != null) {
                string.append(lineStr + "\n");
            }
            String resultStr = string.toString();
            return resultStr;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
