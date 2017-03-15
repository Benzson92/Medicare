package com.benzson.medicare;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Benzson on 2/18/17.
 */

public class AlarmDialog extends DialogFragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    Vibrator vibrator;

    public static AlarmDialog newInstance() {
        Bundle bundle = new Bundle();
        AlarmDialog alarmDialog = new AlarmDialog();
        alarmDialog.setArguments(bundle);
        return alarmDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get instance of Vibrator from current Context
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.alarm_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new AlarmAdapter(getActivity(), AlarmReceiver.alarmList));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(recyclerView);
        builder.setTitle("Take medicine?");
        builder.setCancelable(false);
        builder.setPositiveButton("Take", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
//                alarmManager.cancel(WriteMedilog.pendingIntent);
                vibrator.cancel();
                AlarmReceiver.ringtone.stop();
            }
        });
        return builder.create();
    }

    public void showDialog() {
        // Create the fragment and show it as a dialog.
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction();
        AlarmDialog alarmDialog = AlarmDialog.newInstance();
        alarmDialog.show(fragmentManager, "dialog");
    }



}
