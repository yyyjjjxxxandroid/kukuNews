package com.ixuea.courses.mymusic.component.register

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.activity.BaseTitleActivity
import com.ixuea.courses.mymusic.activity.BaseViewModelActivity
import com.ixuea.courses.mymusic.component.login.BaseLoginActivity
import com.ixuea.courses.mymusic.databinding.ActivityRegisterBinding
/**
* 注册界面
* */
class RegisterActivity : BaseLoginActivity<ActivityRegisterBinding>() {
    private lateinit var viewModel: RegisterViewModel
    override fun initDatum() {
        super.initDatum()
        viewModel=ViewModelProvider(this)[RegisterViewModel::class.java]
        initViewModel(viewModel)
        //观察成功结果
        viewModel.success.observe(this){
            loginViewModel.login(binding.phone.text.toString().trim(),binding.password.text.toString().trim())
        }
    }

    override fun initListeners() {
        super.initListeners()
        binding.primary.setOnClickListener {
            viewModel.register(
                binding.nickname.text.toString().trim(),
                binding.phone.text.toString().trim(),
                binding.email.text.toString().trim(),
                binding.password.text.toString().trim(),
                binding.confirmPassword.text.toString().trim()
            )
        }
    }
}