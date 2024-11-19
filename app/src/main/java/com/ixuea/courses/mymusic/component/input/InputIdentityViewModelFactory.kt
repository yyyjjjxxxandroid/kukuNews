package com.ixuea.courses.mymusic.component.input

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * 内容VM的工厂类 要给viewmodel传参数 固定这么写（手动创建viewmodel才能传参数给他）
 */
class InputIdentityViewModelFactory(private val style :Int) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        //isAssignableFrom确定此 Class 对象表示的类或接口是否与指定参数表示的类或接口相同，或者是该 Class 类或接口的超类或超接口。
        if (modelClass.isAssignableFrom(InputIdentityViewModel::class.java)) {
            return InputIdentityViewModel(style) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}