package com.ixuea.courses.mymusic.component.login

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.ixuea.courses.mymusic.AppContext
import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.activity.BaseTitleActivity
import com.ixuea.courses.mymusic.component.register.RegisterActivity
import com.ixuea.courses.mymusic.config.Config
import com.ixuea.courses.mymusic.databinding.ActivityLoginBinding

class LoginActivity : BaseTitleActivity<ActivityLoginBinding>() {
    private lateinit var viewModel: LoginViewModel
    override fun initDatum() {
        super.initDatum()
        if(Config.DEBUG){
            binding.username.setText("13141111222")
            binding.password.setText("ixueaedu")
        }

        viewModel= ViewModelProvider(this)[LoginViewModel::class.java]
        initViewModel(viewModel)
        viewModel.success.observe(this){
            //成功了

            //执行登入后操作
            AppContext.instance.onLogin()
            finish()
        }
    }

    override fun initListeners() {
        super.initListeners()
        binding.primary.setOnClickListener {
            viewModel.login(binding.username.text.toString().trim(),binding.password.text.toString().trim())
        }
        binding.register.setOnClickListener {
            startActivity(RegisterActivity::class.java)
        }
    }
}