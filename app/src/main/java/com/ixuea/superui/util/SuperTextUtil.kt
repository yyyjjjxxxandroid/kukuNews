package com.ixuea.superui.util

import android.text.method.LinkMovementMethod
import android.widget.TextView

object SuperTextUtil {
    /*
    * 设置富文本，超链接颜色
    *
    * @param view
    *  @param color
    * */

    fun setLinkColor(view: TextView, color: Int) {
        //设置后才可以点击
        view.movementMethod= LinkMovementMethod.getInstance()
        view.setLinkTextColor(color)
    }
}