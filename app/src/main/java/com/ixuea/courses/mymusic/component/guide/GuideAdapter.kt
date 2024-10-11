package com.ixuea.courses.mymusic.component.guide

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ixuea.courses.mymusic.adater.BaseFragmentPagerAdapter

/*
* 引导界面适配器
* */
class GuideAdapter(context: Context,fragmentManager: FragmentManager):
    BaseFragmentPagerAdapter<Int>(context,fragmentManager) {



    override fun getItem(position: Int): Fragment {
              return GuideFragment.newInstance(getData(position))
    }

}