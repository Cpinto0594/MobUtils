package com.mobutils.http.converter;

import java.util.List;

/**
 * Created by CarlosP on 23/02/2018.
 */

public interface IJSONObjectConverter {

    public <T> List<T> DeserializeArray(Class<T> clazz, String value) throws RuntimeException;

    public <T extends Object> T DeserializeObject(Class<T> resultclass, String value)  throws RuntimeException;

    public String SerializeObject(Object value)  throws RuntimeException;
}
