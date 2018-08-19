package com.cashbook.cashbook.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.cashbook.cashbook.R

class GuideActivity : AppCompatActivity() {
    private var mExperienceTv: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)

        initView()
    }

    private fun initView() {
        mExperienceTv = findViewById(R.id.activity_guide_experience) as TextView?
    }
}
