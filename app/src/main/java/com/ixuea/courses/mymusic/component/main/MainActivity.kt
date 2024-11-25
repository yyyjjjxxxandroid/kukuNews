package com.ixuea.courses.mymusic.component.main

import android.util.Log
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.angcyo.tablayout.ViewPagerDelegate
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.ixuea.courses.mymusic.AppContext
import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.activity.BaseViewModelActivity
import com.ixuea.courses.mymusic.component.UserDetailActivity.UserDetailActivity
import com.ixuea.courses.mymusic.component.ad.Ad
import com.ixuea.courses.mymusic.component.login.LoginHomeActivity
import com.ixuea.courses.mymusic.component.user.User
import com.ixuea.courses.mymusic.databinding.ActivityMainBinding
import com.ixuea.courses.mymusic.databinding.ItemTabBinding

import com.ixuea.courses.mymusic.util.Constant
import com.ixuea.courses.mymusic.util.ImageUtil
import com.ixuea.courses.mymusic.util.PreferenceUtil
import com.ixuea.superui.SuperProcessUtil
import com.ixuea.superui.dialog.SuperDialog
import com.ixuea.superui.extension.hide
import com.ixuea.superui.extension.show
import com.ixuea.superui.util.SuperDarkUtil
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import kotlinx.coroutines.launch
import kotlin.math.log

class MainActivity : BaseViewModelActivity <ActivityMainBinding>(){
    private lateinit var viewModel : MainViewModel
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

    override fun initViews() {
        super.initViews()
        //设置沉浸式状态栏(我们现在想内容展示进去但是输入框不展示进去)
        //android:fitsSystemWindows="true",视图会自动在顶部增加一个内边距，使得内容在状态栏下方显示，保证了界面的可读性和美观性。
        QMUIStatusBarHelper.translucent(this)
        if (SuperDarkUtil.isDark(this)) {
            //状态栏文字白色
            QMUIStatusBarHelper.setStatusBarDarkMode(this)
        }else{
            //状态栏文字黑色
            QMUIStatusBarHelper.setStatusBarLightMode(this)
        }
    }
    override fun initDatum() {
        super.initDatum()
       viewModel=ViewModelProvider(this)[MainViewModel::class.java]
        initViewModel(viewModel)
       lifecycleScope.launch {
           viewModel.userData.collect{data->
               showUserData(data)
           }
       }
        binding.content.apply {
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
                binding.content.indicator.addView(root)
            }
        }
        //第二个依赖
        //“delegate” 有 “代表”“委托”“授权” 等。例如：He was delegated to attend the meeting.（他被委派去参加会议。）
        //“install” “安装”“设置”。例如：You need to install the software before using it.（在使用这个软件之前你需要安装它。）
        ViewPager2Delegate.install(binding.content.pager,binding.content.indicator,false )
        val action=intent.action

        if(action== Constant.ACTION_LOGIN){
            startActivity(LoginHomeActivity::class.java)
        }else if (Constant.ACTION_AD==action){
            //广告点击
            processAdClick(intent.getParcelableExtra(Constant.AD)!!)
         }
        viewModel.loadSplashAd()
    }

    private fun processAdClick(data: Ad) {
        Log.d("guanggao", "processAdClick:监听广告点击事件后期用webView跳转 ")
    }

    override fun initListeners() {
        super.initListeners()
        binding.content.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when(position){
                    //状态栏文字白色
                    0,1 ->{QMUIStatusBarHelper.setStatusBarDarkMode(hostActivity)}
                    //状态栏文字黑色
                    else ->{QMUIStatusBarHelper.setStatusBarLightMode(hostActivity)}
                }
            }

        })
        //关闭应用点击
        binding.closeApp.setOnClickListener{
            SuperProcessUtil.killApp()
        }
        binding.userContainer.setOnClickListener{
            closeDrawer()
            if (PreferenceUtil.isLogin()){
                startActivityExtraId(UserDetailActivity::class.java,PreferenceUtil.getUserId())
            }else{
                startActivity(LoginHomeActivity::class.java)
            }
        }
        //退出登录点击
        binding.primary.setOnClickListener{
            SuperDialog.newInstance(supportFragmentManager)
                .setTitleRes(R.string.confirm_logout)
                .setOnClickListener(){
                    AppContext.instance.logout()
                    showNotLogin()
                    closeDrawer()
                }.show()
        }
    }
   fun openDrawer(){
       binding.drawer.openDrawer(GravityCompat.START)
    }
    fun closeDrawer(){
        binding.drawer.closeDrawer(GravityCompat.START)
    }

    override fun onResume() {
        super.onResume()
        showUserInfo()
    }

    private fun showUserInfo() {
        if (PreferenceUtil.isLogin()){
            //已经登入了
            //获取用户信息
            viewModel.loadUserData()
            binding.primary.show()
        }else{
            showNotLogin()
        }
    }
    private fun  showUserData(data: User){
        //显示头像
        ImageUtil.showAvatar(binding.avatar,data.icon)
        //昵称
        binding.nickname.text = data.nickname
    }
    //显示未登录
    private fun showNotLogin(){
     binding.nickname.setText(R.string.login_or_register)
          binding.avatar.setImageResource(R.drawable.default_avatar)
        binding.primary.hide()
    }
}