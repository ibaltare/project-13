package com.keepcoding.navi.dragonballapp.ui.login

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keepcoding.navi.dragonballapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _state : MutableLiveData<LoginState> by lazy {
        MutableLiveData<LoginState>()
    }
    val state: LiveData<LoginState>
        get() = _state

    fun doLogin(user: String, password: String){
        if(user.isBlank() || password.isBlank()){
            setValueOnMainThread(LoginState.Error("Resources.getSystem().getString(R.string.error_login_msg)"))
        }else{
            Log.d("LoginViewModel", "$user : $password ")
        }
    }

    fun setValueOnMainThread(value: LoginState) {
        viewModelScope.launch {
            _state.value = value
        }
    }


}