package com.benzson.medicare;

/**
 * Created by Benzson on 1/13/17.
 */

public class DiaryComponent {

    //private variables
    private int id;
    private String title;
    private String note;
    private String date;
    private String time;

    // Empty constructor
    public DiaryComponent() {

    }

    // constructor
    public DiaryComponent(int id, String title, String note, String date, String time) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.date = date;
        this.time = time;
    }

    // constructor
    public DiaryComponent(String title, String note, String date, String time) {
        this.title = title;
        this.note = note;
        this.date = date;
        this.time = time;
    }

    // getter method
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    // setter method

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
