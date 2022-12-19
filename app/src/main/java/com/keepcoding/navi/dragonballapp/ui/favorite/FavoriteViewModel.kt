package com.keepcoding.navi.dragonballapp.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keepcoding.navi.dragonballapp.domain.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: FavoriteRepository): ViewModel() {

    private val _state : MutableLiveData<FavoriteState> by lazy {
        MutableLiveData<FavoriteState>()
    }
    val state: LiveData<FavoriteState>
        get() = _state

    private fun setValueOnMainThread(value: FavoriteState) {
        viewModelScope.launch {
            _state.value = value
        }
    }

    fun getHeroes(){
        setValueOnMainThread(FavoriteState.Loading)
        viewModelScope.launch {
            _state.value = withContext(Dispatchers.IO){
                repository.getLocalFavoriteHeroes()
            }
        }
    }
}