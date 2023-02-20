package com.example.fukuyoshi.service.callback;

public interface ResponseCallback {
    void onSuccess(String response);
    void onFailed(String error);
}
