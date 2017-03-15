package com.benzson.medicare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by Benzson on 3/13/17.
 */

public class AlarmDialog2 extends AppCompatActivity {

    static RecyclerView recyclerView;
    private Button takeAll;
    private LinearLayoutManager layoutManager;
    private Intent intent;
    public static AlarmDialog2 alarmDialog2;
    private AlarmDatabaseHandler dbAlarm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_list);
        alarmDialog2 = this;
        dbAlarm = new AlarmDatabaseHandler(this);
        AlarmReceiver.alarmList = dbAlarm.getAllAlarmComponent();

        intent = new Intent(this, MainActivity.class);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new AlarmAdapter(this, AlarmReceiver.alarmList));

        takeAll = (Button) findViewById(R.id.takeAll);
        takeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmReceiver.vibrator.cancel();
                AlarmReceiver.ringtone.stop();
                AlarmReceiver.alarmList.clear();
                dbAlarm.deleteAllAlarmComponent();
//                dbAlarm.updateAllAlarmComponent();
                startActivity(intent);
            }
        });
    }

}
