package com.ixuea.courses.mymusic.component.guide

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ixuea.courses.mymusic.databinding.FragmentGuideBinding
import com.ixuea.courses.mymusic.fragment.BaseViewModelFragment
import com.ixuea.courses.mymusic.util.Constant

class GuideFragment :BaseViewModelFragment<FragmentGuideBinding>(){
    override fun initDatum() {
        super.initDatum()
        val data=requireArguments().getInt(Constant.ID)
        binding.icon.setImageResource(data)
    }
    /*
    * 创建方法
    * */
    companion object{
        //Android 在恢复Fragment时，调用的是无参的构造函数，如果使用new的方式构造Fragment，Fragment重新构建会丢失状态。
        // 使用newInstance方式会将参数存储在mArguments中，在Fragment恢复时，会从mArguments中取得参数恢复状态。
        //Fragment (@LayoutRes int contentLayoutId)
        //通过指定一个布局资源 ID，决定了 Fragment 在屏幕上显示的内容和结构。
        // 例如，如果传递的布局资源 ID 是 R.layout.fragment_example，那么这个 Fragment 将会显示在这个布局文件中定义的各种视图组件。
        fun newInstance(data: Int):GuideFragment {
            val args = Bundle()
            args.putInt(Constant.ID,data)

            val fragment = GuideFragment()
            fragment.arguments = args
            return fragment
        }
    }

}