package com.example.revalidatieapp;

public class TimeConverter {

    public static String convertTime(int theTime){
        String time = theTime + "";
        if(time.length() == 1){
            time = "0" + time;
        }

        return time;
    }


}
