package com.keepcoding.navi.dragonballapp.data

import android.util.Log
import com.keepcoding.navi.dragonballapp.data.local.AuthToken
import com.keepcoding.navi.dragonballapp.data.local.LocalDataSource
import com.keepcoding.navi.dragonballapp.data.mappers.EntityMapper
import com.keepcoding.navi.dragonballapp.data.mappers.PresentationMapper
import com.keepcoding.navi.dragonballapp.data.remote.RemoteDataSource
import com.keepcoding.navi.dragonballapp.domain.repository.HomeRepository
import com.keepcoding.navi.dragonballapp.ui.home.HomeState
import retrofit2.HttpException
import javax.inject.Inject

class HomeRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val authToken: AuthToken,
    private val presentationMapper: PresentationMapper,
    private val entityMapper: EntityMapper) : HomeRepository {

    override suspend fun getRemoteHeroes(): HomeState {
        Log.d("HomeRepositoryImp","Remoto")
        val result = remoteDataSource.getHeroes()
        return  when{
            result.isSuccess -> {
                localDataSource.insertHeroes(entityMapper.dtoMap(result.getOrThrow()))
                HomeState.Success(presentationMapper.dtoMap(result.getOrThrow()))
            }
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

    override suspend fun getHeroes(): HomeState{
        val heroes =localDataSource.getHeroes()
        if (heroes.isNotEmpty()){
            Log.d("HomeRepositoryImp","Local")
            return HomeState.Success(presentationMapper.entityMap(heroes))
        }
        return getRemoteHeroes()
    }

    override suspend fun deleteLocalData() {
        authToken.deleteToken()
        localDataSource.deleteHeroes()
    }

}