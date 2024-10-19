package com.ixuea.courses.mymusic.component.discovery

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ixuea.courses.mymusic.component.category.Category
import com.ixuea.courses.mymusic.component.content.ContentFragment

/*
* 发现界面适配器
* */
class DiscoverAdapter(fragmentActivity: FragmentActivity,private val datum:List<Category>)
    : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return datum.size
    }

    override fun createFragment(position: Int): Fragment {
                return ContentFragment.newInstance(datum[position].id)
    }

}