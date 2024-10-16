package com.ixuea.courses.mymusic.component.guide

import android.content.Intent
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.ixuea.courses.mymusic.MainActivity
import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.activity.BaseViewModelActivity
import com.ixuea.courses.mymusic.component.api.DefaultNetworkService
import com.ixuea.courses.mymusic.config.Config
import com.ixuea.courses.mymusic.databinding.ActivityGuideBinding
import com.ixuea.courses.mymusic.util.Constant
import com.ixuea.courses.mymusic.util.PreferenceUtil
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * 左右滚动的引导界面
 */
class GuideActivity:BaseViewModelActivity<ActivityGuideBinding>() {
    private lateinit var adapter: GuideAdapter
    override fun initDatum() {
        super.initDatum()
//apply函数调用链的一部分，它允许在创建对象后对其进行进一步的配置或初始化操作。
        adapter=GuideAdapter(this,supportFragmentManager)
        binding.list.adapter=adapter
        //让指示器和ViewPager关联
        binding.indicator.setViewPager(binding.list)
        //适配器注册数据观察者
        adapter.registerDataSetObserver(binding.indicator.dataSetObserver)
        val datum:MutableList<Int> = mutableListOf()
        datum.add(R.drawable.guide1)
        datum.add(R.drawable.guide2)
        datum.add(R.drawable.guide3)
        datum.add(R.drawable.guide4)
        datum.add(R.drawable.guide5)
        adapter.setDatum(datum)

    }
    override fun initListeners() {
        super.initListeners()
        //两个按钮点击都是跳到主界面  如果是登入注册进入主界面会有判断 为了进入登入界面后back不会直接退出应用而是退到主界面也可以试用
        //登入或助注册
        binding.loginOrRegister.setOnClickListener {
              setShowGuide()
            val intent = Intent(this, MainActivity::class.java)
            intent.action =Constant.ACTION_LOGIN
            startActivity(intent)
            finish()
        }
        //立即体验
        binding.experienceNow.setOnClickListener {
            setShowGuide()
//            startActivityAfterFinishThis(MainActivity::class.java)
//            testGet()
            testRetrofitGet()
        }
    }

    private fun testRetrofitGet() {
        lifecycleScope.launch {
            //看左边的图标 就是挂起函数 suspend挂起函数关键字在content方法里
            //简单理解就是 它调用网络之后 只有请求成功以后才会继续往下执行  就是异步
//
//           val contentWrapper= DefaultNetworkService.create().contents(null,null,10)
//            Log.d("content", "testRetrofitGet: ${contentWrapper.data!!.data!![0].title}")
            val r= DefaultNetworkService.create().contentDetail("1")
            Log.d("content", "testRetrofitGet: ${r.data?.createdAt?:"没有返回值"}")
        }

    }

    private fun testGet() {
      val client=OkHttpClient()
        val  url=Config.ENDPOINT+"v1/contents"
        val request=Request.Builder().url(url).build()
        client.newCall(request).enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.d("OKHTTP", "onFailure: "+e.localizedMessage)
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("OKHTTP", "onResponse: "+response.body!!.string())
            }

        })
    }

    private fun setShowGuide() {
        PreferenceUtil.setShowGuide(false)
    }


}