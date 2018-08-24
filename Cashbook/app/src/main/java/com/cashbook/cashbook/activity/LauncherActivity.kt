package com.cashbook.cashbook.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.cashbook.cashbook.MainActivity
import com.cashbook.cashbook.R

class LauncherActivity : Activity() {
    private var mTimeCount = 3
    private val LAUNCH_DELAY = 1
    private val LAUNCHER_AD_TIME_COUNT_DOWN: Long = 3000

    private var mAvTimeTv: TextView? = null
    private var mLaunchImage: ImageView? = null

    private lateinit var mThread: Thread
    private lateinit var mCountDownTimer: CountDownTimer

    private var mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            if (msg?.what == LAUNCH_DELAY) {
                if (isFirstRun()) {
                    jumpGuide()
                } else {
                    //不是首次打开程序，进入模拟广告页
                    startTimer(LAUNCHER_AD_TIME_COUNT_DOWN)
                }
            }
        }
    }

    private fun jumpGuide() {
        //首次打开程序，进入引导页
        var intent = Intent()
        intent.setClass(this@LauncherActivity, GuideActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        initView()
        initListener()
        launch()
    }

    private fun initView() {
        mAvTimeTv = findViewById(R.id.launcher_av_time_tv) as TextView?
        mLaunchImage = findViewById(R.id.launcher_av_iv) as ImageView?
    }

    private fun initListener() {
        mAvTimeTv?.setOnClickListener {
            cancelTimer()
            jumpMainActivity()
        }
    }

    /**
     * 根据是否首次运行程序判断跳转引导页还是首页
     */
    private fun launch() {
        mThread = object : Thread() {
            override fun run() {
                super.run()
                var message = mHandler.obtainMessage(LAUNCH_DELAY)
                mHandler.sendMessageDelayed(message, 1000)
            }
        }
        mThread.start()
    }

    private fun jumpMainActivity() {
        var intent = Intent()
        intent.setClass(this@LauncherActivity, MainActivity::class.java)
        startActivity(intent)
        //跳转后需要关闭页面
        finish()
    }

    private fun startTimer(time: Long) {
        //开始计时，则显示广告页和计时文字
        mLaunchImage?.setImageResource(R.drawable.av)
        mAvTimeTv?.visibility = View.VISIBLE
        // 启动倒计时
        mCountDownTimer = object : CountDownTimer(time, 1000) {
            override fun onFinish() {
                Log.i("Tag", mTimeCount.toString())
                mAvTimeTv?.text = "$mTimeCount  秒跳转"
                jumpMainActivity()
            }

            override fun onTick(millisUntilFinished: Long) {
                Log.i("Tag", mTimeCount.toString())
                mAvTimeTv?.text = "$mTimeCount  秒跳转"
                mTimeCount -= 1
            }
        }
        // 如果倒计时不为空，则先取消之前倒计时
        cancelTimer()
        mCountDownTimer.start()
    }

    private fun cancelTimer() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel()
        }
    }

    /**
     * 判断是否是第一次启动程序 利用 SharedPreferences 将数据保存在本地
     */
    private fun isFirstRun(): Boolean {
        //实例化SharedPreferences
        var sharePreference = this.getSharedPreferences("share", android.content.Context.MODE_PRIVATE)
        //实例化SharedPreferences 的editor
        var editor = sharePreference.edit()
        var isFirstRun = sharePreference.getBoolean("isFirstRun", true)
        if (!isFirstRun) {
            return false
        } else {
            //存储数据
            editor.putBoolean("isFirstRun", false)
            //提交数据
            editor.commit()
            return true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mHandler != null) {
            mHandler.removeMessages(LAUNCH_DELAY)
        }
        cancelTimer()
        System.gc()
    }
}



