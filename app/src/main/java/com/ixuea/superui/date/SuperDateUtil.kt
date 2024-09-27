package com.ixuea.superui.date

import java.util.Calendar

object SuperDateUtil {
    /*
    * 获取年
    * */
    fun currentYear(): Int {
        return Calendar.getInstance().get(Calendar.YEAR)
    }
}