package com.benzson.medicare;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Benzson on 1/10/17.
 */

public class Medilog extends Fragment {

    private View view;
    private ListView listView;
    private List<MedilogComponent> list;
    public static Medilog medilog;
    private MedilogComponent medilogComponent;
    private MedDatabaseHandler db;

    private TextView showDate;
    private Button addMed;
    private Intent i;
    private Intent i2;

    private SimpleDateFormat simpleDateFormat;

    AlarmManager alarmManager;
    Vibrator vibrator;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.medilog, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("Medilog", "OnActivityCreated");

        i = new Intent(getActivity(), WriteMedilog.class);
        i2 = new Intent(getActivity(), MedilogDetail.class);

        alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        //related to the view in xml
        view = getView();

        medilog = this;
        db = new MedDatabaseHandler(getActivity());
        list = db.getAllMedilogComponent();
        for (MedilogComponent ld : list){
            Log.d("medilogComponent", "ID " + ld.getId());
        }

        //relate the listView from java to the one created in xml
        listView = (ListView) view.findViewById(R.id.listView);

        //show the ListView on the screen
        // The adapter MyListAdapter is responsible for maintaining the data backing this list and for producing
        // a view to represent an item in that data set.
        listView.setAdapter(new MedilogAdapter(getActivity(), list));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                i2.putExtra("id", list.get(position).getId());
                i2.putExtra("path", list.get(position).getPath());
                i2.putExtra("name", list.get(position).getName());
                i2.putExtra("dose", list.get(position).getDose());
                i2.putExtra("note", list.get(position).getNote());
                i2.putExtra("time", list.get(position).getTime());
                i2.putExtra("date", list.get(position).getDate());
                startActivity(i2);
            }
        });

        // delete list item after long clicked
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.d("position",position+"");
                Log.d("MedilogComponent_id", " " + list.get(position).getId());
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure to delete this item?");

                // Add the buttons
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        medilogComponent = db.getMedilogComponent(list.get(position).getId()); // id no. starts from 1
                        alarmManager.cancel(WriteMedilog.pendingIntent);
                        list.remove(position);
                        db.deleteMedilogComponent(medilogComponent);
                        db.updateMedilogComponent(medilogComponent);
//                        vibrator.cancel();
//                        AlarmReceiver.ringtone.stop();
                        listView.setAdapter(new MedilogAdapter(getActivity(), list));
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });

        simpleDateFormat = new SimpleDateFormat("EEE dd, MMMMMMMMM", Locale.US);
        showDate = (TextView) view.findViewById(R.id.showDate);
        showDate.setText(simpleDateFormat.format(new Date()));


        addMed = (Button) view.findViewById(R.id.addMed);
        addMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });
    }

    public void addToListView(String path, String name, String dose, String note, String time, String date) {
        list = new ArrayList<MedilogComponent>();
        medilogComponent = new MedilogComponent();
        medilogComponent.setPath(path);
        medilogComponent.setName(name);
        medilogComponent.setDose(dose);
        medilogComponent.setNote(note);
        medilogComponent.setTime(time);
        medilogComponent.setDate(date);
        list.add(medilogComponent);
        db.addMedilogComponent(medilogComponent); // add medilogComponent to database
        db.updateMedilogComponent(medilogComponent); // update database
        list = db.getAllMedilogComponent();
        for (MedilogComponent ld : list){
            Log.d("medilogComponent", "ID " + ld.getId());
        }
        listView.setAdapter(new MedilogAdapter(getActivity(), list)); // show all list items
    }

    public void addToListView2(int id, String path, String name, String dose, String note, String time, String date) {
        list = new ArrayList<MedilogComponent>();
        medilogComponent = new MedilogComponent();
        medilogComponent.setId(id);
        medilogComponent.setPath(path);
        medilogComponent.setName(name);
        medilogComponent.setDose(dose);
        medilogComponent.setNote(note);
        medilogComponent.setTime(time);
        medilogComponent.setDate(date);
        list.add(medilogComponent);
//        db.addMedilogComponent(medilogComponent); // add medilogComponent to database
        db.updateMedilogComponent(medilogComponent); // update database
        list = db.getAllMedilogComponent();
        for (MedilogComponent ld : list){
            Log.d("medilogComponent", "ID " + ld.getId());
        }
        listView.setAdapter(new MedilogAdapter(getActivity(), list)); // show all list items
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
