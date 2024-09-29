package com.ixuea.courses.mymusic.component.splash

import android.os.Bundle
import android.widget.TextView
import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.activity.BaseLogicActivity
import com.ixuea.superui.util.SuperDarkUtil
import com.ixuea.superui.date.SuperDateUtil
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

/*
* 启动页面
* */
class SplashActivity : BaseLogicActivity() {
    //lateinit：用于延迟初始化非空类型的变量。
    private lateinit var  copyrightView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }



    }

    override fun initViews() {
        super.initViews()
        QMUIStatusBarHelper.translucent(this)
        if (SuperDarkUtil.isDark(this)) {
            //状态栏文字白色
            QMUIStatusBarHelper.setStatusBarDarkMode(this)
        }else{
            //状态栏文字黑色
            QMUIStatusBarHelper.setStatusBarLightMode(this)
        }
        copyrightView=findViewById(R.id.textView)

    }

    override fun initDatum() {
        super.initDatum()
        //        获取系统日期
        val currentYear= SuperDateUtil.currentYear()
        copyrightView.    text = getString(R.string.copyright,currentYear)

        showTermsServiceAgreementDialog()


    }

    private fun showTermsServiceAgreementDialog() {
        TermServiceDialogFragment().show(supportFragmentManager,"TermsServiceDialogFragment")

    }
}


