package com.mobutils.http.responses;

import java.io.InputStream;

/**
 * Created by carlospinto on 28/10/17.
 */

public class HttpResponseStream {

    private InputStream Istream;
    private byte[] responseBytes;
    private long responseLength;
    private String contentType;

    public HttpResponseStream() {

    }

    public HttpResponseStream(InputStream stream) {
        this.Istream = stream;
    }

    public HttpResponseStream(byte[] responseBytes, long responseLength) {
        this.responseBytes = responseBytes;
        this.responseLength = responseLength;
    }

    public byte[] getResponseBytes() {
        return responseBytes;
    }

    public void setResponseBytes(byte[] responseBytes) {
        this.responseBytes = responseBytes;
    }

    public long getResponseLength() {
        return responseLength;
    }

    public void setResponseLength(long responseLength) {
        this.responseLength = responseLength;
    }

    public InputStream getIstream() {
        return Istream;
    }

    public void setIstream(InputStream istream) {
        Istream = istream;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
