package com.ixuea.courses.mymusic.component.login

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.comment.Comment
import com.ixuea.courses.mymusic.component.category.Category
import com.ixuea.courses.mymusic.component.content.Content
import com.ixuea.courses.mymusic.component.input.InputCodePageData
import com.ixuea.courses.mymusic.component.user.User
import com.ixuea.courses.mymusic.entity.response.Meta
import com.ixuea.courses.mymusic.entity.response.onSuccess
import com.ixuea.courses.mymusic.model.BaseViewModel
import com.ixuea.courses.mymusic.repository.DefaultNetworkRepository
import com.ixuea.courses.mymusic.util.PreferenceUtil
import com.ixuea.k.util.StringUtil
import com.ixuea.superui.util.SuperRegularUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.apache.commons.lang3.StringUtils

/*
* 登入界面的viewModel
* */
class LoginViewModel(): BaseViewModel() {
    fun login(username: String, password: String) {
          if (StringUtils.isBlank(username)) {
              _tip.value=R.string.enter_phone_or_email
              //返回
              return
          }
        //如果用户名 不是手机号也不是邮箱 就是格式错误
        if (!(SuperRegularUtil.isPhone(username) || SuperRegularUtil.isEmail(username))){
            _tip.value=R.string.error_username_format
            return
        }
        if (TextUtils.isEmpty(password)){
            _tip.value=R.string.enter_password
            return
        }
        if (!StringUtil.isPassword(password)){
            _tip.value=R.string.error_password_format
            return
        }
        val user= User()
       if (SuperRegularUtil.isPhone(username)){
           user.phone=username
       }else{
           user.email=username
       }
        user.password=password
        login(user)
    }
    //用户名和密码，第三方登入
     fun login(data: User){
        viewModelScope.launch(coroutineExceptionHandler) {
            DefaultNetworkRepository.login(data).onSuccess(viewModel) {
                   //保存用户id
                PreferenceUtil.setUserId(it!!.userId)
                PreferenceUtil.setToken(it!!.session)
                    //聊天token
                PreferenceUtil.setChatToken(it!!.chatToken)
                _success.value=it!!.session
            }
        }
    }
    //验证码登入
   fun login(data: InputCodePageData){
        val user= User()
        user.phone=data.phone
        user.email=data.email
        user.code=data.code
        login(user)
    }



    private val _success= MutableLiveData<String>()
    val success: LiveData<String> =_success


}