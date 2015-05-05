package com.gasstove.gs.util;

import org.joda.time.DateTime;

import java.util.GregorianCalendar;

/**
 * Created by gomes on 5/5/15.
 */
public class Time {

    DateTime jodatime;

    public Time(DateTime jodatime){
        this.jodatime = jodatime;
    }

    public Time(long epochtime){
        this.jodatime = new DateTime(epochtime);
    }

    public Time add_hours(double hours){
        Time newtime = new Time(jodatime);
        newtime.jodatime.plusSeconds((int) (hours*3600d));
        return newtime;
    }

//    public int hoursSinceEpoch(){
//        return (int) ( ((double)jodatime.getMillis())/1000d/60d/60d );
//    }
//
//    public int quarterHoursSinceEpoch(){
//        return (int) ( ((double)jodatime.getMillis())/1000d/60d/15d );
//    }
//
//    public int minutesSinceEpoch(){
//        return (int) ( ((double)jodatime.getMillis())/1000/60d );
//    }
//
//    public int secondsSinceEpoch(){
//        return (int) ( ((double)jodatime.getMillis())/1000 );
//    }


    public java.sql.Date toSqlDate(){
        return new java.sql.Date( jodatime.getMillis() );
    }

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
