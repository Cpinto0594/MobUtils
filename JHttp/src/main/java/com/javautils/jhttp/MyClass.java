package com.javautils.jhttp;

import com.javautils.jhttp.http.request.HttpManager;

public class MyClass {

    public static void main(String args[]) {

        HttpManager http = new HttpManager("");
        http.setHeader("H", "1");
        http.setHeader("H", "2");
    }


}

