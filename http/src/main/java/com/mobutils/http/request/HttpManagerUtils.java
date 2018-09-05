package com.mobutils.http.request;

import android.util.Log;

import com.mobutils.http.converter.HttpManagerConverterFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by CarlosP on 8/8/2017.
 */

public class HttpManagerUtils {


    public static String NO_CONN_MSG = "Se agotó el tiempo de espera al servidor.";
    public static String ERROR_UNKNOWN_MSG = "Petición no fue finalizada con éxito.";
    public static String ERROR_TEMPLATE = "[%s] - [%s]";


    public static boolean isEmpty(Object value) {
        if (value == null) {
            return true;
        }
        if (value instanceof String && ((String) value).trim().isEmpty()) {
            return true;
        }
        if (value instanceof Number && ((Integer) value) == 0) {
            return true;
        }
        return false;
    }

    public static String frombufferReader(BufferedReader stream) throws IOException {
        if (isEmpty(stream)) return null;

        String output;
        StringBuilder sb = new StringBuilder();
        while ((output = stream.readLine()) != null) {
            //System.out.println(output);
            sb.append(output);
        }
        stream.close();
        return sb.toString();
    }

    public static boolean checkServerAvailability(String url) {
        try {
            URL uri = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
            if (conn == null) {
                return false;
            }
            conn.setConnectTimeout(1000);
            conn.connect();
            return conn.getResponseCode() == 200;
        } catch (Exception e) {
            Log.e("checkServerResponse", e.getMessage());
            return false;
        }
    }

    public static boolean isPrimitiveOrPrimitiveWrapperOrString(Class<?> type) {
        return (type.isPrimitive() && type != void.class) ||
                type == Double.class || type == Float.class || type == Long.class ||
                type == Integer.class || type == Short.class || type == Character.class ||
                type == Byte.class || type == Boolean.class || type == String.class;
    }

    //Convertimos los parametros de la peticion en string
    public static String convertParamsToValue(Object param) {
        if (HttpManagerUtils.isEmpty(param)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int loop = 0;
        if (param instanceof Map) {
            Map<?, ?> aux = (Map) param;
            for (Map.Entry<?, ?> key : aux.entrySet()) {
                if (loop == 0) {
                    sb.append(key.getKey()).append("=").append(key.getValue());
                } else {
                    sb.append("&").append(key.getKey()).append("=").append(key.getValue());
                }
                loop++;
            }
        } else if (isPrimitiveOrPrimitiveWrapperOrString(param.getClass())) {
            return param + "";
        } else {
            return HttpManagerConverterFactory.Serialize(param);
        }
        return null;
    }

    //Formatear el mensaje de respuesta
    public static String formatMessage(String template, int code, String message) {
        return String.format(template, code, message);
    }


}
