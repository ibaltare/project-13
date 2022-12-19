package com.keepcoding.navi.dragonballapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keepcoding.navi.dragonballapp.domain.HeroDetail
import com.keepcoding.navi.dragonballapp.domain.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: DetailRepository): ViewModel() {

    private lateinit var _heroDetail: HeroDetail

    private val _state: MutableLiveData<DetailState> by lazy {
        MutableLiveData<DetailState>()
    }
    val state: LiveData<DetailState>
        get() = _state

    private val _like: MutableLiveData<Boolean>by lazy {
        MutableLiveData<Boolean>()
    }
    val like: LiveData<Boolean>
        get() = _like

    private val _btnLike: MutableLiveData<Boolean>by lazy {
        MutableLiveData<Boolean>()
    }
    val btnLike: LiveData<Boolean>
        get() = _btnLike

    private fun setValueOnMainThread(value: DetailState){
        viewModelScope.launch { _state.value = value }
    }

    fun getHeroDetail(heroId: String){
        setValueOnMainThread(DetailState.Loading(true))
        viewModelScope.launch {
            val hero = withContext(Dispatchers.IO){
                repository.getLocalHero(heroId)
            }
            _heroDetail = hero
            _state.value = DetailState.SuccessLocalHero(_heroDetail)
            _like.value = _heroDetail.favorite
            _btnLike.value = true
            val localization = withContext(Dispatchers.IO){
                repository.getHeroLocalizations(hero.id)
            }
            Log.d("LOCALIZATIONS",localization.toString())
            _state.value = DetailState.SuccessLocalizationHero(null)
        }
    }

    fun setHeroLike(){
        setValueOnMainThread(DetailState.Loading(true))
        viewModelScope.launch{
            _btnLike.value = false
            val result = withContext(Dispatchers.IO){
                repository.setHeroLike(_heroDetail.id)
            }
            when(result){
                is LikeResultState.SuccessLikeHero -> {
                    _heroDetail.favorite = !_heroDetail.favorite
                    withContext(Dispatchers.IO){
                        repository.updateLocalHero(_heroDetail)
                    }
                    _like.value = _heroDetail.favorite
                    _state.value = DetailState.Loading(false)
                }
                is LikeResultState.Failure -> _state.value = DetailState.Failure(result.message)
            }
            _btnLike.value = true
        }
    }
}