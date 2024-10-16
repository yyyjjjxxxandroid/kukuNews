package com.ixuea.courses.mymusic.component.content

import com.ixuea.courses.mymusic.component.user.User
import com.ixuea.courses.mymusic.entity.Common



class Content : Common() {
    var title: String? = null
    var content: String? = null
    var icon: String? = null
    var uri: String? = null
    var province: String? = null
    var user: User? = null
    var style: Int = 0
    var width: Int = 0
    var height: Int = 0
    var duration: Int = 0
    var commentsCount: Long = 0
    var clicksCount: Long = 0
    var likesCount: Long = 0
    var icons: List<String>? = null

    /**
     * 是否点赞
     * 有值表示点赞
     * null表示没点赞
     */
    var likeId: String? = null


    /**
     * 是否是竖屏视频
     *
     * @return
     */
    fun isPortraitVideo(): Boolean {
        return width < height
    }

    fun isLike(): Boolean {
        return likeId != null
    }
}