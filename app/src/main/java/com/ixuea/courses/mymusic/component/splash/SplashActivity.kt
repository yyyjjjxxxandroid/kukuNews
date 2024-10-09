package com.ixuea.courses.mymusic.component.splash

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.activity.BaseLogicActivity
import com.ixuea.courses.mymusic.activity.BaseViewModelActivity
import com.ixuea.courses.mymusic.component.guide.GuideActivity
import com.ixuea.courses.mymusic.databinding.ActivitySplashBinding
import com.ixuea.courses.mymusic.util.DefaultPreferenceUtil
import com.ixuea.courses.mymusic.util.PreferenceUtil
import com.ixuea.k.util.StringUtil
import com.ixuea.superui.util.SuperDarkUtil
import com.ixuea.superui.date.SuperDateUtil
import com.permissionx.guolindev.PermissionX
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import kotlin.math.log

/*
* 启动页面
* */
class SplashActivity : BaseViewModelActivity<ActivitySplashBinding>() {

    companion object {
        const val TAG = "SplashActivity"
    }
    //lateinit：用于延迟初始化非空类型的变量。
//    private lateinit var  copyrightView: TextView

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
//        copyrightView=findViewById(R.id.textView)


    }

    override fun initDatum() {
        super.initDatum()
        if (DefaultPreferenceUtil.getInstance(this).isAcceptTermsServiceAgreement) {
            //已经同意了用户协议
           requestPermission()
        } else {
            //        获取系统日期
//            val currentYear= SuperDateUtil.currentYear()
//            copyrightView.text = getString(R.string.copyright,currentYear)
            showTermsServiceAgreementDialog()
        }



    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            PermissionX.init(this).permissions(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.READ_PHONE_STATE,
//                Manifest.permission.READ_MEDIA_AUDIO,
//                Manifest.permission.READ_MEDIA_IMAGES,
//                Manifest.permission.READ_MEDIA_VIDEO,
            )
        } else {
            PermissionX.init(this).permissions(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.READ_PHONE_STATE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE,
            )
        }.request { allGranted, grantedList, deniedList ->
            if (allGranted) {

                binding.root.postDelayed({
                    prepareNext()
                }, 1000)
            } else {
                //可以在这里弹出提示告诉用户为什么需要权限

                finish()
            }
        }
    }
    private fun prepareNext(){
       if (PreferenceUtil.isShowGuide()){
           startActivityAfterFinishThis(GuideActivity::class.java)
           return
       }
        //跳转到下一个界面
      startActivityAfterFinishThis(GuideActivity::class.java)
    }


    private fun showTermsServiceAgreementDialog() {
        //快捷键*2弹出全局搜索
//        TermServiceDialogFragment.show(supportFragmentManager,object : View.OnClickListener{
//            override fun onClick(v: View?) {
//                Log.d(TAG, "primary onClick")
//            }
//
//        })
//前面一直报错是因为TermServiceDialogFragment（）这样字去调用show方法了

        TermServiceDialogFragment.show(supportFragmentManager) {
            Log.d(TAG, "primary onClick")
            DefaultPreferenceUtil.getInstance(this).setAcceptTermsServiceAgreement()
            requestPermission()
        }
    }
}


