package com.keepcoding.navi.dragonballapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keepcoding.navi.dragonballapp.domain.repository.HomeRepository
import com.keepcoding.navi.dragonballapp.ui.login.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository): ViewModel(){

    private val _state : MutableLiveData<HomeState> by lazy {
        MutableLiveData<HomeState>()
    }
    val state: LiveData<HomeState>
        get() = _state

    private fun setValueOnMainThread(value: HomeState) {
        viewModelScope.launch {
            _state.value = value
        }
    }

    fun getHeroes(){
        setValueOnMainThread(HomeState.Loading)
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                repository.getHeroes()
            }
            _state.value = result
        }
    }

    fun Logout(){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                repository.deleteLocalData()
            }
            _state.value = HomeState.Logout
        }
    }

}