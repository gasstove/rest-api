package com.gasstove.gs.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Utility methods
 */
public class Util {

    // GSON ....................................................................
    public static Gson getGson(){

        // create builder to register serializer/deserializer
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.registerTypeAdapter(Time.class, new TimeSerializer());

        return gsonbuilder.create();
    }

    // RANDOM NUMBERS ...........................................................

    public static long randBetween(long start, long end) {
        return start + (long) ( Math.random() * (end - start));
    }

    public static int randBetween(int start, int end) {
        return start + (int) ( Math.random() * (end - start));
    }

    public static double randBetween(double start, double end) {
        return start + Math.random() * (end - start);
    }

}
