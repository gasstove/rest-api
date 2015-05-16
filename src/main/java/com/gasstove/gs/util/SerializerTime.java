package com.gasstove.gs.util;


import com.google.gson.*;

import java.lang.reflect.Type;

public class SerializerTime implements JsonSerializer<Time>, JsonDeserializer<Time> {

    public JsonElement serialize(Time src,java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }

    public Time deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return new Time(json.getAsJsonPrimitive().getAsString());
    }

}