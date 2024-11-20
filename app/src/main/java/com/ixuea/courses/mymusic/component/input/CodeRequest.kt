package com.ixuea.courses.mymusic.component.input

import com.ixuea.courses.mymusic.entity.Base

/**
 * 验证码请求参数
 *
 *
 * 也可以复用User模型
 */
class CodeRequest : Base() {
    var phone: String? = null
    var email: String? = null

    /**
     * 如果发送频繁了，也可以发送验证码时，传递图形验证码
     */
    var code: String? = null
}