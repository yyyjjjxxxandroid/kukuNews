package com.ixuea.courses.mymusic.util

import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ixuea.courses.mymusic.AppContext
import com.ixuea.courses.mymusic.R

object ImageUtil {
    /*
    * 显示网络图片
    * */
    fun showImage(view:ImageView, data:String?,defaultRes:Int= R.drawable.placeholder){
        if (TextUtils.isEmpty(data)){
           view.setImageResource(defaultRes)
        }else{
            val url =if (data!!.startsWith("http")||data.startsWith("/"))
            data
            else
                ResourceUri.resourceUri(data)
            //           可能要传一些自定义的信息或者请求头才能显示出来就要这样写
            //            val glideUrl = GlideUrl(
//                url, LazyHeaders.Builder()
//                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36 Edg/109.0.1518.70")
//                    .build()
//            )
            val options:RequestOptions= getCommonRequestOptions()
            Glide.with(view).load(url).apply(options).into(view)
        }
    }

    /**
     * 获取公共配置
     *刚刚是默认内存缓存，如果想用磁盘缓存
     * @return
     */
    private fun getCommonRequestOptions(): RequestOptions {
        //创建配置选项      参数round: Boolean = false
        val options = RequestOptions()

        //占位图 网络慢没显示东西出来的时候
//        options.placeholder(R.drawable.placeholder)

        //出错后显示的图片
        //包括：图片不存在等情况
        options.error(R.drawable.placeholder_error)

        //从中心裁剪
//        options.centerCrop()
//
//        if (round) {
//            options.transform(RoundedCorners(DensityUtil.dip2px(AppContext.instance, 5F).toInt()))
//        }

        options.diskCacheStrategy(DiskCacheStrategy.ALL)
        return options
    }

}