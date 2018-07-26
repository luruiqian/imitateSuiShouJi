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
    private ViewHolder holder = null;

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

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_account_item, null);
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
        holder.flowPayTv.setText(getItem(position).expend);
        holder.flowIncomeTv.setText(getItem(position).income);
        holder.flowAccountTimeTv.setText(getItem(position).time);
        holder.flowAccountSubTv.setText(getItem(position).recordSubscribe);
        holder.flowIncomeTv.setVisibility(getItem(position).isShowMoney ? View.VISIBLE : View.GONE);
        holder.flowPayTv.setVisibility(getItem(position).isShowMoney ? View.VISIBLE : View.GONE);
    }

    private void bindView(ViewHolder holder, View convertView) {
        holder.flowAccountImage = (ImageView) convertView.findViewById(R.id.adapter_account_iv);
        holder.flowAccountTimeTv = (TextView) convertView.findViewById(R.id.adapter_account_time_tv);
        holder.flowAccountSubTv = (TextView) convertView.findViewById(R.id.adapter_account_sub_tv);
        holder.flowIncomeTv = (TextView) convertView.findViewById(R.id.adapter_flow_income_tv);
        holder.flowPayTv = (TextView) convertView.findViewById(R.id.adapter_flow_pay_tv);
    }

    protected class ViewHolder {
        protected ImageView flowAccountImage;
        protected TextView flowAccountTimeTv;
        protected TextView flowAccountSubTv;
        protected TextView flowIncomeTv;
        protected TextView flowPayTv;
    }
}
