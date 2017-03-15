package com.benzson.medicare;

/**
 * Created by Benzson on 3/11/17.
 */

public class AlarmComponent {

    //private variables
    private int id;
    private String path;
    private String name;
    private String dose;
    private String time;

    // Empty constructor
    public AlarmComponent() {

    }

    // constructor
    public AlarmComponent(int id, String path, String name, String dose, String time) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.dose = dose;
        this.time = time;
    }

    // constructor
    public AlarmComponent(String path, String name, String dose, String time) {
        this.path = path;
        this.name = name;
        this.dose = dose;
        this.time = time;
    }

    // getter method
    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String getDose() {
        return dose;
    }

    public String getTime() {
        return time;
    }

    // setter method
    public void setId(int id) {
        this.id = id;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
