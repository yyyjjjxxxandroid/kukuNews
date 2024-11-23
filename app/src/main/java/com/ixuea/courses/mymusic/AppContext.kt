package com.ixuea.courses.mymusic

import android.app.Application
import com.drake.channel.sendEvent
import com.ixuea.courses.mymusic.component.login.LoginStatusChangedEvent
import com.ixuea.courses.mymusic.util.PreferenceUtil
import com.tencent.mmkv.MMKV

//全局Application,且要去清单文件中配置
//全局唯一性：由于 Application 类在整个应用中是单例存在的，将 MMKV 的初始化放在这里可以保证初始化操作只执行一次，确保 MMKV 在整个应用中是唯一的实例。
//生命周期合适：Application 类是整个 Android 应用程序的入口点，它的生命周期与整个应用的生命周期一致。
//AppContext.instance 是应用级别的 Context 单例对象（一般通过自定义单例模式获取全局 Context）。不同类型的 Context 用途各异
// 应用级 Context 可访问如网络状态查询等系统资源与服务
//随意传递错误或不恰当的 Context 易引发内存泄漏等严重问题
// ，如将 ViewModel 当作 Context 传递或把短生命周期的 Activity 的 Context 传给长时间运行的操作（像异步任务），
// 当 Activity 应销毁而因异步任务持有其 Context 引用无法被回收时便会出现内存泄漏。而应用级 Context 单例如 AppContext.instance,其生命周期与应用相同
// ，只要应用运行就有效，不会因个别组件（Activity 或 Fragment）销毁产生意外内存问题，符合 Android 开发中 Context 使用的最佳实践要求。
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
        loginStatusChanged()
    }



    fun onLogin() {
        loginStatusChanged()
    }
    private fun loginStatusChanged() {
       sendEvent(LoginStatusChangedEvent())
    }

}