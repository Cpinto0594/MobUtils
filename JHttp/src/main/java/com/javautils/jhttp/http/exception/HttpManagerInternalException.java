package com.javautils.jhttp.http.exception;

import com.mobutils.http.request.HttpManagerUtils;

import java.net.HttpURLConnection;

/**
 * Created by carlos on 2/04/18.
 */

public class HttpManagerInternalException extends Exception {
    private static int code = HttpURLConnection.HTTP_INTERNAL_ERROR;

    public HttpManagerInternalException(String mess) {
        super(message(mess));
    }

    public HttpManagerInternalException(Throwable cause) {
        super(message(cause));
    }

    static String message(String cause) {
        return HttpManagerUtils.formatMessage(HttpManagerUtils.ERROR_TEMPLATE, code, cause);
    }

    static String message(Throwable cause) {
        String message = cause.getCause() == null ? cause.getMessage() : cause.getCause().getMessage();
        return HttpManagerUtils.formatMessage(HttpManagerUtils.ERROR_TEMPLATE, code, message);
    }
}
