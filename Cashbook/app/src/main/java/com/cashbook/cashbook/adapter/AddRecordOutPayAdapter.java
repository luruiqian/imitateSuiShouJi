package com.cashbook.cashbook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cashbook.cashbook.R;

import java.util.List;

/**
 * Created by luruiqian on 2018/7/26.
 */

public class AddRecordOutPayAdapter extends RecyclerView.Adapter<AddRecordOutPayAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mList;

    public AddRecordOutPayAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.adapter_add_record_out_pay_item, null);
        //实例化ViewHolder
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String text = mList.get(position);
        holder.text.setText(text);
        holder.text.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return (mList != null && mList.size() > 0) ? mList.size() : 0;
    }

    /**
     * 内部类
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        //行布局中的控件
        ImageView img;
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            //绑定控件
            img = (ImageView) itemView.findViewById(R.id.add_record_out_pay_item_iv);
            text = (TextView) itemView.findViewById(R.id.add_record_out_pay_item_tv);
        }
    }
}
