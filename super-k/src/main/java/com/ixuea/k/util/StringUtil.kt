package com.ixuea.k.util

object StringUtil {
    //是否符合昵称格式
    fun isNickName(nickName: String): Boolean {
        return nickName.length in 2..10
    }
    //是否符合密码格式
    fun isPassword(password: String): Boolean {
        return password.length in 6..15
    }
}