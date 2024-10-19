package com.ixuea.courses.mymusic.component.main

import com.angcyo.tablayout.ViewPagerDelegate
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.activity.BaseViewModelActivity
import com.ixuea.courses.mymusic.component.login.LoginHomeActivity
import com.ixuea.courses.mymusic.databinding.ActivityMainBinding
import com.ixuea.courses.mymusic.databinding.ItemTabBinding
import com.ixuea.courses.mymusic.util.Constant

class MainActivity : BaseViewModelActivity <ActivityMainBinding>(){
    companion object{
        private val indicatorTitles=
            intArrayOf(R.string.discovery, R.string.video, R.string.category, R.string.me)
        private val indicatorIcons= intArrayOf(
            R.drawable.selector_tab_discovery,
            R.drawable.selector_tab_video,
            R.drawable.selector_tab_category,
            R.drawable.selector_tab_me
        )
    }
    override fun initDatum() {
        super.initDatum()
        binding.apply {
            //滚动控件
            //表示缓存几个界面 一上来就缓存免得体验感不好
            pager.offscreenPageLimit= indicatorTitles.size
            //设置adapter
            pager.adapter= MainAdapter(this@MainActivity, indicatorTitles.size)

        }
        //底部tab
        for (i in indicatorTitles.indices) {
            ItemTabBinding.inflate(layoutInflater).apply {
                content.setText(indicatorTitles[i])
                icon.setImageResource(indicatorIcons[i])
                binding.indicator.addView(root)
            }
        }
        //第二个依赖
        //“delegate” 有 “代表”“委托”“授权” 等。例如：He was delegated to attend the meeting.（他被委派去参加会议。）
        //“install” “安装”“设置”。例如：You need to install the software before using it.（在使用这个软件之前你需要安装它。）
        ViewPager2Delegate.install(binding.pager,binding.indicator,false )

        val action=intent.action
        if(action== Constant.ACTION_LOGIN){
            startActivity(LoginHomeActivity::class.java)
        }
    }

}