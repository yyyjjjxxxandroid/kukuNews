package com.ixuea.courses.mymusic.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

/*
* 所有DialogFragment对话框的父类
* */
abstract class BaseDialogFragment : DialogFragment(){
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

/*
* 返回要显示的控件
* */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //获得view
        val view=getLayoutView(inflater,container,savedInstanceState)
        return view
    }

   open abstract fun getLayoutView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initDatum()
        initListeners()

    }


}