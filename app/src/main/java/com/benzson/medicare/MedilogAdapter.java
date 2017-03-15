package com.benzson.medicare;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Benzson on 1/21/17.
 */

public class MedilogAdapter extends BaseAdapter {

    private List<MedilogComponent> mListItem;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private final int THUMBSIZE = 96;

    public MedilogAdapter(Context context, List<MedilogComponent> detailList){

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

            convertView = mLayoutInflater.inflate(R.layout.medilog_item, parent, false);

            // get all views you need to handle from the cell and save them in the view holder
            holder.item_photo = (ImageView) convertView.findViewById(R.id.item_photo);
            holder.item_name = (TextView) convertView.findViewById(R.id.item_name);
            holder.item_dose = (TextView) convertView.findViewById(R.id.item_dose);
            holder.item_time = (TextView) convertView.findViewById(R.id.item_time);
            holder.item_date = (TextView) convertView.findViewById(R.id.item_date);

            // save the view holder on the cell view to get it back latter
            convertView.setTag(holder);
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder) convertView.getTag();
        }

        //get the string item from the position "position" from array list to put it on the TextView
        MedilogComponent medilogComponent = (MedilogComponent) getItem(position);
        if (medilogComponent != null) {
            //set the item name on the TextView
            holder.item_photo.setImageBitmap(ThumbnailUtils
                    .extractThumbnail(BitmapFactory.decodeFile(medilogComponent.getPath()),
                            THUMBSIZE, THUMBSIZE));
            holder.item_name.setText(medilogComponent.getName());
            holder.item_dose.setText(medilogComponent.getDose() + " tablet(s)");
            holder.item_time.setText(medilogComponent.getTime());
            holder.item_date.setText(medilogComponent.getDate());
        } else {
            // make sure that when you have an if statement that alters the UI, you always have an else that sets a default value back, otherwise you will find that the recycled items will have the same UI changes
            holder.item_photo.setImageResource(R.drawable.pill_icon);
            holder.item_name.setText("Unknown");
            holder.item_dose.setText("Unknown");
            holder.item_time.setText("Unknown");
            holder.item_date.setText("Unknown");
        }

        //this method must return the view corresponding to the data at the specified position.
        return convertView;
    }

    static class ViewHolder {
        ImageView item_photo;
        TextView item_name;
        TextView item_dose;
        TextView item_time;
        TextView item_date;
    }
}
