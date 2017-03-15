package com.benzson.medicare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Benzson on 3/6/17.
 */

public class EditDiary extends AppCompatActivity {

    private EditText editTitle;
    private EditText editNote;
    private String title;
    private String note;

    private int id2;
    private String title2;
    private String note2;
    private String date2;
    private String time2;

    private Button saveDiary;
    private Intent intent2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_diary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Retrieve data from ListDiary on listview item click
        intent2 = getIntent();
        id2 = intent2.getIntExtra("id2", 0);
        title2 = intent2.getStringExtra("title2");
        note2 = intent2.getStringExtra("note2");
//        date2 = intent2.getStringExtra("date2");
//        time2 = intent2.getStringExtra("time2");

        editTitle = (EditText) findViewById(R.id.editTitle);
        editNote = (EditText) findViewById(R.id.editNote);
        saveDiary = (Button) findViewById(R.id.saveDiary);

        editTitle.setText(title2);
        editNote.setText(note2);

        saveDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = editTitle.getText().toString();
                note = editNote.getText().toString();
                Log.d("title", title);
                Log.d("note", note);
                date2 = DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date());
                time2 = DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date());
                DiaryDetail.diaryDetail.addToDiaryDetail(title, note, date2, time2);
                Intent intent = new Intent(EditDiary.this, MainActivity.class);
                ListDiary.listDiary.addToListView2(id2, title, note, date2, time2);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
