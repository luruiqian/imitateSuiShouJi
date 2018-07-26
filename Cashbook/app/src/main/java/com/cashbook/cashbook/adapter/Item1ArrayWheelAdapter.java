package com.cashbook.cashbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cashbook.cashbook.R;

import java.util.List;

/**
 * Created by luruiqian on 2018/7/24.
 */

public class Item1ArrayWheelAdapter<T> implements WheelAdapter {
    private Context mContext;
    private List<T> mItems;

    public Item1ArrayWheelAdapter(List<T> items, Context context) {
        mContext = context;
        mItems = items;
    }

    @Override
    public int getItemsCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int index) {
        if (index >= 0 && index < mItems.size()) {
            return mItems.get(index);
        }
        return "";
    }

    @Override
    public int indexOf(Object o) {
        return mItems.indexOf(o);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.wheel_view_item, null);
            holder = new ViewHolder();
            bindView(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        bindData(holder, position);
        return convertView;
    }

    private void bindData(ViewHolder holder, int position) {
        holder.wheelItemPayTv.setText((CharSequence) mItems.get(position));
        holder.wheelItemNameTv.setText((CharSequence) mItems.get(position));
    }

    private void bindView(ViewHolder holder, View convertView) {
        holder.wheelIv = (ImageView) convertView.findViewById(R.id.adapter_wheel_iv);
        holder.wheelItemPayTv = (TextView) convertView.findViewById(R.id.adapter_wheel_pay_tv);
        holder.wheelItemNameTv = (TextView) convertView.findViewById(R.id.adapter_wheel_name_tv);
    }

    protected class ViewHolder {
        protected ImageView wheelIv;
        protected TextView wheelItemNameTv;
        protected TextView wheelItemPayTv;
    }
}
