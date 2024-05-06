package com.example.demo;

public class StaffManagement {
    private String Name;
    private String ShiftStart;
    private String ShiftEnd;

    public StaffManagement(String name, String shiftstart , String shiftend) {
        this.Name = name;
        this.ShiftEnd = shiftend;
        this.ShiftStart = shiftstart;

    }

    public String getName() {
        return Name;
    }

    public String getShiftStart() {
        return ShiftStart;
    }

    public String getShiftEnd() {
        return ShiftEnd;
    }


}
