package com.ixuea.courses.mymusic.component.login

import android.os.Bundle
import android.text.Html
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.activity.BaseTitleActivity
import com.ixuea.courses.mymusic.activity.BaseViewModelActivity
import com.ixuea.courses.mymusic.databinding.ActivityLoginHomeBinding
import com.ixuea.superui.util.SuperTextUtil

/*
* 登入主界面
* */
class LoginHomeActivity : BaseTitleActivity<ActivityLoginHomeBinding>() {
    override fun initDatum() {
        super.initDatum()
        SuperTextUtil.setLinkColor(binding.userAgreement,
            ContextCompat.getColor(hostActivity, R.color.link)
        )
        val content= Html.fromHtml(getString(R.string.user_agreement))
        binding.userAgreement.text=content
    }

}