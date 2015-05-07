package com.gasstove.gs.util;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class TimeDeserializer implements JsonDeserializer<Time> {
    public Time deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return new Time(json.getAsJsonPrimitive().getAsString());
    }
}