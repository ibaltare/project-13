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

    private fun setValueOnMainThread(value: DetailState){
        viewModelScope.launch { _state.value = value }
    }

    fun getHeroDetail(heroId: String){
        setValueOnMainThread(DetailState.Loading)
        viewModelScope.launch {
            val hero = withContext(Dispatchers.IO){
                repository.getLocalHero(heroId)
            }
            _heroDetail = hero
            _state.value = DetailState.SuccessLocalhero(hero)
            _like.value = _heroDetail.favorite

            val localization = withContext(Dispatchers.IO){
                repository.getHeroLocalizations(hero.id)
            }
            Log.d("LOCALIZATIONS",localization.toString())
            _state.value = DetailState.SuccessHeroLocalization(null)
        }
    }

    fun setHeroLike(){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                repository.setHeroLike(_heroDetail.id)
                _heroDetail.favorite = !_heroDetail.favorite
                repository.updateLocalHero(_heroDetail)
            }
            withContext(Dispatchers.Main){
                _like.value = _heroDetail.favorite
            }
        }
    }
}