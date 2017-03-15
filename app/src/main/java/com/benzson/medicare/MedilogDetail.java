package com.benzson.medicare;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Benzson on 3/8/17.
 */

public class MedilogDetail extends AppCompatActivity {

    int id;
    String path;
    String name;
    String dose;
    String note;
    String time;
    String date;

    ImageView medImage;
    TextView medName;
    TextView dosage;
    TextView noteFill;
    TextView timeFill;
    TextView dateFill;

    Intent intent;
    Intent i;

    int imageSize = 180;

    public static MedilogDetail medilogDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medilog_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        medilogDetail = this;

        i = new Intent(this, EditMedilog.class);

        // Retrieve data from ListDiary on listview item click
        intent = getIntent();
        id = intent.getIntExtra("id", 0);
        path = intent.getStringExtra("path");
        name = intent.getStringExtra("name");
        dose = intent.getStringExtra("dose");
        note = intent.getStringExtra("note");
        time = intent.getStringExtra("time");
        date = intent.getStringExtra("date");

        i.putExtra("id2", id);
        i.putExtra("path2", path);
        i.putExtra("name2", name);
        i.putExtra("dose2", dose);
        i.putExtra("note2", note);
        i.putExtra("time2", time);
        i.putExtra("date2", date);

        // Locate the TextViews in medilog_detail.xml
        medImage = (ImageView) findViewById(R.id.medImage);
        medName = (TextView) findViewById(R.id.medName);
        dosage = (TextView) findViewById(R.id.dosage);
        noteFill = (TextView) findViewById(R.id.note_fill);
        timeFill = (TextView) findViewById(R.id.time_fill);
        dateFill = (TextView) findViewById(R.id.date_fill);

        // Load the text into the TextViews
        addToMedilogDetail(path, name, dose, note, time, date);

    }

    public void addToMedilogDetail(String paths, String names, String doses, String notes, String times, String dates){
        medImage.setImageBitmap(ThumbnailUtils
                .extractThumbnail(BitmapFactory.decodeFile(paths),
                        imageSize, imageSize));
        medName.setText(names);
        dosage.setText(doses + " tablet(s)");
        noteFill.setText(notes);
        timeFill.setText(times);
        dateFill.setText(dates);
    }

    @Override
    protected void onStart() {
        super.onStart();
        intent = getIntent();
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
