package com.ixuea.courses.mymusic.activity



import com.ixuea.courses.mymusic.AppContext
import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.entity.response.BaseResponse
import com.ixuea.courses.mymusic.model.BaseViewModel
import com.ixuea.superui.extension.longToast
import com.ixuea.superui.extension.shortToast
import com.ixuea.superui.util.SuperNetworkUtil
import org.apache.commons.lang3.StringUtils
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/*
* 本项目的通用逻辑，例如：背景颜色等。
* */
open class BaseLogicActivity:BaseCommonActivity() {
   protected val hostActivity:BaseLogicActivity
     protected  get() = this
    //class Person {
    //    var name: String = "Unknown"
    //        get() = field.uppercase()
    //}
    //在这个例子中，name 属性有一个访问器。当读取 name 属性时，
// 访问器会将属性值转换为大写后返回。

    /**
     * 初始化通用ViewModel逻辑
     */
    protected fun initViewModel(viewModel: BaseViewModel) {
        //关闭界面
        viewModel.finishPage.observe(this) {
            finish()
        }

        //本地提示
        viewModel.tip.observe(this) {
            onTip(it)
        }

        //异常
        viewModel.exception.observe(this) {
            onException(it)
        }

        //网络响应业务失败
        viewModel.response.observe(this) {
            onResponse(it)
        }

        //加载提示
//        viewModel.loading.observe(this) {
//            if (StringUtils.isNotBlank(it)) showLoading(it) else hideLoading()
//        }
    }

    open fun onTip(data: Int) {
        //扩展函数
        data.shortToast()
        onError()
    }
    open fun onError(){

    }

    open fun onResponse(data: BaseResponse) {
        when (data.status) {
            401 -> {
                R.string.error_not_auth.longToast()
                AppContext.instance.logout()
            }

            403 -> {
                R.string.error_not_permission.longToast()
            }

            404 -> {
                R.string.error_not_found.longToast()
            }
        }
        (data.message ?: getString(R.string.error_unknown)).longToast()
        onError()
    }

    open fun onException(data: Throwable) {
                when (data) {
            is SocketException -> {
                //例如：服务器没有启动
                R.string.error_connect_server.longToast()
            }

            is UnknownHostException -> {
                //域名无法解析，例如：域名写错了
                R.string.error_unknown_host.longToast()
            }

            is SocketTimeoutException -> {
                //连接超时，例如：网络特别慢
                R.string.error_network_timeout.longToast()
            }

            is ConnectException -> {
                //以下情况都会触发该异常：
                //服务器没有开启
                //本地网络关闭
                if (SuperNetworkUtil.isNetworkConnected(hostActivity)) {
                    //本地有网络

                    //提示连接服务端失败
                    R.string.error_connect_server.longToast()
                } else {
                    //本地没有网络

                    //提示，你的网络好像不太好
                    R.string.error_network_not_connect.longToast()
                }
            }

            is HttpException -> {
                //http异常，例如：服务端返回401，403
                handleHttpError(data)
            }

            is IllegalArgumentException -> {
                //本地参数错误
                R.string.error_illegal_argument.shortToast()
            }
//            is ClientException -> {
//                "阿里云OSS客户端错误：${data.localizedMessage}".longToast()
//            }
//            is ServiceException -> {
//                "阿里云OSS服务端错误：${data.localizedMessage}".longToast()
//            }
            else -> {
                R.string.error_unknown.shortToast()
            }
        }

    }

    private fun handleHttpError(data: HttpException) {
       AppContext.instance.getString(R.string.error_server_unknown_code,data.code()).longToast()

    }
}