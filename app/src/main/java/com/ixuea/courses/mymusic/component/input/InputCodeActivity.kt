package com.ixuea.courses.mymusic.component.input

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.component.login.BaseLoginActivity
import com.ixuea.courses.mymusic.databinding.ActivityInputCodeBinding
import com.ixuea.courses.mymusic.util.Constant
import com.king.view.splitedittext.SplitEditText
import kotlinx.coroutines.launch

class InputCodeActivity : BaseLoginActivity<ActivityInputCodeBinding>() {
  private lateinit var viewModel: InputCodeViewModel
    override fun initDatum() {
        super.initDatum()
        val viewModelFactory=InputCodeViewModelFactory(intent.getParcelableExtra(Constant.DATA)!!)
        viewModel= ViewModelProvider(this,viewModelFactory)[InputCodeViewModel::class.java]
        initViewModel(viewModel)

        lifecycleScope.launch {
            viewModel.codeLogin.collect{
                data->
                      loginViewModel.login(data)
            }
        }
            viewModel.codeSendTarget.observe(this){
                binding.codeSendTarget.text=it
            }
            viewModel.sendText.observe(this) {
                binding.send.text = it
            }

            viewModel.sendEnable.observe(this) {

                binding.send.isEnabled = it
            }
        viewModel.loadData()

    }

    override fun initListeners() {
        super.initListeners()
        //设置验证码输入完成后的监听
        binding.code.setOnTextInputListener(object :SplitEditText.OnSimpleTextInputListener(){
            override fun onTextInputCompleted(text: String) {
                viewModel.processNext(text)
            }
        })
        binding.send.setOnClickListener {
            viewModel.sendCode()
        }
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