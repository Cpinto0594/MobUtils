package com.javautils.jhttp.http.handlers;

import  com.javautils.jhttp.http.headers.HttpHeaders;

import java.util.HashMap;

/**
 * Created by CarlosP on 24/5/2017.
 */

public interface HttpResultListeners<Result> {

    public void onSuccess(final Result resultValue, HttpHeaders responseHeaders, int responseCode);

    public void onError(Exception excp, HttpHeaders responseHeaders);

    public boolean onBeforeSend(HashMap<String, String> requestHheaders);

}
