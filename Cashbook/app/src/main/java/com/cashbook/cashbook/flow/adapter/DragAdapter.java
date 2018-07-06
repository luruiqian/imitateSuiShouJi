package com.cashbook.cashbook.flow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cashbook.cashbook.R;

import java.util.List;

/**
 * Created by luruiqian on 2018/7/6.
 */

public class DragAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mDragList;

    public DragAdapter(Context context, List<String> dragList) {
        mContext = context;
        mDragList = dragList;
    }

    @Override
    public int getCount() {
        return (mDragList != null && mDragList.size() > 0) ? mDragList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mDragList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.drag_grid_view_item, null);
            bindView(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        bindData(holder, (String) getItem(position));
        return convertView;
    }

    private void bindData(ViewHolder holder, String item) {
        holder.mDragTv.setText(item);
    }

    private void bindView(ViewHolder holder, View convertView) {
        holder.mDragTv = (TextView) convertView.findViewById(R.id.item_text);
    }

    protected class ViewHolder {
        private TextView mDragTv;
    }
}
