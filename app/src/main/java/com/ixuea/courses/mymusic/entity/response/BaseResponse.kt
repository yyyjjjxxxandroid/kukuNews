package com.ixuea.courses.mymusic.entity.response
/**
 * 通用网络请求响应模型
 */
open class BaseResponse {
    /*
    * 状态码 0表示成功
    * */
    var status=0
    /*
    * 出错的信息提示
    * 发生错误了不一定有
    * */
    var message:String?=null
    /*
    * 是否成功
    * */
    val isSucceeded:Boolean
        get()=status==0

}