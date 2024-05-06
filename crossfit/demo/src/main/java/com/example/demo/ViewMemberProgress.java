package com.example.demo;
public class ViewMemberProgress {
    int Calories;
    int sets;
    int reps;
    String type;
    String Meal;

    public ViewMemberProgress(int cal,int set,int rep,String ty,String ml){
        this.Calories=cal;
        this.sets=set;
        this.reps=rep;
        this.type=ty;
        this.Meal=ml;

    }

    public int getCalories() {
        return Calories;
    }

    public int getReps() {
        return reps;
    }

    public int getSets() {
        return sets;
    }

    public String getMeal() {
        return Meal;
    }

    public String getType() {
        return type;
    }
}
