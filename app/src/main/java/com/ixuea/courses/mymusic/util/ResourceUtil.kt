package com.ixuea.courses.mymusic.util

import android.content.Context
import com.ixuea.courses.mymusic.config.Config

object ResourceUtil {
    /**
     * 将相对资源转为绝对路径
     *
     * @param uri 放到自己服务端的资源以files开头，其他资源都在阿里云oss
     * @return
     */
    fun resourceUri(uri: String): String {
        return if (uri.startsWith("content://")) {
            uri
        } else if (uri.startsWith("http")) {
            uri
        } else if (uri.startsWith("files"))
            uri
        else if (uri.startsWith("r/")) {
            //上传到服务端的资源
            "${Config.ENDPOINT}${uri}"
        } else
            String.format(Config.RESOURCE_ENDPOINT, uri)
    }

    /**
     * 获取颜色属性值
     */
    fun getColorAttributes(context: Context, data: Int): Int {
        val typedArray = context.obtainStyledAttributes(intArrayOf(data))
        return typedArray.getColor(0, 0)
    }
}