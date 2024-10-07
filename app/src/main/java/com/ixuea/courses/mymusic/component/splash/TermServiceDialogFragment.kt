package com.ixuea.courses.mymusic.component.splash

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.FragmentManager
import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.fragment.BaseCommonFragment
import com.ixuea.courses.mymusic.fragment.BaseDialogFragment
import com.ixuea.courses.mymusic.util.DefaultPreferenceUtil
import com.ixuea.superui.SuperProcessUtil
import com.ixuea.superui.util.ScreenUtil
import com.ixuea.superui.util.SuperTextUtil
import com.ixuea.courses.mymusic.component.splash.SplashActivity

/*
* 对话框
* */
class TermServiceDialogFragment : BaseCommonFragment() {
    private lateinit var disagree:Button
    private lateinit var agree:TextView
    private lateinit var contentView:TextView
    private lateinit var onAgreementClickListener: View.OnClickListener
    //如果父类中有抽象方法，那么子类必须实现父类中的所有抽象方法，否则子类也必须声明为抽象类。
    companion object {
        /**
         * 显示对话框
         */
        fun show(fragmentManager: FragmentManager, onAgreementClickListener: View.OnClickListener) {
            val dialogFragment = TermServiceDialogFragment()

            dialogFragment.onAgreementClickListener = onAgreementClickListener

            dialogFragment.show(fragmentManager, "TermServiceDialogFragment")
        }
    }
    override fun initViews() {
        super.initViews()
        //点击外部不消失
        setCancelable(false)
        contentView=findViewById(R.id.content)
        SuperTextUtil.setLinkColor(contentView,getColor(requireContext(),R.color.link))
        agree=findViewById(R.id.primary)
         disagree=findViewById(R.id.disagree)

    }

    override fun initListeners() {
        super.initListeners()
        agree.setOnClickListener {
            //设置同意了用户协议
            DefaultPreferenceUtil.getInstance(requireContext()).setAcceptTermsServiceAgreement()

            //关闭对话框
            dismiss()
            onAgreementClickListener.onClick(it)
        }
        disagree.setOnClickListener {
           dismiss()
            SuperProcessUtil.killApp()
        }
    }

    override fun initDatum() {
        super.initDatum()
        //<a href="链接地址">文本内容</a>这种格式的 HTML 标记在使用Html.fromHtml()方法解析后，会被识别为可点击的超链接。
        // 当用户点击包含这种超链接的文本时，系统会自动启动一个意图（Intent）来打开默认浏览器并导航到指定的链接地址。
        val content=Html.fromHtml(getString(R.string.term_service_privacy_content))
        contentView.text=content
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