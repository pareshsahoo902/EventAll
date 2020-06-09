package com.otta.eventall;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class SpinnerAdapter extends BaseAdapter {

    LayoutInflater inflater;
    ArrayList<String> entries;


    public SpinnerAdapter(Context context, ArrayList<String> entries) {
        this.inflater = LayoutInflater.from(context);
        this.entries = entries;
    }

    @Override
    public int getCount() {
        return entries.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflater.inflate(R.layout.item_spinner_layout , null);
        TextView Item = view.findViewById(R.id.Text_Item);
        Item.setText(entries.get(i));
        return view;
    }
}
