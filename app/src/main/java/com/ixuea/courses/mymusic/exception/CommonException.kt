package com.ixuea.courses.mymusic.exception

import com.ixuea.courses.mymusic.entity.response.BaseResponse

/**
 * 全局通用异常
 */
class CommonException(
    /**
     * 网络响应
     */
    val networkResponse: BaseResponse? = null,

    val throwable: Throwable? = null,

    var tipString: String? = null,
    var tipIcon: Int? = null,
) : RuntimeException()