package com.ixuea.courses.mymusic.splash

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ixuea.courses.mymusic.R
import com.ixuea.superui.date.SuperDarkUtil
import com.ixuea.superui.date.SuperDateUtil
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

/*
* 启动页面
* */
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        QMUIStatusBarHelper.translucent(this)
        if (SuperDarkUtil.isDark(this)) {
            //状态栏文字白色
            QMUIStatusBarHelper.setStatusBarDarkMode(this)
        }else{
            //状态栏文字黑色
            QMUIStatusBarHelper.setStatusBarLightMode(this)
        }

        val currentYear= SuperDateUtil.currentYear()
        val textview=findViewById<TextView>(R.id.textView)
        textview.text = "广东海洋大学 杨杰轩 ${currentYear}"
        //获取系统日期
    }
}