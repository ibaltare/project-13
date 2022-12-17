package com.keepcoding.navi.dragonballapp.data

import com.keepcoding.navi.dragonballapp.data.local.LocalDataSource
import com.keepcoding.navi.dragonballapp.data.mappers.PresentationMapper
import com.keepcoding.navi.dragonballapp.data.remote.RemoteDataSource
import com.keepcoding.navi.dragonballapp.domain.HeroDetail
import com.keepcoding.navi.dragonballapp.domain.repository.DetailRepository
import javax.inject.Inject

class DetailRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val presentationMapper: PresentationMapper
): DetailRepository {

    override suspend fun getLocalHero(heroId: String): HeroDetail {
        val hero = localDataSource.getHeroById(heroId)
        return presentationMapper.entityDetailMap(hero)
    }
}