package com.benzson.medicare;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Benzson on 3/11/17.
 */

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {

    private List<AlarmComponent> mListItem;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private final int THUMBSIZE = 96;

    public AlarmAdapter(Context context, List<AlarmComponent> alarmComponentList) {
        mListItem = alarmComponentList;
        mContext = context;
//        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_item, parent, false);
        AlarmViewHolder alarmViewHolder = new AlarmViewHolder(itemView);
        return alarmViewHolder;
    }

    @Override
    public void onBindViewHolder(AlarmViewHolder holder, int position) {
        AlarmComponent alarmComponent = mListItem.get(position);
        holder.item_photo.setImageBitmap(ThumbnailUtils
                .extractThumbnail(BitmapFactory.decodeFile(alarmComponent.getPath()),
                        THUMBSIZE, THUMBSIZE));
        holder.item_name.setText(alarmComponent.getName());
        holder.item_dose.setText(alarmComponent.getDose() + " tablet(s)");
        holder.item_time.setText(alarmComponent.getTime());
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

    public static class AlarmViewHolder extends RecyclerView.ViewHolder {

        protected ImageView item_photo;
        protected TextView item_name;
        protected TextView item_dose;
        protected TextView item_time;

        public AlarmViewHolder(View itemView) {
            super(itemView);
            item_photo = (ImageView) itemView.findViewById(R.id.med_photo);
            item_name = (TextView) itemView.findViewById(R.id.med_name);
            item_dose = (TextView) itemView.findViewById(R.id.med_dose);
            item_time = (TextView) itemView.findViewById(R.id.med_time);
        }

    }
}
