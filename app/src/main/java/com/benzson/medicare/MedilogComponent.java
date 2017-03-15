package com.benzson.medicare;

/**
 * Created by Benzson on 1/21/17.
 */

public class MedilogComponent {

    //private variables
    private int id;
    private String path;
    private String name;
    private String dose;
    private String note;
    private String time;
    private String date;

    // Empty constructor
    public MedilogComponent() {

    }

    // constructor
    public MedilogComponent(int id, String path, String name, String dose, String note, String time, String date) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.dose = dose;
        this.note = note;
        this.time = time;
        this.date = date;
    }

    // constructor
    public MedilogComponent(String path, String name, String dose, String note, String time, String date) {
        this.path = path;
        this.name = name;
        this.dose = dose;
        this.note = note;
        this.time = time;
        this.date = date;
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

    public String getNote() {
        return note;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
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

    public void setNote(String note) {
        this.note = note;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
