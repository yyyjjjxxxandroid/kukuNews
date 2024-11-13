package com.ixuea.courses.mymusic.component.main

import androidx.lifecycle.viewModelScope
import com.ixuea.courses.mymusic.component.user.User
import com.ixuea.courses.mymusic.entity.response.onSuccess
import com.ixuea.courses.mymusic.model.BaseViewModel
import com.ixuea.courses.mymusic.repository.DefaultNetworkRepository
import com.ixuea.courses.mymusic.util.PreferenceUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
* 首页VM
* */
class MainViewModel: BaseViewModel() {
private val _userData= MutableSharedFlow<User>()
    val userData: Flow<User> =_userData
    fun loadUserData(){
        viewModelScope.launch(coroutineExceptionHandler) {
            DefaultNetworkRepository.userDetail(PreferenceUtil.getUserId()).onSuccess(viewModel){
                _userData.emit(it!!)
            }

        }
    }
}