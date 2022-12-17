package com.keepcoding.navi.dragonballapp.dependencyinjection

import com.keepcoding.navi.dragonballapp.data.DetailRepositoryImp
import com.keepcoding.navi.dragonballapp.data.HomeRepositoryImp
import com.keepcoding.navi.dragonballapp.data.LoginRepositoryImp
import com.keepcoding.navi.dragonballapp.data.local.AuthToken
import com.keepcoding.navi.dragonballapp.data.local.LocalDataSource
import com.keepcoding.navi.dragonballapp.data.local.LocalDataSourceImp
import com.keepcoding.navi.dragonballapp.data.local.SharedPreferencesImp
import com.keepcoding.navi.dragonballapp.data.remote.RemoteDataSource
import com.keepcoding.navi.dragonballapp.data.remote.RemoteDataSourceImp
import com.keepcoding.navi.dragonballapp.domain.repository.DetailRepository
import com.keepcoding.navi.dragonballapp.domain.repository.HomeRepository
import com.keepcoding.navi.dragonballapp.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindLoginRepository(loginRepositoryImp: LoginRepositoryImp): LoginRepository

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSourceImp: RemoteDataSourceImp): RemoteDataSource

    @Binds
    abstract fun bindAuthToken(sharedPreferenceImp: SharedPreferencesImp): AuthToken

    @Binds
    abstract fun bindHomeRepository(homeRepositoryImp: HomeRepositoryImp): HomeRepository

    @Binds
    abstract fun bindLocalDataSource(localDataSourceImpl: LocalDataSourceImp): LocalDataSource

    @Binds
    abstract fun bindDetailRepository(detailRepositoryImpl: DetailRepositoryImp): DetailRepository

}