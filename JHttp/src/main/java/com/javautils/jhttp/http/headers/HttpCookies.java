package com.javautils.jhttp.http.headers;

/**
 * Created by carlospinto on 26/10/17.
 */

public class HttpCookies {


        private String key;
        private String value;
        private String path;
        private String expires;
        private boolean httpOnly;

        public HttpCookies() {
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getExpires() {
            return expires;
        }

        public void setExpires(String expires) {
            this.expires = expires;
        }

        public boolean isHttpOnly() {
            return httpOnly;
        }

        public void setHttpOnly(boolean httpOnly) {
            this.httpOnly = httpOnly;
        }

    @Override
    public String toString() {
        return "HttpCookies{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", path='" + path + '\'' +
                ", expires='" + expires + '\'' +
                ", httpOnly=" + httpOnly +
                '}';
    }
}
