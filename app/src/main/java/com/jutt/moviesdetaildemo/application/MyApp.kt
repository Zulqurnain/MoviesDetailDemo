package com.jutt.moviesdetaildemo.application

import android.webkit.WebView
import androidx.multidex.MultiDexApplication
import com.blankj.utilcode.util.CrashUtils
import com.blankj.utilcode.util.SDCardUtils
import com.blankj.utilcode.util.Utils
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
    }
}
