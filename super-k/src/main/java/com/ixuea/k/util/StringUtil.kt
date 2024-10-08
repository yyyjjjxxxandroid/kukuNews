package com.ixuea.k.util

object StringUtil {
    //是否符合昵称格式
    fun isNickName(nickName: String): Boolean {
        return nickName.length in 2..10
    }
}