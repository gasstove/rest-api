package com.gasstove.gs.util;


import com.google.gson.*;

import java.lang.reflect.Type;

public class SerializerBoolean implements JsonSerializer<Boolean>, JsonDeserializer<Boolean> {

    @Override
    public JsonElement serialize(Boolean arg0, Type arg1, JsonSerializationContext arg2) {
        //return new JsonPrimitive(arg0 ? 1 : 0);
        return new JsonPrimitive( arg0==null ? false : arg0 );
    }

    @Override
    public Boolean deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
        //return arg0.getAsInt() == 1;
        return arg0.getAsBoolean();
    }
}
