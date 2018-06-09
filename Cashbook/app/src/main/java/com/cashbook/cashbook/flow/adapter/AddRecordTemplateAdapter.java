package com.cashbook.cashbook.flow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cashbook.cashbook.R;
import com.cashbook.cashbook.database.CashbookTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luruiqian on 2018/6/7.
 */

public class AddRecordTemplateAdapter extends BaseAdapter {
    private List<CashbookTemplate> mTemplateItemList = new ArrayList<>();
    private Context mContext;

    public AddRecordTemplateAdapter(List<CashbookTemplate> templateItemList, Context context) {
        this.mTemplateItemList = templateItemList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return (mTemplateItemList != null && mTemplateItemList.size() > 0) ? mTemplateItemList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mTemplateItemList.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_add_record_template, null);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.accountType = (TextView) convertView.findViewById(R.id.flow_add_record_template_type_tv);
        holder.accountName = (TextView) convertView.findViewById(R.id.flow_add_record_template_name_tv);
        holder.accountCount = (TextView) convertView.findViewById(R.id.flow_add_record_template_count_tv);
        holder.accountBeizhu = (TextView) convertView.findViewById(R.id.flow_add_record_template_beizhu_tv);
        CashbookTemplate templateItem = (CashbookTemplate) getItem(position);
        bindData(holder, templateItem);
        return convertView;
    }

    private void bindData(ViewHolder holder, CashbookTemplate templateItem) {
        holder.accountName.setText(templateItem.itemName);
        holder.accountType.setText(templateItem.type);
        holder.accountCount.setText(templateItem.money);
        holder.accountBeizhu.setText(templateItem.beizhu + "");
    }

    class ViewHolder {
        public TextView accountType;
        public TextView accountName;
        public TextView accountCount;
        public TextView accountBeizhu;
    }
}
