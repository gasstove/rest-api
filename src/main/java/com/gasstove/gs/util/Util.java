package com.gasstove.gs.util;

import com.gasstove.gs.models.AbstractObject;
import com.gasstove.gs.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Utility methods
 */
public class Util {

    // GSON ....................................................................
    public static Gson getGson(){
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.registerTypeAdapter(Time.class, new SerializerTime());
        gsonbuilder.registerTypeAdapter(Boolean.class, new SerializerBoolean());
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

    /**
     *
     * Sample from A.
     *
     * @param A Array of T
     *
     * @return Sampled array of integers
     */
    public static <T> T sample(final HashSet<T> A) {

        // create new array
        ArrayList<T> Acopy = new ArrayList(A);

        // pick a random element
        return Acopy.get(Util.randBetween(0,A.size()));
    }

    /**
     *
     * Sample n values from A without replacement.
     *
     * @param A Array of T
     * @param n number of samples to take from A
     *
     * @return Sampled array of integers
     */
    public static <T> ArrayList<T> sample_without_replacement(final HashSet<T> A, int n) {
        if(n<0)
            return null;

        // create new array
        ArrayList<T> Acopy = new ArrayList(A);

        // shuffle it
        java.util.Collections.shuffle(Acopy);

        // pick out first n elements
        return new ArrayList(Acopy.subList(0,Math.min(n,A.size())));
    }

    // MISC ................................................................

    public static String jsonArray(ArrayList<AbstractObject> collection){
        if (collection.isEmpty())
            return "[]";
        StringBuilder str = new StringBuilder();
        str.append("[");
        for (AbstractObject obj : collection)
            str.append(obj.toJson()).append(",");
        str.setLength(str.length() - 1);
        str.append("]");
        return str.toString();
    }

    public static String joinToString(Collection<?> collection, CharSequence separator) {
        if (collection.isEmpty())
            return "";
        StringBuilder sepValueBuilder = new StringBuilder();
        for (Object obj : collection)
            sepValueBuilder.append(obj).append(separator);
        sepValueBuilder.setLength(sepValueBuilder.length() - separator.length());
        return sepValueBuilder.toString();
    }

    /** Generate undordered list integers between a and b inclusive
     *
     * @param a
     * @param b
     * @return
     */
    public static HashSet<Integer> intset(int a,int b){
        HashSet<Integer> A = new HashSet<Integer>();
        for(int x=a;x<=b;x++)
            A.add(x);
        return A;
    }
}
