package com.ixuea.courses.mymusic.adapter

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
/*
* 通用FragmentPagerAdapter
* 主要是创建实现了通用的方法
* 直接用FragmentPagerAdapter有可能内存泄露
* 所以直接使用FragmentStatePagerAdapter
* */
//（在管理 Fragment 时，会将其保存在内存中以实现快速切换页面时的流畅性）
//* 当 Activity 或 Fragment 的生命周期结束时，如果FragmentPagerAdapter仍然持有对其中的 Fragment 的引用，
//* 那么这些 Fragment 就无法被垃圾回收器回收，从而导致内存泄露。如果 Fragment 中包含一些耗时的操作或者大量的数据，
//* 而这些 Fragment 又因为FragmentPagerAdapter的引用而无法被回收，就会导致更多的内存被占用
abstract class BaseFragmentPagerAdapter<T> (val context: Context, fragmentManager: FragmentManager):
    FragmentStatePagerAdapter(fragmentManager){
        //当一个类继承自抽象类时，它必须提供抽象类中所有抽象方法的具体实现。否则，这个子类也必须声明为抽象类。
    private var datum :MutableList<T> = mutableListOf()
    override fun getCount(): Int {
        return datum.size
    }



    /*
* 设置数据
* */
    fun setDatum(datum: MutableList<T>) {
        //写this访问的是在这个类中定义的datum不写this访问的是传进来的参数
        this.datum.clear()
        this.datum.addAll(datum)
        notifyDataSetChanged()
    }

    fun getData(position :Int):T
    {
        return datum[position]
    }
    }