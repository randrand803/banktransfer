package com.bank.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TimeUtil {

    public static String TOPIC = "transfer1";

    public static Date getRandomTime(){

        Random random = new Random();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String year = "2022";
        int month = random.nextInt(12)+1;
        int day = random.nextInt(31)+1;

        StringBuilder builder = new StringBuilder();
        builder.append(year);

        if(month < 10){
            builder.append("-0"+month+"-");
        }else{
            builder.append("-" + month + "-");
        }

        if(day < 10){
            builder.append("0"+day);
        }else{
            builder.append(day);
        }

        try {
            return format.parse(builder.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

}
