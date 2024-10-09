package com.ixuea.courses.mymusic

import android.app.Application
import com.tencent.mmkv.MMKV
//全局Application,且要去清单文件中配置
class AppContext : Application(){
    override fun onCreate() {
        super.onCreate()
        initMMKV()
    }

    private fun initMMKV() {
        val rootDir=MMKV.initialize(this)
    }
}