package com.ixuea.courses.mymusic.component.ad

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.activity.BaseViewModelActivity
import com.ixuea.courses.mymusic.component.main.MainActivity
import com.ixuea.courses.mymusic.databinding.ActivityAdBinding
import com.ixuea.courses.mymusic.util.Constant
import com.ixuea.courses.mymusic.util.FileUtil
import com.ixuea.courses.mymusic.util.ImageUtil
import com.ixuea.courses.mymusic.util.IntentUtil
import com.ixuea.courses.mymusic.util.PreferenceUtil
import com.ixuea.superui.extension.show
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import java.io.File

//广告，但是并没有集成广告sdk并没有收益
class AdActivity : BaseViewModelActivity<ActivityAdBinding>() {
    private var data: Ad? = null
    private var action:String?=null
    private var adCountDownTimer: CountDownTimer? = null
    override fun initViews() {
        super.initViews()
            //沉浸式标题栏
        QMUIStatusBarHelper.translucent(this)
    }

    override fun initDatum() {
        super.initDatum()
        //获取广告信息
        data=PreferenceUtil.getSplashAd()
        if (data==null){
            next()
            return
        }
        //显示广告信息
        show()
        binding.shimmer.startShimmer()
    }

    override fun initListeners() {
        super.initListeners()
        binding.skip.setOnClickListener {
            cancelCountDown()
            next()
        }
        //点击广告按钮
        binding.primary.setOnClickListener{
            cancelCountDown()
            action=Constant.ACTION_AD
            next()
        }
    }
    private fun show(){
        val targetFile :File= FileUtil.adFile(hostActivity,data!!.icon!!)
        if (!targetFile.exists()){
                next()
            return
        }
        binding.adControl.show()
        when(data!!.id.toString()){
            Constant.VALUE5.toString() -> showImageAd(targetFile)
//            Constant.VALUE6.toString() -> showImageAd(targetFile)
        }
    }

    private fun showImageAd(data: File) {
        ImageUtil.showLocalImage(binding.image,data.absolutePath)
        startCountDown(5000)
    }

    private fun startCountDown(data: Int) {
        //倒计时
        adCountDownTimer = object : CountDownTimer(data.toLong(), 1000) {
            /**
             * 每次间隔调用
             *
             * @param millisUntilFinished
             */
            override fun onTick(millisUntilFinished: Long) {
                binding.skip.text = getString(
                    R.string.skip_ad_count,
                    millisUntilFinished / 1000 + 1
                )
            }

            //倒计时结束
            override fun onFinish() {
                next()
            }
        }
        //用了倒计时以后一定要去释放避免内存泄露
        adCountDownTimer!!.start()
    }
    private fun cancelCountDown(){
     if (adCountDownTimer!=null){
         adCountDownTimer?.cancel()
         adCountDownTimer=null
     }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelCountDown()
    }
    private fun next(){
        val intent=Intent(hostActivity,MainActivity::class.java)
        IntentUtil.cloneIntent(getIntent(),intent)
        if (data!=null){
             intent.putExtra(Constant.AD,data)
        }
        if (action!=null){
            //跳转到广告界面，要先启动主界面，因为用户在广告界面返回正好看到主界面而不是退出应用，这才符合逻辑
            intent.action=action
        }
        startActivity(intent)
        //关闭界面
        finish()
    }
}