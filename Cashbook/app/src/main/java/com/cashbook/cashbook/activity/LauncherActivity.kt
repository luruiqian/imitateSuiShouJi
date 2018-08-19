package com.cashbook.cashbook.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.cashbook.cashbook.R
class LauncherActivity : AppCompatActivity() {
    private var isFirstLaunch: Boolean = true

    private var mThread = LaunchThread()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_launcher)
        //mThread.start()
        if (isFirstLaunch) {
          //  Intent intent = new Intent(this,)
//            intent.setClass(this,TestActivity::class.java)

            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

    }

    class LaunchThread : Thread() {

        override fun run() {
            super.run()
            Thread.sleep(2000)

        }
    }


}



