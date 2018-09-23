package com.javautils.jhttp.http.request;

import com.javautils.jhttp.http.converter.HttpManagerConverterFactory;
import com.javautils.jhttp.http.exception.HttpManagerInternalException;
import com.javautils.jhttp.http.handlers.HttpResultListeners;
import com.javautils.jhttp.http.headers.HttpCookies;
import com.javautils.jhttp.http.headers.HttpHeaders;
import com.javautils.jhttp.http.responses.HttpResponseStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by CarlosP on 23/5/2017.
 */

public class HttpManager {

    private final String HEAD = "HEAD";
    private final String GET = "GET";
    private final String POST = "POST";
    private final String DELETE = "DELETE";
    private final String PUT = "PUT";
    private static String protocol = "http";
    private String url;
    private HttpURLConnection httpConnection;
    private URL HttpUrl;
    private BufferedReader bReader;
    private OutputStream oStream;
    private static HashMap<String, String> _StaticRequestHeaders;
    private HashMap<String, String> RequestHeaders;
    private HttpHeaders ResponseHeaders;
    private Object params;
    private int responseCode = 0;
    private String responseMessage = "";
    public static int TIME_OUT = 2000;
    public static String TAG = "HTTPMANAG";
    private Class<?> responseClass = String.class;
    private boolean isArrayResponse;


    private static Logger log = LoggerFactory.getLogger(HttpManager.class);

    public HttpManager() {
    }

    public HttpManager(String url_) {
        this.url = url_;
    }

    //GET Method
    public HttpManager head() {
        service(this.HEAD, null);
        return this;
    }

    public HttpManager head(HttpResultListeners callback) {
        service(this.HEAD, callback);
        return this;
    }

    //GET Method
    public HttpManager get() {
        service(this.GET, null);
        return this;
    }

    public HttpManager get(HttpResultListeners callback) {
        service(this.GET, callback);
        return this;
    }

    //POST Method
    public HttpManager post() {
        service(this.POST, null);
        return this;
    }

    public HttpManager post(HttpResultListeners callback) {
        service(this.POST, callback);
        return this;
    }

    //DELETE Method
    public HttpManager delete() {
        service(this.DELETE, null);
        return this;
    }

    public HttpManager delete(HttpResultListeners callback) {
        service(this.DELETE, callback);
        return this;
    }

    //PUR Method
    public HttpManager put() {
        service(this.PUT, null);
        return this;
    }

    public HttpManager put(HttpResultListeners callback) {
        service(this.DELETE, callback);
        return this;
    }

    public HttpManager response2Type(Class responseClass) {
        this.isArrayResponse = false;
        this.responseClass = responseClass;
        return this;
    }

    public HttpManager response2ArrayType(Class responseClass) {
        this.isArrayResponse = true;
        this.responseClass = responseClass;
        return this;
    }

    public HttpManager setParams(Object params) {
        this.params = params;
        return this;
    }

    public HttpManager setTimeOutTime(int value) {
        TIME_OUT = value;
        return this;
    }

    //Encargado de gestionar la peticion http y  evaluar los callback methods
    private Object service(String method, HttpResultListeners callback) {
        Object resultObject = null;
        Exception exp = null;
        try {

            if (callback != null) {
                //Si se retorna falso en el beforeSend callback detenemos la peticion
                boolean beforeSend = callback.onBeforeSend(this.RequestHeaders);
                if (!beforeSend) return null;
            }


            //Realizamos el proceso y devolvemos el valor para enviar a los callback
            resultObject = MakeRequest(method);

        } catch (Exception e) {
            exp = e;
        }
        //Ejecutamos los callback
        if (callback != null) {
            try {
                if (this.responseCode != HttpURLConnection.HTTP_ACCEPTED && this.responseCode != HttpURLConnection.HTTP_OK)
                    callback.onError(exp, this.ResponseHeaders);
                else
                    callback.onSuccess(resultObject, this.ResponseHeaders, this.responseCode);
            } catch (Exception e) {
                log.debug(TAG + " -> " + e.getMessage());
            }
        }
        log.debug(TAG + " -> " + "Finalizando petición;Disconnecting Connection");
        this.httpConnection.disconnect();
        return resultObject;
    }

    //Realizar proceso de peticion http
    private Object MakeRequest(String method) throws Exception {

        initConfigurationVariables(method);

        this.responseCode = HttpURLConnection.HTTP_INTERNAL_ERROR;

        try {
            this.httpConnection.setRequestMethod(method);
            this.httpConnection.setConnectTimeout(TIME_OUT);
            this.httpConnection.setDoInput(true);//Permitir recepcion de datos
            log.debug(TAG + " -> " + "Response Detail: URL [ " + this.url + " ] Method [ " + method + " ]\n  timeout time [ "
                    + TIME_OUT + " ] responseType [ " + this.responseClass.getSimpleName() + " ]\n ");


            //Agregamos los headers a la petición
            fillRequestWithHeaders();
            //Convertimos los parametros enviados a string para enviarlos en la peticion
            Object valueToRequest = HttpManagerUtils.convertParamsToValue(params);
            if (!HttpManagerUtils.isEmpty(valueToRequest)) {
                if (!method.equals(this.GET)) {
                    //Si no es una entidad de tipo nativa como Integer,Double... etc, Agregar serializado a la peticion
                    if (params != null && !HttpManagerUtils.isPrimitiveOrPrimitiveWrapperOrString(params.getClass()))
                        this.httpConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

                    this.httpConnection.setDoOutput(true);//Permitir envio de datos

                    //Obtenemos el stream para envio de datos al servidor
                    this.oStream = this.httpConnection.getOutputStream();
                    byte[] bytes = ((String) valueToRequest).getBytes("UTF-8");//Convertimos el string de datos a Bytes
                    DataOutputStream dos = new DataOutputStream(oStream);
                    dos.write(bytes);//Lo escribimos en el stream  Http
                    oStream.flush();
                    oStream.close();//Cerramos el stream
                    dos.flush();
                    dos.close();

                    log.debug(TAG + " -> " + "Content-Type [ " +
                            this.httpConnection.getContentType() + " ].");

                }
            }
            return convertResponseToResult();

        } catch (Exception e) {
            throw e;
        }
    }

    private Object convertResponseToResult() throws Exception {
        String serverMessage = null;
        Object result = null;

        try {
            this.responseCode = this.httpConnection.getResponseCode();
            this.responseMessage = this.httpConnection.getResponseMessage();
            boolean isSuccess = HttpURLConnection.HTTP_OK <= this.responseCode
                    && this.responseCode <= 299;


            //Obtenemos los Headers
            getHeadersFromResponse();

            //Si queremos obtener un array de bits de data del servidor
            if (isSuccess && responseClass.isAssignableFrom(HttpResponseStream.class)) {
//            if (true) {

                InputStream inputStream = this.httpConnection.getInputStream();
                byte[] resultBytes = {};

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int readed = inputStream.read();
                while (readed != -1) {
                    baos.write(readed);
                    readed = inputStream.read();
                }
                resultBytes = baos.toByteArray();

                HttpResponseStream response = new HttpResponseStream(resultBytes, resultBytes.length);
                response.setIstream(inputStream);
                response.setContentType(this.httpConnection.getContentType());
                result = response;

                log.debug(TAG + " -> " + "Tipo de dato a retornar: Stream");

            } else {

                //Si codigo de error != 20x entonces obtenemos el errorStream si no el inputstream
                //Esto para obtener correctamente el error enviado desde el servidor.
                if (isSuccess) {
                    this.bReader = new BufferedReader(new InputStreamReader(this.httpConnection.getInputStream()));
                } else if (this.httpConnection.getErrorStream() != null) {
                    this.bReader = new BufferedReader(new InputStreamReader(this.httpConnection.getErrorStream()));
                }

                if (this.bReader != null) {
                    serverMessage = HttpManagerUtils.frombufferReader(this.bReader);
                    log.debug(TAG + " -> " + "Respuesta del servidor: " + serverMessage);
                    //Si la peticion retornó error
                    if (!isSuccess) {
                        throw new HttpManagerInternalException(serverMessage);
                    }
                }
                if (isSuccess) {
                    //Retornamos la respuesta en el formato deseado
                    //Si es String la clase respuesta devolvemos sin convertir
                    if (String.class.isAssignableFrom(responseClass)) {
                        result = serverMessage;
                    } else if (this.isArrayResponse) {
                        result = HttpManagerConverterFactory.DeserializeArray(responseClass, serverMessage);
                        log.debug(TAG + " -> " + "Tipo de dato a retornar: List<" + ((Class) responseClass).getName() + ">");
                    } else {
                        result = HttpManagerConverterFactory.DeserializeObject(responseClass, serverMessage);
                        log.debug(TAG + " -> " + "Tipo de dato a retornar: " + ((Class) responseClass).getName());
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    //Agregar header y value al array de headers para la peticion
    public HttpManager setHeader(String key, String value) {
        if (HttpManagerUtils.isEmpty(this.RequestHeaders)) this.RequestHeaders = new HashMap<>();
        if (this.RequestHeaders.get(key) != null) {
            this.RequestHeaders.remove(key);
        }
        this.RequestHeaders.put(key, value);

        log.debug(TAG + " -> " + "Agregando header: " + key + " valor:" + value);
        return this;
    }

    //Iniciamos las variables iniciales de configuración
    private void initConfigurationVariables(String method) throws Exception {
        try {
            //Si no se especificó url, finalizamos la peticion
            if (HttpManagerUtils.isEmpty(this.url))
                throw new HttpManagerInternalException("Url Param not defined");

            //Si hay parametros y es una peticion GET, armamos la url
            if (this.params != null && method == this.GET) {
                if (this.url.contains("?")) {
                    this.url += HttpManagerUtils.convertParamsToValue(params);
                } else {
                    this.url += "?" + HttpManagerUtils.convertParamsToValue(params);
                }
            }
            if (!this.url.toLowerCase().contains("http") && !this.url.toLowerCase().contains("https")) {
                this.url = this.protocol + "://" + this.url;
            }

            this.HttpUrl = new URL(this.url);//Creamos la Variable UrlConnection
            this.httpConnection = (HttpURLConnection) HttpUrl.openConnection(); //Creamos la conexión
        } catch (Exception e) {
            throw e;
        }
    }


    //Tomamos los headers que nos regresó la peticion
    private void getHeadersFromResponse() throws IOException {

        if (!HttpManagerUtils.isEmpty(this.httpConnection) &&
                (this.responseCode == HttpURLConnection.HTTP_CREATED
                        || this.responseCode == HttpURLConnection.HTTP_OK)) {

            this.ResponseHeaders = getHeaders(this.httpConnection);
        }
    }


    private static HttpHeaders getHeaders(HttpURLConnection conection) {
        Map<String, List<String>> headers = conection.getHeaderFields();
        HttpHeaders resultHeaders = new HttpHeaders();
        for (Map.Entry entry : headers.entrySet()) {
            if (entry.getKey() == null) {
                continue;
            }
            switch (entry.getKey().toString().toLowerCase()) {
                case "pragma":
                    resultHeaders.setPragma(entry.getValue().toString());
                    break;
                case "server":
                    resultHeaders.setServer(entry.getValue().toString());
                    break;
                case "connection":
                    resultHeaders.setConnection(entry.getValue().toString());
                    break;
                case "cache-control":
                    resultHeaders.setCacheControl(entry.getValue().toString());
                    break;
                case "expires":
                    resultHeaders.setExpires(((List<String>) entry.getValue()).get(0));
                    break;
                case "content-length":
                    resultHeaders.setContentLength(Long.parseLong(((List<String>) entry.getValue()).get(0)));
                    break;
                case "date":
                    resultHeaders.setDate(((List<String>) entry.getValue()).get(0));
                    break;
                case "content-type":
                    resultHeaders.setContent_type(entry.getValue().toString());
                    break;
                case "set-cookie":
                    assignCookieToHeaders(resultHeaders, (List<String>) entry.getValue());
            }

        }
        return resultHeaders;
    }

    private static List<HttpCookies> assignCookieToHeaders(HttpHeaders resultHeaders, List<String> listCookies) {

        String values[] = null;
        String cookie_[] = null;
        List<HttpCookies> cookiesList = new ArrayList<>();
        HttpCookies cookie = null;
        for (String key : listCookies) {
            values = key.split(";");

            cookie = new HttpCookies();
            for (String cooki : values) {
                cookie_ = cooki.split("=");
                if ("path".equals(cookie_[0].trim())) {
                    cookie.setPath(cookie_[1]);
                } else if ("expires".equals(cookie_[0].trim())) {
                    cookie.setExpires(cookie_[1]);
                } else if ("HttpOnly".equals(cookie_[0].trim())) {
                    cookie.setHttpOnly(true);
                } else {
                    cookie.setKey(cookie_[0]);
                    cookie.setValue(cookie_[1]);
                }
            }
            cookiesList.add(cookie);
        }
        resultHeaders.setCookies(cookiesList);
        return cookiesList;
    }


    //Agregamos los headers a la peticion
    private void fillRequestWithHeaders() {
        if (!HttpManagerUtils.isEmpty(this.RequestHeaders))
            for (Map.Entry<String, String> keyPair : this.RequestHeaders.entrySet()) {
                this.httpConnection.setRequestProperty(keyPair.getKey(), keyPair.getValue());
            }
        if (!HttpManagerUtils.isEmpty(_StaticRequestHeaders))
            for (Map.Entry<String, String> keyPair : this._StaticRequestHeaders.entrySet()) {
                this.httpConnection.setRequestProperty(keyPair.getKey(), keyPair.getValue());
            }
    }

    public static void setRequestHeader(String key, String value) {

        if (HttpManagerUtils.isEmpty(_StaticRequestHeaders)) {
            _StaticRequestHeaders = new HashMap<>();
        }
        _StaticRequestHeaders.put(key, value);
        log.debug(TAG + " -> " + "Agregando header: " + key);
    }

    public static void deleteRequestHeader(String key) {

        if (HttpManagerUtils.isEmpty(_StaticRequestHeaders)) {
            return;
        }
        _StaticRequestHeaders.remove(key);
        log.debug(TAG + " -> " + "Eliminando header: " + key);
    }

    //Setters and Getters


    public HttpHeaders getResponseHeaders() {
        return ResponseHeaders;
    }
}


