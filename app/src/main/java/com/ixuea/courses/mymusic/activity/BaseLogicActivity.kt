package com.ixuea.courses.mymusic.activity
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
}