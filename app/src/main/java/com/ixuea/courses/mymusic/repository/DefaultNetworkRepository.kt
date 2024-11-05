package com.ixuea.courses.mymusic.repository

import com.ixuea.courses.mymusic.comment.Comment
import com.ixuea.courses.mymusic.component.api.DefaultNetworkService
import com.ixuea.courses.mymusic.component.content.Content
import com.ixuea.courses.mymusic.entity.response.DetailResponse
import com.ixuea.courses.mymusic.entity.response.ListResponse
import retrofit2.http.Query

/*
* 网络数据仓库
* 主要用于管理数据的获取和存储。
* Repository 作为一个中间层，将数据的来源与使用数据的地方（通常是视图层，如 Activity 或 Fragment）隔离开来。
* 它提供了统一的数据访问接口，使得视图层不需要关心数据是从本地数据库、网络请求还是其他来源获取的。
* */
object DefaultNetworkRepository {
    private val service: DefaultNetworkService by lazy {
        DefaultNetworkService.create()
    }

    suspend fun contentDetail(id: String): DetailResponse<Content> {
        return service.contentDetail(id)
    }

    suspend fun contents(
        last: String? = null,
        categoryId: String? = null,
        userId: String? = null,
        size: Int = 10,
        style: Int? = null
    ): ListResponse<Content> {
        return service.contents(last, categoryId, userId, size, style)
    }

    suspend fun comments(
        articleId: String? = null,
        parentId: String? = null,
        page: Int = 1,
        size: Int = 10,
    ): ListResponse<Comment> {
        return service.comments(articleId, parentId, page, size)
    }
}