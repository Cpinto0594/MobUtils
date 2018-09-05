package com.mobutils.async;

public class AsyncUtils {
    public static String constructStackTrace(Exception e) {
        Exception aux = e.getCause() != null ? (Exception) e.getCause() : e;
        StringBuilder sb = new StringBuilder();
        sb.append("Exception: ").append(aux.getLocalizedMessage()).append("\n");
        for (StackTraceElement stack : aux.getStackTrace()) {
            sb.append(" Exception File: ").append(stack.getFileName())
                    .append(" Exception Class: ").append(stack.getClassName())
                    .append(" Exception Method: ").append(stack.getMethodName())
                    .append(" Exception LineNumber: ").append(stack.getLineNumber())
                    .append("\n");
        }
        return sb.toString();
    }

}
