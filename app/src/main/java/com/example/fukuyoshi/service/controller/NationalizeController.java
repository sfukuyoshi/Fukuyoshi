package com.example.fukuyoshi.service.controller;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.fukuyoshi.R;
import com.example.fukuyoshi.constant.Constant;
import com.example.fukuyoshi.model.Nationalize;
import com.example.fukuyoshi.service.builder.RequestBuilder;
import com.example.fukuyoshi.service.callback.ResponseCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NationalizeController {

    public void predictName(Context context, String name, Nationalize.NationalizeInterface nationalizeInterface) {

        if (TextUtils.isEmpty(name)) {
            nationalizeInterface.onError(context.getResources().getString(R.string.error_empty_name));
        } else {
            Uri uri = Uri.parse(Constant.NATIONALIZE_API)
                    .buildUpon()
                    .appendQueryParameter("name", name)
                    .build();

            RequestBuilder builder = new RequestBuilder();
            builder.getRequest(context, uri.toString(), new ResponseCallback() {
                @Override
                public void onSuccess(String response) {
                    new Nationalize(response, nationalizeInterface);
                }

                @Override
                public void onFailed(String error) {
                    nationalizeInterface.onError(error);
                }
            });
        }

    }
}
