package com.benzson.medicare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benzson on 3/15/17.
 */

public class AlarmDatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "alarmComponentManager";

    // Contacts table name
    private static final String TABLE_ALARMCOMPONENT = "alarmComponent";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_PATH = "path";
    private static final String KEY_NAME = "name";
    private static final String KEY_DOSE = "dose";
    private static final String KEY_TIME = "time";

    public AlarmDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MEDILOGCOMPONENT_TABLE = "CREATE TABLE " + TABLE_ALARMCOMPONENT + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PATH + " TEXT," + KEY_NAME + " TEXT,"
                + KEY_DOSE + " TEXT," + KEY_TIME + " TEXT" + ")";
        db.execSQL(CREATE_MEDILOGCOMPONENT_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARMCOMPONENT);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new alarmComponent
    public void addAlarmComponent(AlarmComponent alarmComponent) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PATH, alarmComponent.getPath()); // MedilogComponent Path
        values.put(KEY_NAME, alarmComponent.getName()); // MedilogComponent Name
        values.put(KEY_DOSE, alarmComponent.getDose()); // MedilogComponent Dose
        values.put(KEY_TIME, alarmComponent.getTime()); // MedilogComponent Time

        // Inserting Row
        db.insert(TABLE_ALARMCOMPONENT, null, values);
        db.close(); // Closing database connection
    }

    // Getting single alarmComponent by id
    public AlarmComponent getAlarmComponent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ALARMCOMPONENT, new String[] { KEY_ID,
                        KEY_PATH, KEY_NAME, KEY_DOSE, KEY_TIME }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, KEY_ID + " ASC", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        AlarmComponent alarmComponent = new AlarmComponent(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        // return alarmComponent
        return alarmComponent;
    }

    // Getting single alarmComponent by time
    public AlarmComponent getAlarmComponent(String time) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ALARMCOMPONENT, new String[] { KEY_ID,
                        KEY_PATH, KEY_NAME, KEY_DOSE, KEY_TIME }, KEY_ID + "=?",
                new String[] { String.valueOf(time) }, null, null, KEY_TIME + " ASC", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        AlarmComponent alarmComponent = new AlarmComponent(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        // return alarmComponent
        return alarmComponent;
    }

    // Getting all alarmComponent
    public List<AlarmComponent> getAllAlarmComponent() {
        List<AlarmComponent> componentList = new ArrayList<AlarmComponent>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ALARMCOMPONENT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AlarmComponent alarmComponent = new AlarmComponent();
                alarmComponent.setId(Integer.parseInt(cursor.getString(0)));
                alarmComponent.setPath(cursor.getString(1));
                alarmComponent.setName(cursor.getString(2));
                alarmComponent.setDose(cursor.getString(3));
                alarmComponent.setTime(cursor.getString(4));

                // Adding component to list
                componentList.add(alarmComponent);
            } while (cursor.moveToNext());
        }

        // return component list
        return componentList;
    }

    // Getting alarmComponent count
    public int getAlarmComponentCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ALARMCOMPONENT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    // Updating single alarmComponent
    public int updateAlarmComponent(AlarmComponent alarmComponent) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PATH, alarmComponent.getPath());
        values.put(KEY_NAME, alarmComponent.getName());
        values.put(KEY_DOSE, alarmComponent.getDose());
        values.put(KEY_TIME, alarmComponent.getTime());

        // updating row
        return db.update(TABLE_ALARMCOMPONENT, values, KEY_ID + " = ?",
                new String[] { String.valueOf(alarmComponent.getId()) });
    }

    // Deleting single alarmComponent
    public void deleteAlarmComponent(AlarmComponent alarmComponent) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ALARMCOMPONENT, KEY_ID + " = ?",
                new String[] { String.valueOf(alarmComponent.getId()) });
        db.close();
    }

    // Deleting all alarmComponent
    public void deleteAllAlarmComponent() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ALARMCOMPONENT, null, null);
        db.close();
    }

    // Updating all alarmComponent
    public int updateAllAlarmComponent() {
        SQLiteDatabase db = this.getWritableDatabase();

        // updating all rows
        return db.update(TABLE_ALARMCOMPONENT, null, null, null);
    }

}
