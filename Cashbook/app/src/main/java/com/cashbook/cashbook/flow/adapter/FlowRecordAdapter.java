package com.cashbook.cashbook.flow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cashbook.cashbook.R;
import com.cashbook.cashbook.flow.bean.AccountInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luruiqian on 2018/3/27.
 */

public class FlowRecordAdapter extends BaseAdapter {
    private Context mContext;
    private List<AccountInfo> mList = new ArrayList();

    public FlowRecordAdapter(Context context, List<AccountInfo> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return (mList != null && mList.size() > 0) ? mList.size() : 0;
    }

    @Override
    public AccountInfo getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (holder == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_account_item, null);
            holder = new ViewHolder();
            bindView(holder, position);
            convertView.setTag(holder);
        } else {
            convertView.getTag();
        }
        return convertView;
    }

    private void bindView(ViewHolder holder, int position) {
        holder.flowAccountTimeTv.setText(getItem(position).time);
        holder.flowAccountSubTv.setText(getItem(position).recordSubscribe);
        holder.flowIncomeTv.setText(getItem(position).income);
        holder.flowPayTv.setText(getItem(position).expend);
    }

    public class ViewHolder {
        public TextView flowAccountTimeTv;
        public TextView flowAccountSubTv;
        public TextView flowIncomeTv;
        public TextView flowPayTv;
        public ImageView flowAccountImage;
    }
}
