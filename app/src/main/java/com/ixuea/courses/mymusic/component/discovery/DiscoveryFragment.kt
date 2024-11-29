package com.ixuea.courses.mymusic.component.discovery

import android.content.Intent
import android.os.Bundle
import com.ixuea.courses.mymusic.adapter.TabLayoutViewPager2Mediator
import com.ixuea.courses.mymusic.component.main.MainActivity
import com.ixuea.courses.mymusic.component.publish.PublishActivity
import com.ixuea.courses.mymusic.databinding.FragmentDiscoveryBinding

import com.ixuea.courses.mymusic.fragment.BaseViewModelFragment
import com.ixuea.courses.mymusic.util.DataUtil

class DiscoveryFragment :BaseViewModelFragment<FragmentDiscoveryBinding>(){
    override fun initDatum() {
        super.initDatum()
        binding.apply {
            pager.adapter=DiscoverAdapter(requireActivity(), DataUtil.categories)
            TabLayoutViewPager2Mediator(indicator =indicator , pager = pager){
                indicator, pager ->

            }.attach()
        }

    }

    override fun initListeners() {
        super.initListeners()
        //其实在发现页这个fragment里面是操控不了这个侧滑的，侧滑在mainActivity里面
        binding.menu.setOnClickListener {
            //通过将requireActivity()返回的 Activity 对象转换为MainActivity类型，
            // Fragment 就能够明确地访问这些在MainActivity中定义的特有功能。
            (hostActivity as MainActivity).openDrawer()
        }
        binding.add.setOnClickListener {
            loginAfter{
                startActivity(Intent(requireActivity(), PublishActivity::class.java))
            }
        }
    }

    companion object{
        fun newInstance(): DiscoveryFragment {
            val args = Bundle()

            val fragment = DiscoveryFragment()
            fragment.arguments = args
            return fragment
        }
    }
}