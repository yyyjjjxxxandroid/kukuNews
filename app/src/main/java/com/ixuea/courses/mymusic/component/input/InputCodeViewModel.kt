package com.ixuea.courses.mymusic.component.input

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ixuea.courses.mymusic.AppContext
import com.ixuea.courses.mymusic.R
import com.ixuea.courses.mymusic.entity.response.onSuccess
import com.ixuea.courses.mymusic.model.BaseViewModel
import com.ixuea.courses.mymusic.repository.DefaultNetworkRepository
import com.ixuea.courses.mymusic.util.Constant
import com.ixuea.superui.util.SuperRegularUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import org.apache.commons.lang3.StringUtils

class InputCodeViewModel(private val pageData: InputCodePageData) : BaseViewModel() {
    private var countDownTimer: CountDownTimer? = null
    private var codeRequest = CodeRequest()
    private var codeStyle: Int = Constant.VALUE10

    //发送给谁
    private val _codeSendTarget = MutableLiveData<String>()
    val codeSendTarget: LiveData<String> = _codeSendTarget

    //验证码输入完成以后登入
    private val _codeLogin = MutableSharedFlow<InputCodePageData>()
    val codeLogin: Flow<InputCodePageData> = _codeLogin

    //发送按钮的文本
    private val _sendText = MutableLiveData<String>()
    val sendText: LiveData<String> = _sendText

    //发送按钮是否可用
    private val _sendEnable = MutableLiveData<Boolean>()
    val sendEnable: LiveData<Boolean> = _sendEnable

    fun loadData() {
        var target: String
        Log.d(TAG, "loadData: " + pageData!!.phone + "," + pageData!!.email)
        if (StringUtils.isNotBlank(pageData!!.phone)) {
            target = pageData!!.phone!!
            codeStyle = Constant.VALUE10
            codeRequest!!.phone = pageData!!.phone
        } else {
            target = pageData!!.email!!
            codeStyle = Constant.VALUE0
            codeRequest!!.email = pageData!!.email
        }

        viewModelScope.launch {
            _codeSendTarget.value =
                AppContext.instance.getString(R.string.verification_code_sent_to, target)
        }

        sendCode()
    }

    fun sendCode() {
        viewModelScope.launch(coroutineExceptionHandler) {
            DefaultNetworkRepository.sendCode(codeStyle, codeRequest).onSuccess(viewModel) {
                //发送成功了

                //开始倒计时
                startCountDown()
            }
        }
    }

    /**
     * 开始倒计时
     */
    private fun startCountDown() {
        //倒计时的总时间,间隔
        //单位是毫秒
        countDownTimer = object : CountDownTimer(60000, 1000) {
            /**
             * 间隔时间调用
             * @param millisUntilFinished
             */
            override fun onTick(millisUntilFinished: Long) {
                _sendText.value = AppContext.instance.getString(
                    R.string.resend_count,
                    millisUntilFinished / 1000
                )
            }

            /**
             * 倒计时完成
             */
            override fun onFinish() {
                enableSendButton()

            }
        }

        //启动
        countDownTimer!!.start()
        _sendEnable.value = false
    }

    private fun enableSendButton() {
        _sendText.value = AppContext.instance.getString(R.string.resend)
        _sendEnable.value = true
    }

    fun processNext(data: String) {

        if (pageData!!.style == Constant.STYLE_CODE_LOGIN) {
            //验证码登录
            pageData.code = data

            viewModelScope.launch {
                _codeLogin.emit(pageData)
            }
        } else {
            //先校验验证码
            codeRequest!!.code = data
//            viewModelScope.launch {
//                try {
//                    val r=DefaultNetworkRepository.checkCode(codeRequest)
//                    if (r.isSucceeded) {
//                        //重设密码
//                    }else{
//                        _response.value=r
//                    }
//                } catch (e: Exception) {
//                    _exception.value=e
//                    enableSendButton()
//                }
//            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        //销毁定时器
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
            countDownTimer = null
        }
    }
}