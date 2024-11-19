package com.ixuea.courses.mymusic.component.login
import android.text.Html
import androidx.core.content.ContextCompat
import com.drake.channel.receiveEvent
import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.activity.BaseTitleActivity
import com.ixuea.courses.mymusic.component.input.InputIdentityActivity

import com.ixuea.courses.mymusic.databinding.ActivityLoginHomeBinding
import com.ixuea.superui.util.SuperTextUtil

/**
* 主界面
* */
class LoginHomeActivity : BaseLoginActivity<ActivityLoginHomeBinding>() {
    override fun initDatum() {
        super.initDatum()
        SuperTextUtil.setLinkColor(binding.userAgreement,
            ContextCompat.getColor(hostActivity, R.color.link)
        )
        val content= Html.fromHtml(getString(R.string.user_agreement))
        binding.userAgreement.text=content
        receiveEvent<LoginStatusChangedEvent> {
            finish()
        }
    }

    override fun initListeners() {
        super.initListeners()
        binding.usernameLogin.setOnClickListener {startActivity(LoginActivity::class.java)}
        binding.phoneLogin.setOnClickListener {
            InputIdentityActivity.start(hostActivity)
        }
    }

}