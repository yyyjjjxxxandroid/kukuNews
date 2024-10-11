package com.ixuea.courses.mymusic.component.guide

import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.activity.BaseViewModelActivity
import com.ixuea.courses.mymusic.databinding.ActivityGuideBinding
import com.ixuea.courses.mymusic.util.PreferenceUtil

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
        binding.loginOrRegister.setOnClickListener {
              PreferenceUtil.setShowGuide(false)
        }
        binding.experienceNow.setOnClickListener {
            PreferenceUtil.setShowGuide(false)
        }

    }

}