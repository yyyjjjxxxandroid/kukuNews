package com.ixuea.courses.mymusic.component.guide

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/*
* 引导界面适配器
* */
class GuideAdapter(val context: Context,fragmentManager: FragmentManager):
    FragmentStatePagerAdapter(fragmentManager) {
      private var datum :MutableList<Int> = mutableListOf()
        override fun getCount(): Int {
         return datum.size
    }

    override fun getItem(position: Int): Fragment {
        Log.d("GuideAdapter", "getItem:${position} ")
              return GuideFragment.newInstance(datum[position])
    }
/*
* 设置数据
* */
    fun setDatum(datum: MutableList<Int>) {
        //写this访问的是在这个类中定义的datum不写this访问的是传进来的参数
        this.datum.clear()
        this.datum.addAll(datum)
        notifyDataSetChanged()

    }
}