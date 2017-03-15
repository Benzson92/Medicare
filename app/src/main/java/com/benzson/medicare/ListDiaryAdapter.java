package com.benzson.medicare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Benzson on 1/13/17.
 */

public class ListDiaryAdapter extends BaseAdapter {

    private List<DiaryComponent> mListItem;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public ListDiaryAdapter(Context context, List<DiaryComponent> detailList){

        mListItem = detailList;
        mContext = context;

        //get the layout inflater
//        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        //getCount() represents how many items are in the list
        return mListItem.size();
    }

    @Override
    public Object getItem(int position) {
        //get the data of an item from a specific position
        //i represents the position of the item in the list
        return mListItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        //get the position id of the item from the list
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // create a ViewHolder reference
        ViewHolder holder;

        //check to see if the reused view is null or not, if is not null then reuse it
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = mLayoutInflater.inflate(R.layout.listdiary_item, parent, false);

            // get all views you need to handle from the cell and save them in the view holder
            holder.item_title = (TextView) convertView.findViewById(R.id.item_title);
            holder.item_date = (TextView) convertView.findViewById(R.id.item_date);
            holder.item_time = (TextView) convertView.findViewById(R.id.item_time);

            // save the view holder on the cell view to get it back latter
            convertView.setTag(holder);
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder) convertView.getTag();
        }

        //get the string item from the position "position" from array list to put it on the TextView
        DiaryComponent diaryComponent = (DiaryComponent) getItem(position);
        if (diaryComponent != null) {
            //set the item name on the TextView
            holder.item_title.setText(diaryComponent.getTitle());
            holder.item_date.setText(diaryComponent.getDate());
            holder.item_time.setText(diaryComponent.getTime());
        } else {
            // make sure that when you have an if statement that alters the UI, you always have an else that sets a default value back, otherwise you will find that the recycled items will have the same UI changes
            holder.item_title.setText("Unknown");
            holder.item_date.setText("Unknown");
            holder.item_time.setText("Unknown");
        }

        //this method must return the view corresponding to the data at the specified position.
        return convertView;
    }

    static class ViewHolder {
        TextView item_title;
        TextView item_date;
        TextView item_time;
    }
}
