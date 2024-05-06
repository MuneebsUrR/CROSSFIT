package com.example.demo;

public class LogWorkout {
    private int sets;
    private int reps;
    private String type;

    public LogWorkout(int s,int r, String t)
    {
        this.sets = s;
        this.reps = r;
        this.type = t;
    }
    public int getSets(){
        return sets;
    }
    public int getReps(){
        return reps;
    }
    public String getType(){
        return type;
    }
}
