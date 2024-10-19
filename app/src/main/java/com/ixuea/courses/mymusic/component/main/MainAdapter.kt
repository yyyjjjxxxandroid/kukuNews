package com.ixuea.courses.mymusic.component.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ixuea.courses.mymusic.component.category.CategoryFragment
import com.ixuea.courses.mymusic.component.discovery.DiscoveryFragment
import com.ixuea.courses.mymusic.component.me.MeFragment
import com.ixuea.courses.mymusic.component.shortviedo.ShortVideoFragment

/*
* 首页界面adapter
* */
class MainAdapter(fragmentActivity: FragmentActivity, private val count:Int) :FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int {
        return count
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            1->ShortVideoFragment.newInstance()
            2->CategoryFragment.newInstance()
            3-> MeFragment.newInstance()
            else-> DiscoveryFragment.newInstance()
        }

    }
}