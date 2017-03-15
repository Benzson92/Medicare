package com.benzson.medicare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by Benzson on 1/16/17.
 */

public class DiaryDetail extends AppCompatActivity {

    int id;
    String title;
    String note;
    String date;
    String time;

    TextView titleFill;
    TextView noteFill;
    TextView dateFill;
    TextView timeFill;

    Intent intent;
    Intent i;

    public static DiaryDetail diaryDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        diaryDetail = this;

        i = new Intent(this, EditDiary.class);

        // Retrieve data from ListDiary on listview item click
        intent = getIntent();
        id = intent.getIntExtra("id", 0);
        title = intent.getStringExtra("title");
        note = intent.getStringExtra("note");
        date = intent.getStringExtra("date");
        time = intent.getStringExtra("time");

        i.putExtra("id2", id);
        i.putExtra("title2", title);
        i.putExtra("note2", note);
//        i.putExtra("date2", date);
//        i.putExtra("time2", time);

        // Locate the TextViews in diary_detail.xml
        titleFill = (TextView) findViewById(R.id.title_fill);
        noteFill = (TextView) findViewById(R.id.note_fill);
        dateFill = (TextView) findViewById(R.id.date_fill);
        timeFill = (TextView) findViewById(R.id.time_fill);

        // Load the text into the TextViews
        addToDiaryDetail(title, note, date, time);
    }

    public void addToDiaryDetail(String titles, String notes, String dates, String times){
        titleFill.setText(titles);
        noteFill.setText(notes);
        dateFill.setText(dates);
        timeFill.setText(times);
    }

    @Override
    protected void onStart() {
        super.onStart();
        intent = getIntent();
//        titleFill.setText(title);
//        noteFill.setText(note);
//        dateFill.setText(date);
//        timeFill.setText(time);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        intent = getIntent();
//        titleFill.setText(title);
//        noteFill.setText(note);
//        dateFill.setText(date);
//        timeFill.setText(time);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_edit) {
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
