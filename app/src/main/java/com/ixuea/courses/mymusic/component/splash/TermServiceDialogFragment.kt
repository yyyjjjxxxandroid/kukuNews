package com.ixuea.courses.mymusic.component.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.FragmentManager
import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.fragment.BaseDialogFragment
import com.ixuea.superui.util.ScreenUtil

/*
* 对话框
* */
class TermServiceDialogFragment : BaseDialogFragment() {
    //如果父类中有抽象方法，那么子类必须实现父类中的所有抽象方法，否则子类也必须声明为抽象类。
    companion object{
        //提供类似静态成员的功能可以在其中定义常量、方法等，通过类名直接访问，比如 MyClass.companionObjectMethod()。
        //实现工厂方法：用于创建类的实例，方便对实例化过程进行控制。
        //可用于单例模式：轻松实现单例，如 MyClass.instance

    }
    override fun initViews() {
        super.initViews()
        //点击外部不消失
        setCancelable(false)
    }

    //onResume当 Fragment 开始与用户交互时被调用。在这个阶段，Fragment 处于活动状态，可以接收用户输入和处理事件。可以在这个方法中进行一些与显示相关的操作，例如调整对话框的大小。

    override fun onResume() {
        super.onResume()
        //修改宽度
        //对话框（dialog）的窗口（window）的布局参数（LayoutParams）,attributes是指对话框窗口的布局参数。
        val params:ViewGroup.LayoutParams=dialog!!.window!!.attributes
        params.width=((ScreenUtil.getScreenWith(requireContext()))*0.9).toInt()
        params.height=ViewGroup.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes=params as WindowManager.LayoutParams
    }
    override fun getLayoutView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_term_service_dialog,container,false)
    }

}