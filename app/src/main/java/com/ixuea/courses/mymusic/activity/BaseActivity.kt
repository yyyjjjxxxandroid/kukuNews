package com.ixuea.courses.mymusic.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
//open可被继承
open class BaseActivity :AppCompatActivity(){
    /**
     * 找控件
     */
    //“protected open” 一起使用时，表示这个成员既可以在子类中访问，又允许被进一步继承
    protected open fun initViews() {}

    /**
     * 设置数据
     */
    protected open fun initDatum() {}

    /**
     * 设置监听器
     */
    protected open fun initListeners() {}

    /**
     * 在onCreate方法后面调用
     * @param savedInstanceState
     * onPostCreate()在活动（Activity）的onCreate()方法之后被调用。
     * 这个方法通常用于在活动完全初始化后执行一些额外的设置操作，比如对视图进行进一步的配置、加载额外的数据等。
     */


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        initViews()
        initDatum()
        initListeners()
    }
}