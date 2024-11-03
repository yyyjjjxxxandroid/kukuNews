package com.ixuea.courses.mymusic.entity.response

import com.ixuea.courses.mymusic.model.BaseViewModel

class DetailResponse<T> : BaseResponse(){
  var data:T?=null
    //class Content {
    //    var id: String? = null
    //    var title: String? = null
    //    var cotent: String? = null
    //    var icon: String? = null
    //    var uri: String? = null
    //    var province: String? = null
    //}
}
suspend fun <T> DetailResponse<T>.onSuccess(
  viewModel: BaseViewModel,
  action: suspend (data: T?) -> Unit
): Unit {
  if (isSucceeded) {
    action(data)
  } else {
    viewModel._response.value = this
  }
}