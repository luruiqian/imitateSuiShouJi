package com.cashbook.cashbook.activity

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.cashbook.cashbook.R
import com.cashbook.cashbook.adapter.GuidePagerAdapter

class GuideActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {
    private lateinit var mFirstGuide: View
    private lateinit var mSecondGuide: View
    private lateinit var mThirdGuide: View
    private lateinit var mForthGuide: View
    private lateinit var mGuideVp: ViewPager
    private lateinit var mGuideIndicator: LinearLayout
    private lateinit var mPagerAdapter: GuidePagerAdapter
    private lateinit var mIndicatorTips: IntArray

    private lateinit var mPageViewList: ArrayList<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)

        initView()
    }

    private fun initView() {
        //将view加进viewpager
        mGuideVp = findViewById(R.id.guide_vp) as ViewPager
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
        mIndicatorTips = intArrayOf()
        for (i: Int in mPageViewList.indices){

        }
            mPagerAdapter = GuidePagerAdapter()
        mGuideVp.adapter = mPagerAdapter
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    /**
     * 切换view时下方小圆点的变化
     */
    override fun onPageSelected(position: Int) {

    }
}
