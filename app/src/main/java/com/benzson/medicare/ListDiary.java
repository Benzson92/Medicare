package com.benzson.medicare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Benzson on 1/10/17.
 */

public class ListDiary extends Fragment {

    private View view;
    private ListView listView;
    private List<DiaryComponent> list;
    public static ListDiary listDiary;
    DiaryComponent diaryComponent;
    DatabaseHandler db;

    private Button addDiary;
    private Intent i;
    private Intent i2;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_diary, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("ListDiary", "OnActivityCreated");

        i = new Intent(getActivity(), WriteDiary.class);
        i2 = new Intent(getActivity(), DiaryDetail.class);

        //related to the view in xml
        view = getView();

        listDiary = this;
        db = new DatabaseHandler(getActivity());
        list = db.getAllDiaryComponent();
        for (DiaryComponent ld : list){
            Log.d("diaryComponent", "ID " + ld.getId());
        }
        //relate the listView from java to the one created in xml
        listView = (ListView) view.findViewById(R.id.listView);

        //show the ListView on the screen
        // The adapter MyListAdapter is responsible for maintaining the data backing this list and for producing
        // a view to represent an item in that data set.
        listView.setAdapter(new ListDiaryAdapter(getActivity(), list));

        // show DiaryDetail after clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                i2.putExtra("id", list.get(position).getId());
                i2.putExtra("title", list.get(position).getTitle());
                i2.putExtra("note", list.get(position).getNote());
                i2.putExtra("date", list.get(position).getDate());
                i2.putExtra("time", list.get(position).getTime());
                startActivity(i2);
            }
        });

        // delete list item after long clicked
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.d("position",position+"");
                Log.d("DiaryComponent_id", " " + list.get(position).getId());
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure to delete this item?");
                // Add the buttons
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        diaryComponent = db.getDiaryComponent(list.get(position).getId()); // id no. starts from 1
                        list.remove(position);
                        db.deleteDiaryComponent(diaryComponent);
                        db.updateDiaryComponent(diaryComponent);
                        listView.setAdapter(new ListDiaryAdapter(getActivity(), list));
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

        addDiary = (Button) view.findViewById(R.id.addDiary);
        addDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });

    }

    public void addToListView(String title, String note) {
        list = new ArrayList<DiaryComponent>();
        diaryComponent = new DiaryComponent();
        diaryComponent.setTitle(title);
        diaryComponent.setNote(note);
        diaryComponent.setDate(DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date()));
        diaryComponent.setTime(DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date()));
        list.add(diaryComponent);
        db.addDiaryComponent(diaryComponent); // add diaryComponent to database
        db.updateDiaryComponent(diaryComponent); // update database
        list = db.getAllDiaryComponent();
        for (DiaryComponent ld : list){
            Log.d("diaryComponent", "ID " + ld.getId());
        }
        listView.setAdapter(new ListDiaryAdapter(getActivity(), list)); // show all list items
    }

    public void addToListView2(int id, String title, String note, String date, String time) {
        list = new ArrayList<DiaryComponent>();
        diaryComponent = new DiaryComponent();
        diaryComponent.setId(id);
        diaryComponent.setTitle(title);
        diaryComponent.setNote(note);
        diaryComponent.setDate(date);
        diaryComponent.setTime(time);
        list.add(diaryComponent);
//        db.addDiaryComponent(diaryComponent); // add diaryComponent to database
        db.updateDiaryComponent(diaryComponent); // update database
        list = db.getAllDiaryComponent();
        for (DiaryComponent ld : list){
            Log.d("diaryComponent", "ID " + ld.getId());
        }
        listView.setAdapter(new ListDiaryAdapter(getActivity(), list)); // show all list items
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        i2 = new Intent(getActivity(), DiaryDetail.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        i2 = new Intent(getActivity(), DiaryDetail.class);
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
