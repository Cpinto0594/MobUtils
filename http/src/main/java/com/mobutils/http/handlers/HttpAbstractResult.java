package com.mobutils.http.handlers;

import com.mobutils.http.headers.HttpHeaders;

import java.util.HashMap;

//import com.fasterxml.jackson.core.type.TypeReference;


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
