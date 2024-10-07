package com.ixuea.courses.mymusic.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * 设置偏好工具类
 * 是否登录了
 */
class DefaultPreferenceUtil(context: Context) {
    private var context:Context
    private val preference : SharedPreferences
    init {
        //保存不可以直接使用context，因为context如果传的是activity会导致内存泄露
        //如果当前工具类引用了内存实例，当界面关闭后，应为界面对应在这里还有引用，所以会导致界面对象不被释放
        this.context=context.applicationContext
         //获取系统默认偏好设置，在设置界面保存的值可以这样获取
        preference=PreferenceManager.getDefaultSharedPreferences(this.context)
        //自定义名称
//        preference=this.context.getSharedPreferences(NAME,Context.MODE_PRIVATE)

    }

    /**
     * 设置同意了用户协议
     */
fun setAcceptTermsServiceAgreement(){

    putBoolean(TERM_SERVICE,true)
}

    //获取是否同意了用户协议
    private fun putBoolean(key: String, value: Boolean) {
        preference.edit().putBoolean(key,value).apply()
    }


    val isAcceptTermsServiceAgreement:Boolean
        get() = preference.getBoolean(TERM_SERVICE,false)

    companion object{
        private const val TERM_SERVICE="term_service"

        private var instance:DefaultPreferenceUtil?=null
        /*
        * java写法获取偏好设置单例
        *
        * */
        @Synchronized
        fun getInstance(context: Context):DefaultPreferenceUtil{
            if (instance==null){
                instance=DefaultPreferenceUtil(context)
            }
            return instance!!
        }
    }
}