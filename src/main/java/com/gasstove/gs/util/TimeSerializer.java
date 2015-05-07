package com.gasstove.gs.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Created by gomes on 5/6/15.
 */

public class TimeSerializer implements JsonSerializer<Time> {
    public JsonElement serialize(Time src,java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }
}