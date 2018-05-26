package com.cashbook.cashbook.flow.fragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cashbook.cashbook.flow.bean.AddDetail;

import java.util.List;

/**
 * Created by luruiqian on 2018/5/26.
 */

public class AddRecordItemAdapter extends BaseAdapter {
    private Context mContext;
    private List<AddDetail> mAddDetailList;

    public AddRecordItemAdapter(Context context,List<AddDetail> addDetailList) {
        this.mContext = context;
        this.mAddDetailList = addDetailList;
    }

    @Override
    public int getCount() {
        return mAddDetailList == null ? 0 : mAddDetailList.size();
    }

    @Override
    public Object getItem(int i) {
        return mAddDetailList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
        }
        return null;
    }

    protected class ViewHolder{

    }
}
