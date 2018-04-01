package com.cashbook.cashbook.flow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cashbook.cashbook.R;

import java.util.ArrayList;
import java.util.List;

public class FlowSpinnerAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mList = new ArrayList<String>();

    public FlowSpinnerAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList != null && mList.size() > 0 ? mList.size() : 0;
    }

    @Override
    public String getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.flow_spinner_item, null);
            holder.imageIv = (ImageView) view.findViewById(R.id.flow_spinner_item_iv);
            holder.infoTv = (TextView) view.findViewById(R.id.flow_spinner_item_tv);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.infoTv.setText(getItem(i));
        return view;
    }

    public class ViewHolder {
        public ImageView imageIv;
        public TextView infoTv;
    }
}
