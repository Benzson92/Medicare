package com.benzson.medicare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benzson on 1/25/17.
 */

public class MedDatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "medComponentManager";

    // Contacts table name
    private static final String TABLE_MEDILOGCOMPONENT = "medilogComponent";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_PATH = "path";
    private static final String KEY_NAME = "name";
    private static final String KEY_DOSE = "dose";
    private static final String KEY_NOTE = "note";
    private static final String KEY_TIME = "time";
    private static final String KEY_DATE = "date";

    public MedDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MEDILOGCOMPONENT_TABLE = "CREATE TABLE " + TABLE_MEDILOGCOMPONENT + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PATH + " TEXT," + KEY_NAME + " TEXT,"
                + KEY_DOSE + " TEXT," + KEY_NOTE + " TEXT," + KEY_TIME + " TEXT," + KEY_DATE + " TEXT" + ")";
        db.execSQL(CREATE_MEDILOGCOMPONENT_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDILOGCOMPONENT);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new medilogComponent
    public void addMedilogComponent(MedilogComponent medilogComponent) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PATH, medilogComponent.getPath()); // MedilogComponent Path
        values.put(KEY_NAME, medilogComponent.getName()); // MedilogComponent Name
        values.put(KEY_DOSE, medilogComponent.getDose()); // MedilogComponent Dose
        values.put(KEY_NOTE, medilogComponent.getNote()); // MedilogComponent Note
        values.put(KEY_TIME, medilogComponent.getTime()); // MedilogComponent Time
        values.put(KEY_DATE, medilogComponent.getDate()); // MedilogComponent Date

        // Inserting Row
        db.insert(TABLE_MEDILOGCOMPONENT, null, values);
        db.close(); // Closing database connection
    }

    // Getting single medilogComponent by id
    public MedilogComponent getMedilogComponent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MEDILOGCOMPONENT, new String[] { KEY_ID,
                        KEY_PATH, KEY_NAME, KEY_DOSE, KEY_NOTE, KEY_TIME, KEY_DATE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, KEY_ID + " ASC", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        MedilogComponent medilogComponent = new MedilogComponent(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
        // return medilogComponent
        return medilogComponent;
    }

    // Getting single medilogComponent by time
    public MedilogComponent getMedilogComponent(String time) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MEDILOGCOMPONENT, new String[] { KEY_ID,
                        KEY_PATH, KEY_NAME, KEY_DOSE, KEY_NOTE, KEY_TIME, KEY_DATE }, KEY_ID + "=?",
                new String[] { String.valueOf(time) }, null, null, KEY_TIME + " ASC", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        MedilogComponent medilogComponent = new MedilogComponent(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
        // return diaryComponent
        return medilogComponent;
    }

    // Getting all medilogComponent
    public List<MedilogComponent> getAllMedilogComponent() {
        List<MedilogComponent> componentList = new ArrayList<MedilogComponent>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MEDILOGCOMPONENT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MedilogComponent medilogComponent = new MedilogComponent();
                medilogComponent.setId(Integer.parseInt(cursor.getString(0)));
                medilogComponent.setPath(cursor.getString(1));
                medilogComponent.setName(cursor.getString(2));
                medilogComponent.setDose(cursor.getString(3));
                medilogComponent.setNote(cursor.getString(4));
                medilogComponent.setTime(cursor.getString(5));
                medilogComponent.setDate(cursor.getString(6));

                // Adding component to list
                componentList.add(medilogComponent);
            } while (cursor.moveToNext());
        }

        // return component list
        return componentList;
    }

    // Getting medilogComponent count
    public int getMedilogComponentCount() {
        String countQuery = "SELECT  * FROM " + TABLE_MEDILOGCOMPONENT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    // Updating single medilogComponent
    public int updateMedilogComponent(MedilogComponent medilogComponent) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PATH, medilogComponent.getPath());
        values.put(KEY_NAME, medilogComponent.getName());
        values.put(KEY_DOSE, medilogComponent.getDose());
        values.put(KEY_NOTE, medilogComponent.getNote());
        values.put(KEY_TIME, medilogComponent.getTime());
        values.put(KEY_DATE, medilogComponent.getDate());

        // updating row
        return db.update(TABLE_MEDILOGCOMPONENT, values, KEY_ID + " = ?",
                new String[] { String.valueOf(medilogComponent.getId()) });
    }

    // Deleting single medilogComponent
    public void deleteMedilogComponent(MedilogComponent medilogComponent) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEDILOGCOMPONENT, KEY_ID + " = ?",
                new String[] { String.valueOf(medilogComponent.getId()) });
        db.close();
    }
}
