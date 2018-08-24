package com.cashbook.cashbook.adapter

import android.support.v4.view.PagerAdapter
import android.view.View
import java.util.*

/**
 * Created by luruiqian on 2018/8/24.
 */
open class GuidePagerAdapter(pagerViewList: ArrayList<View>) : PagerAdapter() {
    private lateinit var mPagerViewList: List<View>
    override fun getCount(): Int {
        return mPagerViewList.size
    }

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return `object` == view
    }

}