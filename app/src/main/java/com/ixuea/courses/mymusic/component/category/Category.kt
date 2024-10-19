package com.ixuea.courses.mymusic.component.category

import com.ixuea.courses.mymusic.entity.Common

/*
* 分类
* */


class Category : Common() {
    lateinit var title: String
    var icon: String? = null
    var data: List<Common>? = null

    companion object {
        fun create(id: String, title: String): Category {
            val r = Category()
            r.id = id
            r.title = title
            return r
        }
    }
}