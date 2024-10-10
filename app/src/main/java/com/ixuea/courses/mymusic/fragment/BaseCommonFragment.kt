package com.ixuea.courses.mymusic.fragment

import android.content.Intent
import android.view.View
import androidx.annotation.IdRes


abstract class BaseCommonFragment : BaseFragment() {
    //@IdRes是一个注解，用于指示参数id是一个资源 ID。
    fun <T : View?> findViewById(@IdRes id: Int): T {
        return requireView().findViewById(id)
    }

    /**
     * 获取int值
     *
     * @param key
     * @return
     */
    protected fun extraInt(key: String?): Int {
        return requireArguments().getInt(key, -1)
    }

    /**
     * 启动界面，可以传递一个字符串参数
     *
     * @param clazz
     * @param id
     */
//    protected fun startActivityExtraId(
//        clazz: Class<*>,
//        id: String
//    ) {
//        //创建Intent
//        val intent = Intent(activity, clazz).apply {
//            //传递数据
//            //不为空才传递
//            putExtra(Constant.ID, id)
//        }
//
//        //启动界面
//        startActivity(intent)
//    }

    /**
     * 启动界面
     * @param clazz
     */
    protected fun startActivity(clazz: Class<*>) {
        //创建Intent
        val intent = Intent(requireActivity(), clazz)

        //启动界面
        startActivity(intent)
    }
}