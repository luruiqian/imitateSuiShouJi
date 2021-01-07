package com.cashbook.cashbook.View;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cashbook.cashbook.R;
import com.cashbook.cashbook.adapter.PhotoViewerFragment;
import com.cashbook.cashbook.utils.Utils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;

class PhotoViewer {
    private final String INDICATOR_TYPE_DOT = "INDICATOR_TYPE_DOT";
    private final String INDICATOR_TYPE_TEXT = "INDICATOR_TYPE_TEXT";

    //commit 1
    private ShowImageViewInterface mInterface;
    //commit 2
    private OnPhotoViewerCreatedListener mCreatedInterface;
    private OnPhotoViewerDestroyListener mDestroyInterface;

    private ArrayList<String> imgData; // 图片数据
    private WeakReference<ViewGroup> container;  // 存放图片的容器， ListView/GridView/RecyclerView
    private int currentPage = 0;   // 当前页

    private WeakReference<View> clickView; //点击那一张图片时候的view
    private View.OnLongClickListener longClickListener;

    private String indicatorType = INDICATOR_TYPE_DOT;  // 默认type为小圆点

    interface OnPhotoViewerCreatedListener {
        void onCreated();
    }


    interface OnPhotoViewerDestroyListener {
        void onDestroy();
    }

    private void setOnPhotoViewerCreatedListener() {
        mCreatedInterface = new OnPhotoViewerCreatedListener() {
            @Override
            public void onCreated() {

            }
        };
    }

    private void setOnPhotoViewerDestroyListener() {
        mDestroyInterface = new OnPhotoViewerDestroyListener() {

            @Override
            public void onDestroy() {

            }
        };
    }

    /**
     * 小圆点的drawable
     * 下标0的为没有被选中的
     * 下标1的为已经被选中的
     */
    private int[] mDot = {R.drawable.no_selected_dot, R.drawable.selected_dot};


    interface ShowImageViewInterface {
        void show(ImageView iv, String url);
    }

    /**
     * 设置显示ImageView的接口
     */
    ShowImageViewInterface setShowImageViewInterface(ShowImageViewInterface i) {
        mInterface = i;
        return mInterface;
    }

    /**
     * 设置点击一个图片
     */
    public void setClickSingleImg(String data, View view) {
        imgData.add(data);
        clickView = new WeakReference(view);
    }

    /**
     * 设置图片数据
     */
    public void setData(ArrayList<String> data) {
        imgData = data;
    }


    public void setImgContainer(AbsListView container) {
        this.container = new WeakReference(container);
    }

    public void setImgContainer(RecyclerView container) {
        this.container = new WeakReference(container);
    }

    /**
     * 获取itemView
     */
    private View getItemView() {
        if (clickView == null) {
            View itemView = null;
            if (container.get() instanceof AbsListView) {
                AbsListView absListView = (AbsListView) container.get();
                itemView = absListView.getChildAt(currentPage - absListView.getFirstVisiblePosition());
            } else {
                itemView = ((RecyclerView) container.get()).getLayoutManager().findViewByPosition(currentPage);
            }

            if (itemView instanceof ViewGroup) {
                findImageView(itemView);
            } else {
                return (ImageView) itemView;
            }
        } else {
            return clickView.get();
        }
        return null;
    }

    private View findImageView(ViewGroup group) {
        for (int i = 0; i < group.getChildCount(); i++) {
            if (group.getChildAt(i) instanceof ImageView) {
                return group.getChildAt(i);
            }
            if (group.getChildAt(i) instanceof ViewGroup) {
                return findImageView((ViewGroup) group.getChildAt(i));
            } else throw new RuntimeException("未找到ImageView");
        }
        return null;
    }

    /**
     * 获取现在查看到的图片的原始位置 (中间)
     */
    private int[] getCurrentViewLocation() {
        int[] result = new int[2];
        getItemView().getLocationInWindow(result);
        result[0] += getItemView().getMeasuredWidth() / 2;
        result[1] += getItemView().getMeasuredHeight() / 2;
        return result;
    }


    /**
     * 设置当前页， 从0开始
     */
    public void setCurrentPage(int page) {
        currentPage = page;
    }

    public void start(Fragment fragment) {
        FragmentActivity activity = fragment.getActivity();
        start((AppCompatActivity) activity);
    }


    public void start(Fragment fragment) {
        FragmentActivity activity = fragment.getActivity();
        start(activity);
    }


    public void start(FragmentActivity activity) {
        show(activity);
    }


    public void setOnLongClickListener(View.OnLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }


    /**
     * 设置指示器的样式，但是如果图片大于9张，则默认设置为文字样式
     */
    public void setIndicatorType(String type) {
        this.indicatorType = type;
    }

    private void show(AppCompatActivity activity) {


        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();


        // 设置添加layout的动画
        LayoutTransition layoutTransition = new LayoutTransition();
        ObjectAnimator alphaOa = ObjectAnimator.ofFloat(null, "alpha", 0f, 1f);
        alphaOa.setDuration(50);
        layoutTransition.setAnimator(LayoutTransition.APPEARING, alphaOa);
        decorView.setLayoutTransition(layoutTransition);

        FrameLayout frameLayout = new FrameLayout(activity);

        View photoViewLayout = LayoutInflater.from(activity).inflate(R.layout.activity_photoviewer, null);
        ViewPager viewPager = photoViewLayout.findViewById(R.id.mLookPicVP);

        List<PhotoViewerFragment> fragments = new ArrayList<PhotoViewerFragment>();
        /**
         * 存放小圆点的Group
         */
        LinearLayout mDotGroup;

        /**
         * 存放没有被选中的小圆点Group和已经被选中小圆点
         * 或者存放数字
         */
        FrameLayout mFrameLayout;
        /**
         * 选中的小圆点
         */
        View mSelectedDot;


        /**
         * 文字版本当前页
         */
        TextView tv;


        for (int i = 0; i < imgData.size(); i++) {
            Fragment f = new PhotoViewerFragment();
            PhotoViewerFragment.OnExitListener exitListener = new PhotoViewerFragment.OnExitListener() {
                @Override
                public void exit() {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mDotGroup != null)
                                mDotGroup.removeAllViews();
                            frameLayout.removeAllViews();
                            decorView.removeView(frameLayout);
                            fragments.clear();


                            if (mDestroyInterface != null) {
                                mDestroyInterface.onDestroy();
                            }
                        }
                    });
                }

        };
            f.setData(intArrayOf(getItemView().measuredWidth, getItemView().measuredHeight), getCurrentViewLocation(), imgData[i], true)
            f.longClickListener = longClickListener
            fragments.add(f)

        val adapter = PhotoViewerPagerAdapter(fragments, activity.supportFragmentManager)


        viewPager.adapter = adapter
        viewPager.currentItem = currentPage
        viewPager.offscreenPageLimit = 100
        viewPager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state:Int){

            }

            override fun onPageScrolled(position:Int, positionOffset:Float, positionOffsetPixels:Int)
            {

                if (mSelectedDot != null && imgData.size > 1) {
                    val dx = mDotGroup !!.getChildAt(1).x - mDotGroup !!.getChildAt(0).x
                    mSelectedDot !!.translationX = (position * dx) + positionOffset * dx
                }
            }

            override fun onPageSelected(position:Int){
                currentPage = position


                /**
                 * 解决RecyclerView获取不到itemView的问题
                 * 如果滑到的view不在当前页面显示，那么则滑动到那个position，再获取itemView
                 */
                if (container.get() !is AbsListView){
                    val layoutManager = (container.get() as RecyclerView).layoutManager
                    if (layoutManager is LinearLayoutManager){
                        if (currentPage < layoutManager.findFirstVisibleItemPosition() || currentPage > layoutManager.findLastVisibleItemPosition()) {
                            layoutManager.scrollToPosition(currentPage)
                        }
                    } else if (layoutManager is GridLayoutManager){
                        if (currentPage < layoutManager.findFirstVisibleItemPosition() || currentPage > layoutManager.findLastVisibleItemPosition()) {
                            layoutManager.scrollToPosition(currentPage)
                        }
                    }
                }

                /**
                 * 设置文字版本当前页的值
                 */
                if (tv != null) {
                    tv !!.text = "${currentPage + 1}/${imgData.size}"
                }

                // 这里延时0.2s是为了解决上面👆的问题。因为如果刚调用ScrollToPosition方法，就获取itemView是获取不到的，所以要延时一下
                Timer().schedule(timerTask {
                    fragments[currentPage].setData(intArrayOf(getItemView().measuredWidth, getItemView().measuredHeight), getCurrentViewLocation(), imgData[currentPage], false)
                },200)

            }

        })

        frameLayout.addView(photoViewLayout)


        frameLayout.post {
            mFrameLayout = FrameLayout(activity)
            if (imgData.size in 2. .9 && indicatorType == INDICATOR_TYPE_DOT){

                /**
                 * 实例化两个Group
                 */
                if (mFrameLayout != null) {
                    mFrameLayout !!.removeAllViews()
                }
                if (mDotGroup != null) {
                    mDotGroup !!.removeAllViews()
                    mDotGroup = null
                }
                mDotGroup = LinearLayout(activity)

                if (mDotGroup !!.childCount != 0)
                mDotGroup !!.removeAllViews()
                val dotParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                /**
                 * 未选中小圆点的间距
                 */
                dotParams.rightMargin = Utils.dp2px(activity, 12)

                /**
                 * 创建未选中的小圆点
                 */
                for (i in 0 until imgData.size){
                    val iv = ImageView(activity)
                    iv.setImageDrawable(activity.resources.getDrawable(mDot[0]))
                    iv.layoutParams = dotParams
                    mDotGroup !!.addView(iv)
                }

                /**
                 * 设置小圆点Group的方向为水平
                 */
                mDotGroup !!.orientation = LinearLayout.HORIZONTAL
                /**
                 * 设置小圆点在中间
                 */
                mDotGroup !!.gravity = Gravity.CENTER or Gravity.BOTTOM
                /**
                 * 两个Group的大小都为match_parent
                 */
                val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT)


                params.bottomMargin = Utils.dp2px(activity, 70)
                /**
                 * 首先添加小圆点的Group
                 */
                frameLayout.addView(mDotGroup, params)

                mDotGroup !!.post {
                    if (mSelectedDot != null) {
                        mSelectedDot = null
                    }
                    if (mSelectedDot == null) {
                        val iv = ImageView(activity)
                        iv.setImageDrawable(activity.resources.getDrawable(mDot[1]))
                        val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                        /**
                         * 设置选中小圆点的左边距
                         */
                        params.leftMargin = mDotGroup !!.getChildAt(0).x.toInt()
                        iv.translationX = (dotParams.rightMargin * currentPage + mDotGroup !!.
                        getChildAt(0).width * currentPage).toFloat()
                        params.gravity = Gravity.BOTTOM
                        mFrameLayout !!.addView(iv, params)
                        mSelectedDot = iv
                    }
                    /**
                     * 然后添加包含未选中圆点和选中圆点的Group
                     */
                    frameLayout.addView(mFrameLayout, params)
                }
            } else{
                tv = TextView(activity)
                tv !!.text = "${currentPage + 1}/${imgData.size}"
                tv !!.setTextColor(Color.WHITE)
                tv !!.gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
                tv !!.textSize = 18f
                mFrameLayout !!.addView(tv)
                val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT)
                params.bottomMargin = Utils.dp2px(activity, 80)
                frameLayout.addView(mFrameLayout, params)

            }
        }
        decorView.addView(frameLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        if (mCreatedInterface != null) {
            mCreatedInterface !!.onCreated()
        }
    }

}
