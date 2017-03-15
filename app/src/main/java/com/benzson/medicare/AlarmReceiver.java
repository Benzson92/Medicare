package com.benzson.medicare;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Benzson on 12/12/16.
 */

public class AlarmReceiver extends WakefulBroadcastReceiver {
    Uri alarmUri;
    ComponentName comp;
    public static Ringtone ringtone;
    MainActivity inst;
    public static Vibrator vibrator;
//    AlertDialog.Builder builder;
    public static List<AlarmComponent> alarmList;
    private AlarmComponent alarmComponent;
    private AlarmDatabaseHandler dbAlarm;

    private List<MedilogComponent> mediList;
    private MedDatabaseHandler dbMed;
//    int dbCount;
    int count = 0;

    Window window;

    String path;
    String name;
    String dose;
    String time;
    String date;

    @Override
    public void onReceive(Context context, Intent intent) {

        //this will update the UI with message
        inst = MainActivity.instance();

        path = intent.getStringExtra("picturePath");
        name = intent.getStringExtra("medName");
        dose = intent.getStringExtra("dose");
        time = intent.getStringExtra("time");
        date = intent.getStringExtra("date");

        alarmList = new ArrayList<AlarmComponent>();
        alarmComponent = new AlarmComponent();
        dbAlarm = new AlarmDatabaseHandler(context);

        dbMed = new MedDatabaseHandler(context);
        mediList = dbMed.getAllMedilogComponent();
//        dbCount = dbMed.getMedilogComponentCount();

        for (int i = 0; i < mediList.size(); i++) {
            Log.d("getTime", mediList.get(i).getTime() + "");
            Log.d("getDate", mediList.get(i).getDate() + "");
            if ((mediList.get(i).getTime().equals(time)) && (mediList.get(i).getDate().equals(date))) {
                Log.d("getTimee", mediList.get(i).getTime() + "");
                Log.d("timee", time + "");
                Log.d("getDatee", mediList.get(i).getDate() + "");
                Log.d("datee", date + "");
                Log.d("medilist", mediList.get(i) + "");
                alarmComponent.setPath(mediList.get(i).getPath());
                alarmComponent.setName(mediList.get(i).getName());
                Log.d("getDate", mediList.get(i).getName() + "");
                alarmComponent.setDose(mediList.get(i).getDose());
                alarmComponent.setTime(mediList.get(i).getTime());
                alarmList.add(alarmComponent);
                dbAlarm.addAlarmComponent(alarmComponent); // add alarmComponent to database
                dbAlarm.updateAlarmComponent(alarmComponent); // update database
                alarmList = dbAlarm.getAllAlarmComponent();
                Log.d("lists", alarmList + "");
            }
            count++;
            Log.d("count", count + "");
        }

//        builder = new AlertDialog.Builder(MainActivity.instance());
//        builder.setTitle("med name");
//        builder.setMessage("Take medicine?");
//
//        // Add the buttons
//        builder.setCancelable(false);
//        builder.setPositiveButton("Take", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                // User clicked OK button
////                alarmManager.cancel(WriteMedilog.pendingIntent);
//                vibrator.cancel();
//                AlarmReceiver.ringtone.stop();
//            }
//        });

        // Get instance of Vibrator from current Context
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        // Start immediately
        // Vibrate for 200 milliseconds
        // Sleep for 0 milliseconds
        long[] pattern = {0, 200, 400};

        // The "0" means to repeat the pattern starting at the beginning and -1 means do not stop
        // You will vibrate for your pause times and pause for your vibrate times!
        vibrator.vibrate(pattern, 0);

//        inst.setAlarmText("Alarm! Wake up! Wake up!");

        //this will sound the alarm tone
        //this will sound the alarm once, if you wish to
        //raise alarm in loop continuously then use MediaPlayer and setLooping(true)
        alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        if (alarmUri == null) {
//            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        }
        ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();

        //this will send a notification message
        comp = new ComponentName(context.getPackageName(), AlarmService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));

        window = MainActivity.instance().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        Intent alarmIntent = new Intent("android.intent.action.MAIN");
        alarmIntent.setClass(context, AlarmDialog2.class);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(alarmIntent);

//        // Create the AlertDialog
//        AlertDialog dialog = builder.create();
//        dialog.show();

//        AlarmDialog.newInstance().showDialog();

//        AlarmDialog2.recyclerView.setAdapter(new AlarmAdapter(new AlarmDialog2(), list));

        setResultCode(Activity.RESULT_OK);
    }

}
