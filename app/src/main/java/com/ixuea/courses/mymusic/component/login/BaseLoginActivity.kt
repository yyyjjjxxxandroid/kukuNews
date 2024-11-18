package com.ixuea.courses.mymusic.component.login

import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.drake.channel.receiveEvent
import com.ixuea.courses.mymusic.AppContext
import com.ixuea.courses.mymusic.activity.BaseTitleActivity
//登入通用界面
open class BaseLoginActivity<VB:ViewBinding> : BaseTitleActivity<VB>(){
  lateinit var loginViewModel: LoginViewModel
    override fun initDatum() {
        super.initDatum()
        loginViewModel= ViewModelProvider(this)[LoginViewModel::class.java]
        initViewModel(loginViewModel)
        loginViewModel.success.observe(this){
            //成功了

            //执行登入后操作
            AppContext.instance.onLogin()

        }
         receiveEvent<LoginStatusChangedEvent> {
             finish()
         }
    }
}