package com.ixuea.courses.mymusic.component.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.component.category.Category
import com.ixuea.courses.mymusic.entity.response.Meta
import com.ixuea.courses.mymusic.entity.response.onSuccess
import com.ixuea.courses.mymusic.model.BaseViewModel
import com.ixuea.courses.mymusic.repository.DefaultNetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.apache.commons.lang3.StringUtils

/*
* 内容界面的viewModel
* */
class ContentViewModel(private val categoryId: String?): BaseViewModel() {
    //_ + private 表示内部使用的变量
    private val _data= MutableSharedFlow<Meta<Content>>()
    //这个外部使用变量，为什么定义两个？为了避免在fragment中去更改他的数据
    val data: Flow<Meta<Content>> =_data
    var lastId: String? = null
    var query: String? = null


    //预览图片
    data class PreviewMediaPageData(val view: RecyclerView, val medias: List<String>, val position: Int)

    private val _toArticleDetail = MutableSharedFlow<String>()
    val toArticleDetail: SharedFlow<String> = _toArticleDetail

    private val _toCourseDetail = MutableSharedFlow<String>()
    val toCourseDetail: SharedFlow<String> = _toCourseDetail

    private val _previewMedia = MutableSharedFlow<PreviewMediaPageData>()
    val previewMedia: Flow<PreviewMediaPageData> = _previewMedia
 fun loadMore(lastId:String?=null){
     this.lastId=lastId
     //加了这个参数coroutineExceptionHandler，异常就让他处理，就不用写try catch要手动处理的时候再写try catch
     viewModelScope.launch(coroutineExceptionHandler) {
//         //在viewmodel里面请求到数据，
//         try {
//         //try catch是另一种错误 异常 如没网什么的
////             val r=DefaultNetworkRepository.contents(lastId,categoryId=categoryId)
////             if(r.isSucceeded){
////                 //成功
////                 //然后把数据放在这个流里面，然后fragment就可以通过监听这个流来获取数据
////                 _data.emit(r.data!!)
////             }else{
////                 //业务错误(应该停止刷新不能一直刷新，同时告诉用户出错了)
////                 //这里的失败指的是用户名或密码错误 或者创建用户参数没有填
////                 _response.value=r
////             }
//           //  把上面的代码封装成扩展函数
//             DefaultNetworkRepository.contents(lastId,categoryId=categoryId).onSuccess(viewModel){
//                 _data.emit(it)
//             }
//         } catch (e: Exception) {
//            _exception.value=e
//         }

         DefaultNetworkRepository.contents(lastId,categoryId=categoryId).onSuccess(viewModel){
                 _data.emit(it)
             }
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