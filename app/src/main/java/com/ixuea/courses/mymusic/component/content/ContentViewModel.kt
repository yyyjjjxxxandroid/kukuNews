package com.ixuea.courses.mymusic.component.content

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.ixuea.courses.mymusic.entity.response.Meta
import com.ixuea.courses.mymusic.repository.DefaultNetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.apache.commons.lang3.StringUtils

/*
* 内容界面的viewModel
* */
class ContentViewModel:ViewModel() {
    //_ + private 表示内部使用的变量
    private val _data= MutableSharedFlow<Meta<Content>>()
    //这个外部使用变量，为什么定义两个？为了避免在fragment中去更改他的数据
    val data: Flow<Meta<Content>> =_data
    var lastId: String? = null
    var query: String? = null

    private val _toArticleDetail = MutableSharedFlow<String>()
    val toArticleDetail: SharedFlow<String> = _toArticleDetail

    private val _toCourseDetail = MutableSharedFlow<String>()
    val toCourseDetail: SharedFlow<String> = _toCourseDetail

    private val _previewMedia = MutableSharedFlow<PreviewMediaPageData>()
    val previewMedia: Flow<PreviewMediaPageData> = _previewMedia
 fun loadMore(lastId:String?=null){
     this.lastId=lastId
     viewModelScope.launch {
         //在viewmodel里面请求到数据，
         val r=DefaultNetworkRepository.contents(lastId)
         //然后把数据放在这个流里面，然后fragment就可以通过监听这个流来获取数据
         _data.emit(r.data!!)
     }
 }
    /**
     * 列表item点击
     */
    fun itemClick(data: Content) {
        viewModelScope.launch {
            if (StringUtils.isNotBlank(data.uri)) {
                _toCourseDetail.emit(data.id!!)
            } else {
                _toArticleDetail.emit(data.id!!)
            }
        }
    }
    fun previewMedias(view: RecyclerView, medias: List<String>, position: Int) {
        viewModelScope.launch {
         _previewMedia.emit(PreviewMediaPageData(view, medias, position))
        }

    }
}