package com.cashbook.cashbook.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cashbook.cashbook.R;
import com.cashbook.cashbook.View.PhotoViewer;

import java.util.ArrayList;
import java.util.List;


public class ImageScaleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    RecyclerView mRecyclerView;
    List<String> list = new ArrayList<>();
    List<String> picData = new ArrayList<>();


    public ImageScaleAdapter(Context context, RecyclerView recyclerView) {
        this.mContext = context;
        this.mRecyclerView = recyclerView;
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        picData.add("https://qiniucdn.fairyever.com/15149579640159.jpg");
        picData.add("https://qiniucdn.fairyever.com/15149577854174.png");
        picData.add("https://qiniucdn.fairyever.com/15248077829234.jpg");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.adapter_image_scale, null);
        return new ClassTargetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ClassTargetViewHolder) {
            ClassTargetViewHolder viewHolder = (ClassTargetViewHolder) holder;
            viewHolder.imageView = viewHolder.itemView.findViewById(R.id.iv);
            Glide.with(mContext)
                    .load("https://testjoylearning.bmw.com.cn/upload/img/b0d03dcf5240450bbf8e70be365f2154.jpg")
                    .into(viewHolder.imageView);
            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PhotoViewer
                            .setData(picData)
                            .setImgContainer(mRecyclerView)
                            .setOnPhotoViewerCreatedListener(new PhotoViewer.OnPhotoViewerCreatedListener() {
                                @Override
                                public void onCreated() {

                                    Toast.makeText(mContext, "created", Toast.LENGTH_LONG).show();
                                }
                            })


                            .setOnLongClickListener(object :OnLongClickListener {
                        override fun onLongClick(view:View){
                            Toast.makeText(view.context, "haha", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setCurrentPage(position)
                            .setShowImageViewInterface(object :PhotoViewer.ShowImageViewInterface {
                        override fun show(iv:ImageView, url:String){
                            Glide.with(iv.context).load(url).into(iv)
                        }
                    })
                    .start(activity)
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

//    public void addAll(List<VrTrainingDetailEntityBean> list) {
//        this.mList.clear();
//        if (list != null) {
//            mList.addAll(list);
//        }
//        notifyDataSetChanged();
//    }

    class ClassTargetViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ClassTargetViewHolder(View itemView) {
            super(itemView);

        }
    }

}
