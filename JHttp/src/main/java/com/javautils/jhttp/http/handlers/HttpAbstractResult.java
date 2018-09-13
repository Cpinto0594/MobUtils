package com.javautils.jhttp.http.handlers;

import  com.javautils.jhttp.http.headers.HttpHeaders;

import java.util.HashMap;



/**
 * Created by CarlosP on 24/5/2017.
 */

public abstract class HttpAbstractResult<Result> implements HttpResultListeners<Result> {

    protected HttpAbstractResult() {
    }


    @Override
    public boolean onBeforeSend(HashMap<String, String> requestHheaders) {
        return true;
    }

    @Override
    public void onError(Exception excp, HttpHeaders responseHeaders) {
        throw new RuntimeException(excp);
    }

    @Override
    public void onSuccess(Result resultValue, HttpHeaders responseHeaders, int responseCode) {

    }
}
