package com.ixuea.courses.mymusic.util

import android.view.LayoutInflater
import java.lang.reflect.ParameterizedType

/**
 * 反射工具类
 */
object ReflectUtil {
    /**
     * 创建view binding
     */
    fun <VB> newViewBinding(layoutInflater: LayoutInflater, clazz: Class<*>): VB {
        return try {
            //获取泛型参数对象这段代码尝试获取指定类的泛型父类。
            // 如果直接获取失败（即clazz.genericSuperclass不是ParameterizedType类型）则尝试获取其父类的泛型父类。
            val type = try {
                clazz.genericSuperclass as ParameterizedType
            } catch (e: ClassCastException) {
                clazz.superclass.genericSuperclass as ParameterizedType
            }

            //type.actualTypeArguments[0]：ViewBinding
            // 这里从获取到的泛型参数对象中提取第一个实际类型参数，并将其转换为Class<VB>类型。
            val clazzVB = type.actualTypeArguments[0] as Class<VB>

            //获取inflate方法，通过反射获取视图绑定类中的inflate方法
            val inflateMethod = clazzVB.getMethod("inflate", LayoutInflater::class.java)
            //调用获取到的inflate方法，并将结果转换为VB类型返回。
            inflateMethod.invoke(null, layoutInflater) as VB
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException(e)
        }
    }
}