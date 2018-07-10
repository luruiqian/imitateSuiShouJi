package com.cashbook.cashbook.flow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cashbook.cashbook.R;
import com.cashbook.cashbook.flow.bean.DragInfo;

import java.util.HashMap;
import java.util.List;

/**
 * Created by luruiqian on 2018/7/6.
 */

public class DragAdapter extends BaseAdapter implements View.OnClickListener {
    private Context mContext;
    private int mSelectedPosition;
    private List<HashMap<String, DragInfo>> mDragList;
    private TextView mDragTv;

    public DragAdapter(Context context, List<HashMap<String, DragInfo>> dataSourceList, int position) {
        mContext = context;
        mDragList = dataSourceList;
        mSelectedPosition = position;
    }

    @Override
    public int getCount() {
        return (mDragList != null && mDragList.size() > 0) ? mDragList.size() : 0;
    }

    @Override
    public DragInfo getItem(int position) {
        return mDragList.get(position).get("dragInfo");
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.drag_grid_view_item, null);
            bindView(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == mSelectedPosition) {
            getItem(position).isSelect = true;
            mSelectedPosition = position;
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getItem(position).isSelect = true;
                getItem(mSelectedPosition).isSelect = false;

                mSelectedPosition = position;
            }
        });
        bindData(holder, (DragInfo) getItem(position));
        return convertView;
    }

    private void bindData(ViewHolder holder, DragInfo item) {
        holder.mDragTv.setText(item.dragName);
        holder.mDragTv.setSelected(item.isSelect);
    }

    private void bindView(ViewHolder holder, View convertView) {
        holder.mDragTv = (TextView) convertView.findViewById(R.id.item_text);
    }

    @Override
    public void onClick(View v) {
//        if (position != mSelectedPosition) {
//            getItem(mSelectedPosition).isSelect = false;
//            getItem(position).isSelect = true;
//            mSelectedPosition = position;
//        }
    }

    protected class ViewHolder {
        private TextView mDragTv;
    }
}
