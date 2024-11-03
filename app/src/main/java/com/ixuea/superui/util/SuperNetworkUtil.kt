package com.ixuea.superui.util

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build

/**
 *
 * * 网络工具类
 * * 可以判断这个设备是否有网络连接，以及是wifi还是移动网络
 * */


object SuperNetworkUtil {
    /**
     * 网络是否连接了
     *
     * @param context
     * @return
     * */
    fun isNetworkConnected(context: Context?): Boolean {
        if (context != null) {
            //低版本
            val connectivityManager = getConnectivityManager(context)
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                val networkInfo = connectivityManager.activeNetworkInfo
                if (networkInfo != null) {
                    return networkInfo.isAvailable
                }
            } else {
                //高版本
                //和获取当前激活的网络
                val activeNetwork = connectivityManager.activeNetwork
                //获取激活网络的详细信息
                val networkCapabilities =
                    connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

                if (networkCapabilities.hasCapability(android.net.NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                    //可以正常上网
                    return true
                }
            }
        }
        return false
    }
    /**
    * wifi是否连接了
    * */
    fun isWifiConnected(context: Context): Boolean {
        return getConnectedType(context) == ConnectivityManager.TYPE_WIFI
    }
    /**移动网络是否连接了
    * 这些返回的信息是有优先记得 比如：同时连接了wifi和移动网络，那么返回false
    * */
    fun isMobileConnected(context: Context): Boolean {
        return getConnectedType(context) == ConnectivityManager.TYPE_MOBILE
    }
  /**
  * 获取网络的连接类型
  * */
    fun getConnectedType(context: Context?): Int {
        if (context!= null) {
        val connectivityManager = getConnectivityManager(context)
            val networkInfo = connectivityManager.activeNetworkInfo
            if (networkInfo!= null&&networkInfo.isAvailable) {
                return networkInfo.type
            }
        }
      return -1
    }
    fun getConnectivityManager(context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}