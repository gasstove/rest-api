package com.gasstove.gs.test.util;

import com.gasstove.gs.util.SerializerTime;
import com.gasstove.gs.util.Time;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by gomes on 5/6/15.
 */
public class TestTime {

    @Test
    public void testSerializer(){
        Time time = new Time(1430972483L);
        String str1 =  time.toJson().getAsString();
        String str2  = time.fromJson(str1).toString();
        assertEquals(str1,str2);
        assertEquals(str1, "1970-01-17T05:29:32.483-08:00");
    }

    @Test
    public void testGson(){
        ArrayList<Time> times = new ArrayList<Time>();
        times.add(new Time(1430972483L));
        times.add(new Time(1530972483L));

        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.registerTypeAdapter(Time.class, new SerializerTime());
        Gson gson = gsonbuilder.create();
        assertEquals("[\"1970-01-17T05:29:32.483-08:00\",\"1970-01-18T09:16:12.483-08:00\"]",gson.toJson(times));
    }

}
