package com.cashbook.cashbook.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import com.cashbook.cashbook.MainActivity
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

    /**
     * 根据是否首次运行程序判断跳转引导页还是首页
     */
    private fun jumpGuide() {
        mHandler = object : Handler() {
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                if (msg?.what == LAUNCH_DELAY) {
                    if (isFirstRun()) {
                        var intent = Intent()
                        intent.setClass(this@LauncherActivity, GuideActivity::class.java)
                        startActivity(intent)
                    } else {
                        var intent = Intent()
                        intent.setClass(this@LauncherActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                    //跳转后需要关闭页面
                    finish()
                }
            }
        }
        mThread = object : Thread() {
            override fun run() {
                super.run()
                mHandler.sendEmptyMessageDelayed(LAUNCH_DELAY, 2000)
            }
        }
        mThread.start()
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

}



