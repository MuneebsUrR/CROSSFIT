package com.example.demo;

import java.sql.Date;

public class LogCalories {


        private String MealName;
        private int Calories;
        private String TimeStamp;

        public LogCalories(String mealname, int calories , String timestamp) {
            this.MealName = mealname;
            this.Calories = calories;
            this.TimeStamp = timestamp;


        }

    public String getMealName() {
        return MealName;
    }

    public int getCalories() {
        return Calories;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

}
