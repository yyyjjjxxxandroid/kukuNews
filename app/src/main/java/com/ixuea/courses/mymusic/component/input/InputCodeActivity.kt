package com.ixuea.courses.mymusic.component.input

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.util.Constant

class InputCodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_code)
    }
    //定义这样一个方法的好处就是其他开发人员要跳转这样一个界面，没有写这个方法的话，只能查看你的代码或者注释，写上注释告诉别人要传什么值，更好维护！
    companion object {
        //伴生对象的作用
        //类似静态成员容器：相当于 Java 里的静态成员，可在不创建类实例的情况下访问其成员，方便组织与类相关功能。
        //实现单例模式：常用于实现简单单例，比如定义私有实例属性及公有获取实例方法，确保单例存在。
        //存放共享数据和常量：可放置类相关常量或通用数据，便于统一管理与访问。
        fun start(context: Context,data: InputCodePageData){
            val intent= Intent(context,InputCodeActivity::class.java)
             intent.putExtra(Constant.DATA,data)
            context.startActivity(intent)
        }
      }
}