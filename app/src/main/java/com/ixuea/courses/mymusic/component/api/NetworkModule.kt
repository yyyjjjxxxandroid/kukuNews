package com.ixuea.courses.mymusic.component.api


import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.ixuea.courses.mymusic.AppContext
import com.ixuea.courses.mymusic.config.Config
import com.ixuea.courses.mymusic.util.JSONUtil
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {
    /**
     * 提供OkHttpClient
     */
    fun provideOkHttpClient(): OkHttpClient {
        //初始化okhttp
        val okhttpClientBuilder = OkHttpClient.Builder()

        //配置缓存   定义的全局AppContext实例，可以方便地获取到应用的缓存目录路径，用于存储缓存数据。
        val cache = Cache(AppContext.instance.cacheDir, Config.NETWORK_CACHE_SIZE)
        okhttpClientBuilder.cache(cache)

        okhttpClientBuilder.connectTimeout(10, TimeUnit.SECONDS) //连接超时时间
            .writeTimeout(10, TimeUnit.SECONDS) //写，也就是将数据发送到服务端超时时间
            .readTimeout(10, TimeUnit.SECONDS) //读，将服务端的数据下载到本地

        //添加公共网络请求参数拦截器
//        okhttpClientBuilder.addInterceptor(TokenInterceptor())

        if (Config.DEBUG) {
            //调试模式

            //创建okhttp日志拦截器
            val loggingInterceptor = HttpLoggingInterceptor()

            //设置日志等级
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

            //添加到网络框架中
            okhttpClientBuilder.addInterceptor(loggingInterceptor)

            //添加chucker实现应用内显示网络请求拦截器
            okhttpClientBuilder.addInterceptor(ChuckerInterceptor.Builder(AppContext.instance).build())
        }
        return okhttpClientBuilder.build()
    }

    /**
     * 提供Retrofit实例
     *
     * @param okHttpClient
     * @return
     */
    fun provideRetrofit(okHttpClient: OkHttpClient?): Retrofit {
        return Retrofit.Builder() //让retrofit使用okhttp
            .client(okHttpClient) //api地址
            .baseUrl(Config.ENDPOINT) //使用gson解析json
            //包括请求参数和响应
            //addConverterFactory方法用于注册一个数据转换器工厂
            //GsonConverterFactory是 Retrofit 提供的一个转换器工厂，它依赖于 Gson 库来进行 JSON 数据的转换。
            //这个工厂的作用是在 Retrofit 进行网络请求和接收响应时，
            // 自动将 JSON 格式的数据转换为指定的 Java 或 Kotlin 对象，以及将对象转换为 JSON 格式的数据进行发送。
            //JSONUtil.createGson()创建一个特定配置的 Gson 对象
            .addConverterFactory(GsonConverterFactory.create(JSONUtil.createGson())) //创建retrofit
            .build()
    }
}