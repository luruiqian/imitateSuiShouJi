package com.cashbook.cashbook.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import com.cashbook.cashbook.R

class LauncherActivity : AppCompatActivity() {

    private lateinit var mHandler: Handler
    private lateinit var mThread: Thread

    private val LAUNCH_DELAY = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        jumpGuide()
    }

    private fun jumpGuide() {
        mThread = object : Thread() {
            override fun run() {
                super.run()
                mHandler.sendEmptyMessageDelayed(LAUNCH_DELAY, 2000)
            }
        }

        mHandler = object : Handler() {
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                if (msg?.what == LAUNCH_DELAY) {
                    var intent = Intent()
                    intent.setClass(this@LauncherActivity, GuideActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (mThread.isAlive) {
            mThread.start()
        }
    }

    override fun onStop() {
        super.onStop()
        if (mThread.isAlive) {
            mThread.interrupt()
        }
    }

}



