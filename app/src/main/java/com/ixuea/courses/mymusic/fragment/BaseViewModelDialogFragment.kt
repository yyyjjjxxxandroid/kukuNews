package com.ixuea.courses.mymusic.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.ixuea.courses.mymusic.util.ReflectUtil

abstract class BaseViewModelDialogFragment<VB : ViewBinding> : BaseCommonFragment() {
    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ReflectUtil.newViewBinding(layoutInflater, this.javaClass)
    }

    override fun getLayoutView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //后面继承的子类不用再重写了重写会改变逻辑了
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //避免内存为空
        _binding = null
    }
}