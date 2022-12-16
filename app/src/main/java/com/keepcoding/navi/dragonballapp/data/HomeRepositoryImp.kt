package com.keepcoding.navi.dragonballapp.data

import com.keepcoding.navi.dragonballapp.data.local.LocalDataSource
import com.keepcoding.navi.dragonballapp.data.mappers.PresentationMapper
import com.keepcoding.navi.dragonballapp.data.remote.RemoteDataSource
import com.keepcoding.navi.dragonballapp.domain.Hero
import com.keepcoding.navi.dragonballapp.domain.repository.HomeRepository
import com.keepcoding.navi.dragonballapp.ui.home.HomeState
import com.keepcoding.navi.dragonballapp.ui.login.LoginState
import retrofit2.HttpException
import javax.inject.Inject

class HomeRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    /*private val localDataSource: LocalDataSource,*/
    private val presentationMapper: PresentationMapper) : HomeRepository {

    override suspend fun getRemoteHeroes(): HomeState{
        val result = remoteDataSource.getHeroes()
        return  when{
            result.isSuccess -> HomeState.Success(presentationMapper.dtoMap(result.getOrThrow()))
            else -> {
                when (val exception = result.exceptionOrNull()){
                    is HttpException -> HomeState.Failure("Error de Red ${exception.code()}")
                    else -> {
                        HomeState.Failure(result.exceptionOrNull()?.message ?: "Error!!")
                    }
                }
            }
        }
    }

    override suspend fun getLocalHeroes(): HomeState {
        return HomeState.Loading
    }

}