package com.ixuea.courses.mymusic

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ixuea.courses.mymusic.activity.BaseViewModelActivity
import com.ixuea.courses.mymusic.component.login.LoginHomeActivity
import com.ixuea.courses.mymusic.databinding.ActivityMainBinding
import com.ixuea.courses.mymusic.util.Constant

class MainActivity : BaseViewModelActivity <ActivityMainBinding>(){
    override fun initDatum() {
        super.initDatum()
        val action=intent.action
        if(action== Constant.ACTION_LOGIN){
            startActivity(LoginHomeActivity::class.java)
        }
    }
}