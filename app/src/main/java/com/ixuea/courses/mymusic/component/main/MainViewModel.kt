package com.ixuea.courses.mymusic.component.main


import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.FutureTarget
import com.ixuea.courses.mymusic.AppContext
import com.ixuea.courses.mymusic.component.ad.Ad
import com.ixuea.courses.mymusic.component.user.User
import com.ixuea.courses.mymusic.entity.response.onSuccess
import com.ixuea.courses.mymusic.model.BaseViewModel
import com.ixuea.courses.mymusic.repository.DefaultNetworkRepository
import com.ixuea.courses.mymusic.util.FileUtil
import com.ixuea.courses.mymusic.util.PreferenceUtil
import com.ixuea.courses.mymusic.util.ResourceUri
import com.ixuea.superui.util.SuperNetworkUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.io.FileUtils
import java.io.File

/**
 * 首页VM
 * */
class MainViewModel : BaseViewModel() {
    private val _userData = MutableSharedFlow<User>()
    val userData: Flow<User> = _userData
    fun loadUserData() {
        viewModelScope.launch(coroutineExceptionHandler) {
            DefaultNetworkRepository.userDetail(PreferenceUtil.getUserId()).onSuccess(viewModel) {
                _userData.emit(it!!)
            }

        }
    }

    fun loadSplashAd() {
        viewModelScope.launch(coroutineExceptionHandler) {
            DefaultNetworkRepository.ads().onSuccess(viewModel) {
                // 处理广告数据
                if (CollectionUtils.isNotEmpty(it.data)) {
                    downloadAd(it.data!!.last())
                } else {
                    //删除本地广告数据
                    deleteSplashAd()
                }
            }
        }
    }

    private fun deleteSplashAd() {
             val ad=PreferenceUtil.getSplashAd()
        if (ad!=null) {
            //删除本地广告数据
               PreferenceUtil.setSplashAd(null)
             FileUtils.deleteQuietly(FileUtil.adFile(AppContext.instance, ad.icon!!))
        }
    }

    private fun downloadAd(data: Ad) {
        //只有在wifi的情况下才下载
//        if (!SuperNetworkUtil.isWifiConnected(AppContext.instance)) {
//            return
//        }
        //判断文件是否存在，如果存在则不下载
        //这么写有bug，如果用户知道你的机制可以在你这个文件路径下创建一个文件就可以跳过广告,得加密，除非用户破解出来
        val targetFile: File = FileUtil.adFile(AppContext.instance, data.icon!!)
        Log.d("yjxyjx", "downloadAd:${targetFile.absolutePath} ")
        if (targetFile.exists()) {
            PreferenceUtil.setSplashAd(data)
            return
        }
         //我们虽然已经在协程中，但是Glide不支持协程
        Thread{
            try {
                val target:FutureTarget<File> = Glide.with(AppContext.instance)
                    //它指定了加载的结果期望以文件（File）的形式返回
                    .asFile()
                    //转化成绝对路径
                   .load(ResourceUri.resourceUri(data.icon!!))
                       //Glide启动异步加载流程
                   .submit()

                //获取下载文件
                val file:File=target.get()
                //将文件拷贝到我们需要的位置,Glide下载的文件是临时文件，我们需要将它拷贝到我们需要的位置
                FileUtils.moveFile(file,targetFile)
                //广告信息保存到偏好设置里面
                PreferenceUtil.setSplashAd(data)
            }catch (e:Exception){
                 e.printStackTrace()
            }

        }.start()
    }
}