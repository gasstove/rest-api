package com.gasstove.gs.util;

import com.google.gson.*;
import org.joda.time.DateTime;

/**
 * Class for managing date/time quantities
 */
public class Time {

    public DateTime jodatime;

    // CONSTRUCTORS .....................................................

    public Time(DateTime jodatime){
        this.jodatime = jodatime;
    }

    public Time(long epochtime){
        this.jodatime = new DateTime(epochtime);
    }

    public Time(String jsonstr){
        this.jodatime = new DateTime(jsonstr);
    }

    public Time add_hours(double hours){
        Time newtime = new Time(jodatime);
        newtime.jodatime.plusSeconds((int) (hours*3600d));
        return newtime;
    }

    // CONVERTERS ........................................................

    public java.sql.Date toSqlDate(){
        return new java.sql.Date( jodatime.getMillis() );
    }

    // SERIALIZER  ........................................................

    public JsonElement toJson(){
        return (new SerializerTime()).serialize(this,null,null);
    }

    public Time fromJson(String jsonString){
        SerializerTime td = new SerializerTime();
        return td.deserialize(new JsonPrimitive(jsonString),null,null);
    }

    // OVERRIDES ..........................................................

    @Override
    public String toString() {
        return jodatime.toString();
    }


    // STATICS ..........................................................

    /**
     * Generate a random date base on min_year and max_year
     */
    public static Time randomDate(int min_year,int max_year){
        DateTime mindate = new DateTime(min_year,1,1,0,0);
        DateTime maxdate = new DateTime(max_year,12,31,23,59);
        DateTime datetime = new DateTime( Util.randBetween(mindate.getMillis(),maxdate.getMillis()) );
        return new Time(datetime);
    }
}
