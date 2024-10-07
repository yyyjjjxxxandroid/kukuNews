package com.ixuea.courses.mymusic.component.splash

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.activity.BaseLogicActivity
import com.ixuea.courses.mymusic.util.DefaultPreferenceUtil
import com.ixuea.superui.util.SuperDarkUtil
import com.ixuea.superui.date.SuperDateUtil
import com.permissionx.guolindev.PermissionX
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

/*
* 启动页面
* */
class SplashActivity : BaseLogicActivity() {
    companion object {
        const val TAG = "SplashActivity"
    }
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
        if (DefaultPreferenceUtil.getInstance(this).isAcceptTermsServiceAgreement) {
            //已经同意了用户协议
           requestPermission()
        } else {
            //        获取系统日期
            val currentYear= SuperDateUtil.currentYear()
            copyrightView.    text = getString(R.string.copyright,currentYear)
            showTermsServiceAgreementDialog()
        }



    }

   private fun requestPermission() {
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

           PermissionX.init(this)
               .permissions(
                   Manifest.permission.CAMERA,
                   Manifest.permission.RECORD_AUDIO,
                   Manifest.permission.ACCESS_COARSE_LOCATION,
                   Manifest.permission.ACCESS_FINE_LOCATION,
                   Manifest.permission.READ_PHONE_STATE,
                   Manifest.permission.READ_MEDIA_AUDIO,
                   Manifest.permission.READ_MEDIA_IMAGES,
                   Manifest.permission.READ_MEDIA_VIDEO,
               )
       }else{

           PermissionX.init(this)
               .permissions(
                   Manifest.permission.CAMERA,
                   Manifest.permission.RECORD_AUDIO,
                   Manifest.permission.ACCESS_COARSE_LOCATION,
                   Manifest.permission.ACCESS_FINE_LOCATION,
                   Manifest.permission.READ_PHONE_STATE,
                   Manifest.permission.READ_EXTERNAL_STORAGE,
                   Manifest.permission.WRITE_EXTERNAL_STORAGE,
               )
       }.request{allGranted,grantedList,deniedList->
                    if (allGranted){
                        //延时1秒后再跳转

//                        Handler().postDelayed({prepareNext()},1000)
                        prepareNext()

                    }else{
                        // 可以弹出提示告诉用户为什么要权限

                        finish()
                    }
            }
    }
    private fun prepareNext(){
        Log.d("yjx", "prepareNext: ")
    }


    private fun showTermsServiceAgreementDialog() {
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


