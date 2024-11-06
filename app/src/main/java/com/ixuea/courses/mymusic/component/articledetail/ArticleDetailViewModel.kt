package com.ixuea.courses.mymusic.component.articledetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.comment.Comment
import com.ixuea.courses.mymusic.component.category.Category
import com.ixuea.courses.mymusic.component.content.Content
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
class ArticleDetailViewModel(private val id: String): BaseViewModel() {
    //_ + private 表示内部使用的变量
    private val _data= MutableSharedFlow<Content>()
    //这个外部使用变量，为什么定义两个？为了避免在fragment中去更改他的数据
    val data: Flow<Content> =_data
    private val _comments= MutableSharedFlow<Meta<Comment>>()
     val comments:Flow<Meta<Comment>> = _comments
    lateinit var content: Content
 fun loadData(){
     viewModelScope.launch(coroutineExceptionHandler) {
         DefaultNetworkRepository.contentDetail(id).onSuccess(viewModel){
             //这个字段是为了让外面直接获取到id
             content=it!!
                 _data.emit(it!!)
             }
     }
     loadMoreComments()
 }

    private fun loadMoreComments() {
        viewModelScope.launch(coroutineExceptionHandler) {
            DefaultNetworkRepository.comments(articleId = id).onSuccess(viewModel){
                _comments.emit(it)
            }
        }
    }

    fun loadReplyComment(data: Comment) {

    }

}