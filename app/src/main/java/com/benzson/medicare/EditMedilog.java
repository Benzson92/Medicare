package com.benzson.medicare;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Benzson on 3/8/17.
 */

public class EditMedilog extends AppCompatActivity {

    private EditText editMed;
    private EditText editDose;
    private EditText editNote;
    private String med;
    private String dose;
    private String note;
    private String time;

    private int hour;
    private int minutes;
    private int startYear;
    private int startMonth;
    private int startDay;
    private int endYear;
    private int endMonth;
    private int endDay;
    private String dateName;

    private ImageButton medImage;
    private Button addTime;
    private Button chooseDate;
//    private Button endDate;
    private Button saveMedilog;

    private Dialog imageDialog;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private Uri mCapturedImageURI;
//    private String picturePath;

    private Calendar calendar;
    private Calendar calStart;
    private Calendar calEnd;
//    private long startMillis;
//    private long endMillis;
//    private long diffDays;

    private TimePickerDialog timePickerDialog;
    private DatePickerDialog chooseDateDialog;
//    private DatePickerDialog endDateDialog;
    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat dateListFormat;
    private SimpleDateFormat simpleTimeFormat;

    private Intent i;
    private Intent intent;
    private Intent intent2;
    public static PendingIntent pendingIntent;
    AlarmManager alarmManager;

    private int id2;
    private String path2;
    private String name2;
    private String dose2;
    private String note2;
    private String time2;
    private String date2;

    int imageSize = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_medilog);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Retrieve data from ListDiary on listview item click
        intent2 = getIntent();
        id2 = intent2.getIntExtra("id2", 0);
        path2 = intent2.getStringExtra("path2");
        name2 = intent2.getStringExtra("name2");
        dose2 = intent2.getStringExtra("dose2");
        note2 = intent2.getStringExtra("note2");
        time2 = intent2.getStringExtra("time2");
        date2 = intent2.getStringExtra("date2");

        i = new Intent(this, MainActivity.class);
        intent = new Intent(EditMedilog.this, AlarmReceiver.class);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        calendar = Calendar.getInstance();
        calStart = Calendar.getInstance();
        calEnd = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("d MMM yyyy", Locale.US);
        dateListFormat = new SimpleDateFormat("EEE d MMM", Locale.US);
        simpleTimeFormat = new SimpleDateFormat("HH:mm ", Locale.US);

        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                newDate.set(Calendar.MINUTE, minute);
                addTime.setText(simpleTimeFormat.format(newDate.getTime()));
                hour = hourOfDay;
                minutes = minute;
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

        chooseDateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                chooseDate.setText(simpleDateFormat.format(newDate.getTime()));
                startYear = year;
                startMonth = monthOfYear;
                startDay = dayOfMonth;
                calStart.setTime(newDate.getTime());
//                startMillis = newDate.getTimeInMillis();
            }

        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

//        endDateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                Calendar newDate = Calendar.getInstance();
//                newDate.set(year, monthOfYear, dayOfMonth);
//                endDate.setText(simpleDateFormat.format(newDate.getTime()));
//                endYear = year;
//                endMonth = monthOfYear;
//                endDay = dayOfMonth;
//                calEnd.setTime(newDate.getTime());
////                endMillis = newDate.getTimeInMillis();
//            }
//
//        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        editMed = (EditText) findViewById(R.id.editMed);
        editDose = (EditText) findViewById(R.id.editDose);
        editNote = (EditText) findViewById(R.id.editNote);

        medImage = (ImageButton) findViewById(R.id.medImage);
        addTime = (Button) findViewById(R.id.addTime);
        chooseDate = (Button) findViewById(R.id.chooseDate);
//        endDate = (Button) findViewById(R.id.endDate);
        saveMedilog = (Button) findViewById(R.id.saveMedilog);

        medImage.setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(path2),
                imageSize, imageSize));
        editMed.setText(name2);
        editDose.setText(dose2);
        editNote.setText(note2);
//        addTime.setText(time2);
//        startDate.setText(date2);

        imageDialog = new Dialog(this);

        medImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageDialog.setContentView(R.layout.image_dialog);
                imageDialog.setTitle("Choose Image");
                imageDialog.findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageDialog.dismiss();
                    }
                });
//                imageDialog.findViewById(R.id.chooseDefault).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                        medImage.setImageResource(R.drawable.pill_icon);
//                    }
//                });
                imageDialog.findViewById(R.id.choosePath).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activeGallery();
                    }
                });
                imageDialog.findViewById(R.id.takePhoto).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activeTakePhoto();
                    }
                });

                // show dialog on screen
                imageDialog.show();
            }
        });

        addTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });

        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDateDialog.show();
            }
        });

//        endDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                endDateDialog.show();
//            }
//        });

        saveMedilog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                med = editMed.getText().toString();
                dose = editDose.getText().toString();
                note = editNote.getText().toString();
                time = addTime.getText().toString();
                Log.d("med", med);
                Log.d("dose", dose);
                Log.d("note", note);
                Log.d("time", time);

//                diffDays = (endMillis - startMillis)/(24 * 60 * 60 * 1000);
//                int x = 0;
//                for (Date date = calStart.getTime(); !calStart.after(calStart); calStart.add(Calendar.DATE, 1), date = calStart.getTime()) {
                Calendar newTime = Calendar.getInstance();
                newTime.set(startYear, startMonth, startDay, hour, minutes, 0);
                newTime.set(Calendar.MILLISECOND, 0);
                dateName = dateListFormat.format(calStart.getTime());
                Bundle bundle = new Bundle();
                bundle.putString("picturePath", path2);
                bundle.putString("medName", med);
                bundle.putString("dose", dose);
                bundle.putString("time", time);
                bundle.putString("date", dateName);
                intent.putExtras(bundle);
                pendingIntent = PendingIntent.getBroadcast(EditMedilog.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                Medilog.medilog.addToListView2(id2, path2, med, dose, note, time, dateName);
                MedilogDetail.medilogDetail.addToMedilogDetail(path2, med, dose, note, time, dateName);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, newTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
//                    Log.d("date", date + "");
                Log.d("calStart", calStart.getTime() + "");
//                    x++;
//                }
                startActivity(i);
            }
        });
    }

    /**
     * to gallery
     */
    private void activeGallery() {
        Intent intents = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intents, RESULT_LOAD_IMAGE);
    }

    /**
     * take a photo
     */
    private void activeTakePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            String fileName = "temp.jpg";
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, fileName);
            mCapturedImageURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override protected void onActivityResult(int requestCode, int resultCode,
                                              Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_LOAD_IMAGE:
                if (requestCode == RESULT_LOAD_IMAGE &&
                        resultCode == RESULT_OK && null != data) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver()
                            .query(selectedImage, filePathColumn, null, null,
                                    null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    path2 = cursor.getString(columnIndex);
                    cursor.close();
                    MedilogComponent medilogComponent = new MedilogComponent();
                    medilogComponent.setPath(path2);
                    medImage.setImageBitmap(ThumbnailUtils
                            .extractThumbnail(BitmapFactory.decodeFile(medilogComponent.getPath()),
                                    medImage.getWidth(), medImage.getHeight()));
                }
            case REQUEST_IMAGE_CAPTURE:
                if (requestCode == REQUEST_IMAGE_CAPTURE &&
                        resultCode == RESULT_OK) {
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor =
                            managedQuery(mCapturedImageURI, projection, null,
                                    null, null);
                    int column_index_data = cursor.getColumnIndexOrThrow(
                            MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    path2 = cursor.getString(column_index_data);
                    MedilogComponent medilogComponent = new MedilogComponent();
                    medilogComponent.setPath(path2);
                    medImage.setImageBitmap(ThumbnailUtils
                            .extractThumbnail(BitmapFactory.decodeFile(medilogComponent.getPath()),
                                    medImage.getWidth(), medImage.getHeight()));
                }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
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
    }
}
