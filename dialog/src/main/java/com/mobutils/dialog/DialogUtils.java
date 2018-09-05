package com.mobutils.dialog;

public class DialogUtils {

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

}
