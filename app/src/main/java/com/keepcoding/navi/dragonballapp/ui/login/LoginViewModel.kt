package com.keepcoding.navi.dragonballapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keepcoding.navi.dragonballapp.domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository) : ViewModel() {

    private val _state : MutableLiveData<LoginState> by lazy {
        MutableLiveData<LoginState>()
    }
    val state: LiveData<LoginState>
        get() = _state

    fun doLogin(user: String, password: String){
        if(user.isBlank() || password.isBlank()){
            setValueOnMainThread(LoginState.Failure("Usuario o contraseña incorrecto"))
        }else{
            viewModelScope.launch {
                val response = withContext(Dispatchers.IO){
                    repository.doLogin(user,password)
                }
                _state.value = response
            }
        }
    }

    fun loadingState(){
        setValueOnMainThread(LoginState.Loading)
    }

    fun authTokenExist(): Boolean{
        return repository.authTokenExist()
    }

    fun saveAuthentication(token: String){
        repository.saveToken(token)
    }

    private fun setValueOnMainThread(value: LoginState) {
        viewModelScope.launch {
            _state.value = value
        }
    }

}