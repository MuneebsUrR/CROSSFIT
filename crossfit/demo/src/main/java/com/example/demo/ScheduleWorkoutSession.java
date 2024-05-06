package com.example.demo;

import javafx.scene.control.TextField;

public class ScheduleWorkoutSession {
    private String date;
    private String time;
    private String type;

    public ScheduleWorkoutSession(String t,String tm,String d){
        this.date = d;
        this.time = tm;
        this.type = t;

    }



    public String getDate()
    {
        return date;
    }
    public String getTime()
    {
        return time;
    }
    public String getType(){
        return type;
    }

}
