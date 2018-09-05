package com.mobutils.http.headers;

import java.util.Date;
import java.util.List;

/**
 * Created by carlospinto on 26/10/17.
 */

public class HttpHeaders {


    private String pragma;
    private String server;
    private String connection;
    private String cacheControl;
    private String expires;
    private long contentLength;
    private String date;
    private String content_type;
    private List<HttpCookies> cookies;

    public HttpHeaders() {
    }

    public List<HttpCookies> getCookies() {
        return cookies;
    }

    public void setCookies(List<HttpCookies> cookies) {
        this.cookies = cookies;
    }

    public String getPragma() {
        return pragma;
    }

    public void setPragma(String pragma) {
        this.pragma = pragma;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getCacheControl() {
        return cacheControl;
    }

    public void setCacheControl(String cacheControl) {
        this.cacheControl = cacheControl;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public HttpCookies findCookie(String key) {
        for (HttpCookies cookie : getCookies()) {
            if (cookie.getKey().equals(key)) {
                return cookie;
            }
        }
        return null;
    }
}
