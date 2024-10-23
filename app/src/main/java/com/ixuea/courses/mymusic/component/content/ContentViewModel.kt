package com.ixuea.courses.mymusic.component.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ixuea.courses.mymusic.entity.response.Meta
import com.ixuea.courses.mymusic.repository.DefaultNetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/*
* 内容界面的viewModel
* */
class ContentViewModel:ViewModel() {
    //_ + private 表示内部使用的变量
    private val _data= MutableSharedFlow<Meta<Content>>()
    //这个外部使用变量，为什么定义两个？为了避免在fragment中去更改他的数据
    val data: Flow<Meta<Content>> =_data
 fun loadMore(lastId:String?=null){
     viewModelScope.launch {
         //在viewmodel里面请求到数据，
         val r=DefaultNetworkRepository.contents(lastId)
         //然后把数据放在这个流里面，然后fragment就可以通过监听这个流来获取数据
         _data.emit(r.data!!)
     }
 }
}