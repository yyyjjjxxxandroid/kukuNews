package com.ixuea.courses.mymusic.component.api

import com.ixuea.courses.mymusic.component.content.Content
import com.ixuea.courses.mymusic.entity.response.DetailResponse
import com.ixuea.courses.mymusic.entity.response.ListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
/*
* 网络api
* */
interface DefaultNetworkService {
    // 内容列表
        @GET("v1/contents")
        //挂起函数suspend 不可以直接调用 需要放到协程里面直接调用
        suspend fun contents(
            @Query(value = "last") last: String?,
            @Query(value = "category_id") categoryId: String?,
            @Query(value = "user_id") userId: String?,
            @Query(value = "size") size: Int,
            @Query(value = "style") style: Int?=null,
        ): ListResponse<Content>
     //内容详情
     @GET("v1/contents/{id}")
     suspend fun contentDetail(@Path("id") id: String): DetailResponse<Content>
    companion object {
            fun create(): DefaultNetworkService {
                ///创建 Retrofit
                return NetworkModule.provideRetrofit(NetworkModule.provideOkHttpClient())
                    //使用 Retrofit 实例创建一个实现了DefaultNetworkService接口的对象。
                    .create(DefaultNetworkService::class.java)
                //一旦有了 Retrofit 对象，就可以使用它的 create 方法来创建一个实现了 DefaultNetworkService 接口的实例。
            // 这意味着可以通过这个实例调用在 DefaultNetworkService 接口中定义的网络请求方法，如 contents 和 contentDetail。
            }
        }
    }
