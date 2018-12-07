package com.cashbook.cashbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cashbook.cashbook.R;
import com.cashbook.cashbook.flow.bean.MyFootPrintBean;

import java.util.List;


/**
 * @author luruiqian 客服聊天我的浏览adapter
 */
public class MyBrowseItemAdapter extends BaseAdapter {
    private Context mContext;
    private List<MyFootPrintBean> mBrowseList;

    public MyBrowseItemAdapter(Context context,List<MyFootPrintBean> browseList) {
        mContext = context;
        mBrowseList = browseList;
    }

    private void bindData(ViewHolder holder, int i) {
        MyFootPrintBean printBean = mBrowseList.get(i);
//        ImageUtils.with(mContext).loadListImage(printBean.imgUrl, holder.mOrderImage);
        holder.mBrowseProductNameTv.setText(printBean.produceName);
        holder.mBrowseProductPriceTv.setText(printBean.salePrice);
        //有库存，隐藏无货图片；无库存，显示无货图片
        if (mContext.getString(R.string.im_customer_service_yes).equals(printBean.stockState)) {
            holder.mNoGoodImage.setVisibility(View.GONE);
        } else if (mContext.getString(R.string.im_customer_service_no).equals(printBean.stockState)) {
            holder.mNoGoodImage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getCount() {
        return (mBrowseList != null && mBrowseList.size() > 0) ? mBrowseList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mBrowseList != null && mBrowseList.size() > 0 ? mBrowseList.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyBrowseItemAdapter.ViewHolder holder = null;
        if (convertView == null) {
            holder = new MyBrowseItemAdapter.ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.im_chat_product_browser_item_adapter, null);
            holder.mNoGoodImage = (ImageView) convertView.findViewById(R.id.im_chat_product_browse_no_goods);
//            holder.mOrderImage = (FrescoDraweeView) convertView.findViewById(R.id.im_chat_product_browse_image);
            holder.mBrowseProductNameTv = (TextView) convertView.findViewById(R.id.im_chat_product_browse_item_name_tv);
            holder.mBrowseProductPriceTv = (TextView) convertView.findViewById(R.id.im_chat_product_browse_item_price_tv);
            convertView.setTag(holder);
        } else {
            holder = (MyBrowseItemAdapter.ViewHolder) convertView.getTag();
        }
        bindData(holder, position);
        return convertView;
    }

    private static class ViewHolder {
        public ImageView mNoGoodImage;
//        public FrescoDraweeView mOrderImage;
        public TextView mBrowseProductNameTv;
        public TextView mBrowseProductPriceTv;
    }
}
