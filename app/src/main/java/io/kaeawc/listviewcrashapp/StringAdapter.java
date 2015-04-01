package io.kaeawc.listviewcrashapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StringAdapter extends BaseAdapter {

    private static final String TAG = StringAdapter.class.getCanonicalName();

    private LayoutInflater mInflater;
    private List<String> mStrings;
    private ViewHolder mHolder;

    public StringAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mStrings = new ArrayList<>();
    }

    public StringAdapter(Context context, List<String> strings) {
        mInflater = LayoutInflater.from(context);
        mStrings = strings;
    }

    public void add(String album)
    {
        Log.w(TAG, "add");
        mStrings.add(album);
        notifyDataSetChanged();
    }

    public void clear()
    {
        Log.w(TAG, "clear");
        mStrings.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        Log.w(TAG, "getCount : " + mStrings.size());
        return mStrings.size();
    }

    @Override
    public Object getItem(int position)
    {
        Log.w(TAG, "getItem");
        return mStrings.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        Log.w(TAG, "getItemId");
        return position;
    }

    private class ViewHolder
    {
        TextView  text_item;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Log.w(TAG, "getView");
        View rowView = convertView;

        if(rowView == null) {
            Log.i(TAG, "rowView is null");
            rowView = mInflater.inflate(R.layout.list_item, null);
            mHolder = new ViewHolder();
            mHolder.text_item = (TextView) rowView;
            rowView.setTag(mHolder);
        } else {
            Log.i(TAG, "rowView is not null");
            mHolder = (ViewHolder) rowView.getTag();
        }

        String text = mStrings.get(position);

        if (mHolder == null) {
            Log.w(TAG, "mHolder is null");
        } else {
            Log.w(TAG, "mHolder is not null");

            if (mHolder.text_item == null) {
                Log.w(TAG, "text_item is null");

            } else {
                Log.w(TAG, "text_item is not null");
                mHolder.text_item.setText(text);
            }
        }

        return rowView;
    }

}
