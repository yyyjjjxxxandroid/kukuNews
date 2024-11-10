package com.ixuea.courses.mymusic.activity

import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.ixuea.courses.mymusic.R

/*
* 标题界面（通用）
* */
open class BaseTitleActivity<VB:ViewBinding>:BaseViewModelActivity<VB>() {
    lateinit var toolbar: Toolbar
    override fun initViews() {
        super.initViews()
        toolbar=binding.root.findViewById(R.id.toolbar)
        //初始化
        setSupportActionBar(toolbar)
        if (isShowBackMenu()){
            showBackMenu()
        }
    }

    /**
     * 显示返回按钮
     */
    protected open fun showBackMenu() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * 是否显示返回按钮
     * @return
     */
    protected open fun isShowBackMenu(): Boolean {
        return true
    }

    /**
     * 菜单点击了回调
     * @param item
     * @return
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                //Toolbar返回按钮点击
                onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}