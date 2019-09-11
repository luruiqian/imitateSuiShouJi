package com.cashbook.cashbook.flow.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cashbook.cashbook.R;
import com.cashbook.cashbook.bean.AddRecordNewsList;
import com.cashbook.cashbook.constants.JavaConstant;

import java.util.List;

public class AddRecordBalanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<AddRecordNewsList> mList;
    private BigViewHolder mBigHolder;
    private SmallViewHolder mSmallHolder;
    private MulViewHolder mMulHolder;

    public AddRecordBalanceAdapter(Context context, List<AddRecordNewsList> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == JavaConstant.BIG_TYPE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_big_layout, null);
            return new BigViewHolder(view);
        } else if (viewType == JavaConstant.SMALL_TYPE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_small_layout, null);
            return new SmallViewHolder(view);
        } else if (viewType == JavaConstant.MUL_TYPE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_mul_layout, null);
            return new MulViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BigViewHolder) {
            BigViewHolder viewHolder = (BigViewHolder) holder;
            viewHolder.mBigTitle.setText(mList.get(position).title);
//            viewHolder.mBigImage.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_launcher));
        } else if (holder instanceof SmallViewHolder) {
            SmallViewHolder viewHolder = (SmallViewHolder) holder;
            viewHolder.mSmallTitle.setText(mList.get(position).title);
            viewHolder.mSmallImage.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_launcher));
        } else if (holder instanceof MulViewHolder) {
            MulViewHolder viewHolder = (MulViewHolder) holder;
            viewHolder.mMulTitle.setText(mList.get(position).title);
            viewHolder.mMullImage1.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_launcher));
            viewHolder.mMullImage2.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_launcher));
            viewHolder.mMullImage3.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_launcher));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (JavaConstant.BIG_STYLE.equals(mList.get(position).type)) {
            return JavaConstant.BIG_TYPE;
        } else if (JavaConstant.SMALL_STYLE.equals(mList.get(position).type)) {
            return JavaConstant.SMALL_TYPE;
        } else if (JavaConstant.MUL_STYLE.equals(mList.get(position).type)) {
            return JavaConstant.MUL_TYPE;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return mList.size() > 0 ? mList.size() : 0;
    }

    class BigViewHolder extends RecyclerView.ViewHolder {
        private TextView mBigTitle;
        private ImageView mBigImage;

        public BigViewHolder(View itemView) {
            super(itemView);
            mBigTitle = (TextView) itemView.findViewById(R.id.big_title);
            mBigImage = (ImageView) itemView.findViewById(R.id.big_iv);
        }
    }

    class SmallViewHolder extends RecyclerView.ViewHolder {
        private TextView mSmallTitle;
        private ImageView mSmallImage;

        public SmallViewHolder(View itemView) {
            super(itemView);
            mSmallTitle = (TextView) itemView.findViewById(R.id.small_title);
            mSmallImage = (ImageView) itemView.findViewById(R.id.small_sub_iv);
        }
    }

    class MulViewHolder extends RecyclerView.ViewHolder {
        private TextView mMulTitle;
        private ImageView mMullImage1;
        private ImageView mMullImage2;
        private ImageView mMullImage3;

        public MulViewHolder(View itemView) {
            super(itemView);
            mMulTitle = (TextView) itemView.findViewById(R.id.mul_title);
            mMullImage1 = (ImageView) itemView.findViewById(R.id.mul_img01);
            mMullImage2 = (ImageView) itemView.findViewById(R.id.mul_img02);
            mMullImage3 = (ImageView) itemView.findViewById(R.id.mul_img03);
        }
    }
}
