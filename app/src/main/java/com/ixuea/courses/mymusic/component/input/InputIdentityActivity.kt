package com.ixuea.courses.mymusic.component.input

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.drake.channel.receiveEvent
import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.activity.BaseTitleActivity
import com.ixuea.courses.mymusic.component.login.LoginStatusChangedEvent
import com.ixuea.courses.mymusic.databinding.ActivityInputIdentityBinding
import com.ixuea.courses.mymusic.util.Constant
import kotlinx.coroutines.launch
import org.apache.commons.lang3.StringUtils

/**
* 输入手机号 邮箱的通用界面
* */
class InputIdentityActivity : BaseTitleActivity<ActivityInputIdentityBinding>() {

    private lateinit var viewModel: InputIdentityViewModel

    override fun initDatum() {
        super.initDatum()
        val viewModelFactory=InputIdentityViewModelFactory(intent.getIntExtra(Constant.STYLE,-1))
       viewModel= ViewModelProvider(this,viewModelFactory)[InputIdentityViewModel::class.java]
        initViewModel(viewModel)

           lifecycleScope.launch {
               viewModel.title.collect{
                       data->
                   setTitle(data)
               }
           }
        lifecycleScope.launch {
            viewModel.toNext.collect{
            data->
               InputCodeActivity.start(this@InputIdentityActivity,data)

            }
        }
        viewModel.loadData()

        receiveEvent<LoginStatusChangedEvent> {
            finish()
        }
    }

    override fun initListeners() {
        super.initListeners()

        //输入框改变了
        binding.username.doAfterTextChanged {
            val notBlank= StringUtils.isNotBlank(it.toString().trim())
            binding.primary.isEnabled=notBlank
        }
        binding.primary.setOnClickListener {
            viewModel.primaryClick(binding.username.text.toString().trim())
        }
    }
    companion object{
        //启动界面
        fun start(context: Context, style:Int=Constant.STYLE_CODE_LOGIN){
            val intent= Intent(context,InputIdentityActivity::class.java)
            intent.putExtra(Constant.STYLE,style)
            context.startActivity(intent)
        }
    }
}