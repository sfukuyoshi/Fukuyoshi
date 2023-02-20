package com.example.fukuyoshi.service.builder;

import android.content.Context;

import com.example.fukuyoshi.service.callback.RequestCallback;
import com.example.fukuyoshi.service.callback.ResponseCallback;

import org.chromium.net.CronetEngine;
import org.chromium.net.UrlRequest;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RequestBuilder {

    public void getRequest(Context context, String url, ResponseCallback responseCallback) {
        CronetEngine.Builder myBuilder = new CronetEngine.Builder(context);
        CronetEngine cronetEngine = myBuilder.build();
        Executor executor = Executors.newSingleThreadExecutor();
        UrlRequest.Builder requestBuilder = cronetEngine.newUrlRequestBuilder(
                    url, new RequestCallback(responseCallback), executor);
        UrlRequest request = requestBuilder.build();
        request.start();
    }
}
