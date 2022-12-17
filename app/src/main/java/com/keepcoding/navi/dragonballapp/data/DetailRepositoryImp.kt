package com.keepcoding.navi.dragonballapp.data

import com.keepcoding.navi.dragonballapp.data.local.LocalDataSource
import com.keepcoding.navi.dragonballapp.data.local.model.HeroEntity
import com.keepcoding.navi.dragonballapp.data.mappers.EntityMapper
import com.keepcoding.navi.dragonballapp.data.mappers.LocalizationMapper
import com.keepcoding.navi.dragonballapp.data.mappers.PresentationMapper
import com.keepcoding.navi.dragonballapp.data.remote.RemoteDataSource
import com.keepcoding.navi.dragonballapp.domain.HeroDetail
import com.keepcoding.navi.dragonballapp.domain.repository.DetailRepository
import com.keepcoding.navi.dragonballapp.ui.detail.DetailState
import retrofit2.HttpException
import javax.inject.Inject

class DetailRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val presentationMapper: PresentationMapper,
    private val localizationMapper: LocalizationMapper,
    private val entityMapper: EntityMapper,
): DetailRepository {

    override suspend fun getLocalHero(heroId: String): HeroDetail {
        val hero = localDataSource.getHeroById(heroId)
        return presentationMapper.entityDetailMap(hero)
    }

    override suspend fun setHeroLike(hero: String) {
        remoteDataSource.setHeroLike(hero)
    }

    override suspend fun getHeroLocalizations(heroId: String): DetailState {
        val result = remoteDataSource.getHeroLocalizations(heroId)
        return when{
            result.isSuccess -> DetailState.SuccessHeroLocalization(localizationMapper.dtoMap(result.getOrThrow()))
            else -> {
                when (val exception = result.exceptionOrNull()){
                    is HttpException -> DetailState.Failure("Error de Red ${exception.code()}")
                    else -> DetailState.Failure(result.exceptionOrNull()?.message ?: "Error!!")
                }
            }
        }
    }

    override suspend fun updateLocalHero(hero: HeroDetail) {
        localDataSource.updateHero(entityMapper.pdMap(hero))
    }
}