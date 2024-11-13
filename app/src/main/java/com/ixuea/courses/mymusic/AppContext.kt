package com.ixuea.courses.mymusic

import android.app.Application
import com.ixuea.courses.mymusic.util.PreferenceUtil
import com.tencent.mmkv.MMKV

//全局Application,且要去清单文件中配置
//全局唯一性：由于 Application 类在整个应用中是单例存在的，将 MMKV 的初始化放在这里可以保证初始化操作只执行一次，确保 MMKV 在整个应用中是唯一的实例。
//生命周期合适：Application 类是整个 Android 应用程序的入口点，它的生命周期与整个应用的生命周期一致。
class AppContext : Application() {
    companion object {
        lateinit var instance: AppContext
    }

    override fun onCreate() {
        super.onCreate()
        //AppContext实例赋值给instance属性,AppContext.instance访问到这个全局的应用上下文
        instance = this
        initMMKV()
    }

    private fun initMMKV() {
        val rootDir = MMKV.initialize(this)
    }

    //退出登入
    fun logout() {
     logoutSilence()
    }

    private fun logoutSilence() {
        //清楚登入相关信息
        PreferenceUtil.logout()
    }

    fun onLogin() {

    }


}