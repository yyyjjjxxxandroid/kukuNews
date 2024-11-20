package com.ixuea.courses.mymusic.component.api

import com.ixuea.courses.mymusic.comment.Comment
import com.ixuea.courses.mymusic.component.content.Content
import com.ixuea.courses.mymusic.component.input.CodeRequest
import com.ixuea.courses.mymusic.component.login.Session
import com.ixuea.courses.mymusic.component.user.User
import com.ixuea.courses.mymusic.entity.Base
import com.ixuea.courses.mymusic.entity.BaseId
import com.ixuea.courses.mymusic.entity.response.DetailResponse
import com.ixuea.courses.mymusic.entity.response.ListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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
    //用户详情
    @GET("v1/users/{id}")
    suspend fun userDetail(@Path("id") id: String): DetailResponse<User>

    // 评论列表
    @GET("v1/comments")
    //挂起函数suspend 不可以直接调用 需要放到协程里面直接调用
    //这里面不推荐使用默认值  推荐写到repository去
    suspend fun comments(
        @Query(value = "article_id") articleId: String?,
        @Query(value = "parent_id") parentId: String?,
        @Query(value = "page") page: Int,
        @Query(value = "size") size: Int,
    ): ListResponse<Comment>
  //登入
    @POST("v1/sessions")
    suspend fun login(@Body data:User):DetailResponse<Session>
//注册
    @POST("v1/users")
    suspend fun register(@Body data: User): DetailResponse<BaseId>
    //region 验证码
    /**
     * 发送验证码
     *
     * @param data
     * @return
     */
    @POST("v1/codes")
    suspend fun sendCode(
        @Query(value = "style") style: Int,
        @Body data: CodeRequest
    ): DetailResponse<Base>

    /**
     * 校验验证码
     *
     * @param data
     * @return
     */
    @POST("v1/codes/check")
    suspend fun checkCode(
        @Body data: CodeRequest
    ): DetailResponse<Base>
    //endregion



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
