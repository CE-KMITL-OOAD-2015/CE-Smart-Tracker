package com.kaleido.cesmarttracker;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

/**
 * Created by pirushprechathavanich on 10/24/15.
 */
public class ConnectHttp {
    private static final String BASE_URL = "http://203.151.92.177:8080/";
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static SyncHttpClient clientSync = new SyncHttpClient();

    public static void setAuthen(String username, String password) {
        client.setBasicAuth(username, password);
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void delete(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.delete(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static void getSync(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        clientSync.get(getAbsoluteUrl(url), params, responseHandler);
    }

}
