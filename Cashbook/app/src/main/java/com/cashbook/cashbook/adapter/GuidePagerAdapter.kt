package com.cashbook.cashbook.adapter

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import java.util.*

/**
 * Created by luruiqian on 2018/8/24.
 */
open class GuidePagerAdapter(pagerViewList: ArrayList<View>) : PagerAdapter() {
    private var mPagerViewList: List<View> = pagerViewList
    override fun getCount(): Int {
        return mPagerViewList.size
    }

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return `object` == view
    }

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        container?.addView(mPagerViewList[position])
        return mPagerViewList[position]
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        if (mPagerViewList[position] != null) {
            container?.removeView(mPagerViewList[position])
        }
    }

}