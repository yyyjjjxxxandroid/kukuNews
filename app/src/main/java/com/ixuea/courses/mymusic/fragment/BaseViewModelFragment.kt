package com.ixuea.courses.mymusic.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.ixuea.courses.mymusic.entity.response.BaseResponse
import com.ixuea.courses.mymusic.model.BaseViewModel
import com.ixuea.courses.mymusic.util.ReflectUtil

abstract class BaseViewModelFragment<VB : ViewBinding> : BaseLogicFragment() {
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
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
//    /**
//    * 初始话通用viewModel逻辑
//    * */
//    protected  fun initViewModel(viewModel:BaseViewModel){
//        //关闭界面
//        viewModel.finishPage.observe(this){
//            hostActivity.finish()
//        }
//        //本地提示
//        viewModel.tip.observe(this){
//            onTip(it)
//        }
//        //异常
//        viewModel.exception.observe(this){
//            onException(it)
//        }
//        //网络响应业务失败
//        viewModel.response.observe(this){
//            onResponse(it)
//        }
//        //加载提示
//        viewModel.loading.observe(this){
//
//        }
//    }
//    open fun onTip(data: Int) {
//        hostActivity.onTip(data)
//        onError()
//    }
//
//    open fun onResponse(data: BaseResponse) {
//        hostActivity.onResponse(data)
//        onError()
//    }
//
//    open fun onException(data: Throwable) {
//        hostActivity.onException(data)
//        onError()
//    }
//
//    open fun onError() {
//
//    }

}