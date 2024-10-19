package com.ixuea.courses.mymusic.component.discovery

import android.os.Bundle
import com.ixuea.courses.mymusic.adapter.TabLayoutViewPager2Mediator
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
    companion object{
        fun newInstance(): DiscoveryFragment {
            val args = Bundle()

            val fragment = DiscoveryFragment()
            fragment.arguments = args
            return fragment
        }
    }
}