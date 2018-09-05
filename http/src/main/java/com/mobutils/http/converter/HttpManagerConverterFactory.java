package com.mobutils.http.converter;

import android.util.Log;

import com.mobutils.http.request.HttpManager;
import com.mobutils.http.request.HttpManagerUtils;

import java.util.List;

/**
 * Created by CarlosP on 24/02/2018.
 */

public class HttpManagerConverterFactory {

    private static IJSONObjectConverter converter;

    public static void setCustomConverter(IJSONObjectConverter converter_) {
        converter = converter_;
    }

    public static <T> T DeserializeObject(Class<T> clazz, String values) {
        if (converter == null || values == null) return null;
        Object result = null;
        try {
            result = converter.DeserializeObject(clazz, values);
        } catch (Exception e) {
            Log.e(HttpManager.TAG, "Unable to convert data to desired class -> " + clazz.getSimpleName());
        }
        return (T) result;
    }

    public static <T> List<T> DeserializeArray(Class<T> clazz, String values) {
        if (converter == null || values == null) return null;
        Object result = null;
        try {
            result = converter.DeserializeArray(clazz, values);
        } catch (Exception e) {
            Log.e(HttpManager.TAG, "Unable to convert data to desired list of class -> " + clazz.getSimpleName());
        }

        return (List<T>) result;
    }

    public static String Serialize(Object values) {
        if (converter == null || values == null) return null;
        if (HttpManagerUtils.isPrimitiveOrPrimitiveWrapperOrString(values.getClass())) {
            return "" + values;
        }
        Object result = null;
        try {
            result = converter.SerializeObject(values);
        } catch (Exception e) {
            Log.e(HttpManager.TAG, "Unable to convert data to String");
        }
        return (String) result;
    }

}
