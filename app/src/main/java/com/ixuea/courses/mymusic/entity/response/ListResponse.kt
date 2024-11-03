package com.ixuea.courses.mymusic.entity.response

import com.ixuea.courses.mymusic.model.BaseViewModel

class ListResponse <T> :BaseResponse() {
    var data:Meta<T>?=null
}
//针对它写一个扩展方法
suspend fun <T> ListResponse<T>.onSuccess(
    viewModel: BaseViewModel,
    action: suspend (data: Meta<T>) -> Unit
): Unit {
    if (isSucceeded) {
        action(data!!)
    } else {
        viewModel._response.value = this
    }
}