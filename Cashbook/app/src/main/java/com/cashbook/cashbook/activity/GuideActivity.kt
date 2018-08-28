package com.cashbook.cashbook.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.cashbook.cashbook.MainActivity
import com.cashbook.cashbook.R
import com.cashbook.cashbook.adapter.GuidePagerAdapter

class GuideActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {
    private lateinit var mFirstGuide: View
    private lateinit var mSecondGuide: View
    private lateinit var mThirdGuide: View
    private lateinit var mForthGuide: View
    private lateinit var mGuideVp: ViewPager
    private lateinit var mExperienceIv: ImageView
    private lateinit var mGuideIndicator: LinearLayout
    private lateinit var mPagerAdapter: GuidePagerAdapter
    private lateinit var mPageViewList: ArrayList<View>
    private lateinit var mIndicatorTips: Array<ImageView?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)

        initView()
        initListener()
    }

    private fun initListener() {
        //点击立即体验按钮，跳转首页
        mExperienceIv?.setOnClickListener {
            jumpMainActivity()
        }
    }

    private fun jumpMainActivity() {
        var intent = Intent()
        intent.setClass(this@GuideActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun initView() {
        //将view加进viewpager
        mGuideVp = findViewById(R.id.guide_vp) as ViewPager
        mExperienceIv = findViewById(R.id.guide_immediate_experience_btn) as ImageView
        mFirstGuide = layoutInflater.inflate(R.layout.guide_first_view, null)
        mSecondGuide = layoutInflater.inflate(R.layout.guide_second_view, null)
        mThirdGuide = layoutInflater.inflate(R.layout.guide_third_view, null)
        mForthGuide = layoutInflater.inflate(R.layout.guide_forth_view, null)
        mPageViewList = ArrayList()
        mPageViewList.add(mFirstGuide)
        mPageViewList.add(mSecondGuide)
        mPageViewList.add(mThirdGuide)
        mPageViewList.add(mForthGuide)
        //下面的小圆点
        mGuideIndicator = findViewById(R.id.guide_indicator) as LinearLayout
        mIndicatorTips = arrayOfNulls<ImageView>(4)
        for (i: Int in mPageViewList.indices) {
            var imageView = ImageView(this)
            Log.i("Tag", "imageView " + i)
            imageView.layoutParams = ViewGroup.LayoutParams(40, 40)
            imageView.setPadding(10, 10, 10, 10)
            mIndicatorTips[i] = imageView
            mIndicatorTips[i]?.setImageResource(R.drawable.guide_indicator_selector)
            //默认第一张图显示为选中状态
            mIndicatorTips[i]?.isSelected = i == 0
            mGuideIndicator.addView(mIndicatorTips[i])
        }
        mPagerAdapter = GuidePagerAdapter(mPageViewList)
        mGuideVp.adapter = mPagerAdapter
        mGuideVp.addOnPageChangeListener(this)
    }

    override fun onPageScrollStateChanged(state: Int) {
        when (state) {
            ViewPager.SCROLL_STATE_IDLE -> {
                if (mGuideVp.currentItem == mGuideVp.adapter.count - 1) {
                    jumpMainActivity()
                }
            }
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    /**
     * 切换view时下方小圆点的变化
     */
    override fun onPageSelected(position: Int) {
        for (i: Int in mPageViewList.indices) {
            mIndicatorTips[i]?.isSelected = i == position
            if (position == mPageViewList.size - 1) {
                mExperienceIv.visibility = View.VISIBLE
            } else {
                mExperienceIv.visibility = View.GONE
            }
        }
    }
}
