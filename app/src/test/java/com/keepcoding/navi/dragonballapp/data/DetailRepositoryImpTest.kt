package com.keepcoding.navi.dragonballapp.data

import com.google.common.truth.Truth
import com.keepcoding.navi.dragonballapp.data.local.LocalDataSource
import com.keepcoding.navi.dragonballapp.data.mappers.EntityMapper
import com.keepcoding.navi.dragonballapp.data.mappers.LocalizationMapper
import com.keepcoding.navi.dragonballapp.data.mappers.PresentationMapper
import com.keepcoding.navi.dragonballapp.data.remote.RemoteDataSource
import com.keepcoding.navi.dragonballapp.domain.HeroDetail
import com.keepcoding.navi.dragonballapp.fakes.FakeLocalDataSource
import com.keepcoding.navi.dragonballapp.fakes.FakeRemoteDataSource
import com.keepcoding.navi.dragonballapp.ui.detail.DetailState
import com.keepcoding.navi.dragonballapp.ui.detail.LikeResultState
import com.keepcoding.navi.dragonballapp.utils.Generator
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

@OptIn(ExperimentalCoroutinesApi::class)
class DetailRepositoryImpTest{
    //SUT
    private lateinit var detailRepositoryImp: DetailRepositoryImp
    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var localDataSource: LocalDataSource
    private lateinit var presentationMapper: PresentationMapper
    private lateinit var localizationMapper: LocalizationMapper
    private lateinit var entityMapper: EntityMapper

    @Before
    fun setUp(){
        presentationMapper = PresentationMapper()
        entityMapper = EntityMapper()
        localizationMapper = LocalizationMapper()
    }

    @Test
    fun `When getLocalHero expects success return HeroDetail`() = runTest{
        //given
        remoteDataSource = FakeRemoteDataSource(true)
        localDataSource = FakeLocalDataSource()
        detailRepositoryImp = DetailRepositoryImp(
            remoteDataSource,
            localDataSource,
            presentationMapper,
            localizationMapper,
            entityMapper
        )

        //when
        val actual = detailRepositoryImp.getLocalHero(anyString())

        //then
        Truth.assertThat(actual).isNotNull()
        Truth.assertThat(actual).isInstanceOf(HeroDetail::class.java)
    }

    @Test
    fun `When setHeroLike expects success return success state`() = runTest{
        //given
        remoteDataSource = FakeRemoteDataSource(true)
        localDataSource = FakeLocalDataSource()
        detailRepositoryImp = DetailRepositoryImp(
            remoteDataSource,
            localDataSource,
            presentationMapper,
            localizationMapper,
            entityMapper
        )

        //when
        val actual = detailRepositoryImp.setHeroLike(anyString())

        //then
        Truth.assertThat(actual).isInstanceOf(LikeResultState.SuccessLikeHero::class.java)
    }

    @Test
    fun `When setHeroLike fail expects failure state`() = runTest{
        //given
        remoteDataSource = FakeRemoteDataSource(false)
        localDataSource = FakeLocalDataSource()
        detailRepositoryImp = DetailRepositoryImp(
            remoteDataSource,
            localDataSource,
            presentationMapper,
            localizationMapper,
            entityMapper
        )

        //when
        val actual = detailRepositoryImp.setHeroLike(anyString())

        //then
        Truth.assertThat(actual).isInstanceOf(LikeResultState.Failure::class.java)
    }

    @Test
    fun `When getHeroLocalizations expects success return success with Localizations`() = runTest{
        //given
        remoteDataSource = FakeRemoteDataSource(true)
        localDataSource = FakeLocalDataSource()
        detailRepositoryImp = DetailRepositoryImp(
            remoteDataSource,
            localDataSource,
            presentationMapper,
            localizationMapper,
            entityMapper
        )

        //when
        val actual = detailRepositoryImp.getHeroLocalizations(anyString())

        //then
        Truth.assertThat(actual).isInstanceOf(DetailState.SuccessLocalizationHero::class.java)
        Truth.assertThat((actual as DetailState.SuccessLocalizationHero).localizations?.count())
            .isGreaterThan(0)
    }

    @Test
    fun `When getHeroLocalizations fail return failure state`() = runTest{
        //given
        remoteDataSource = FakeRemoteDataSource(false)
        localDataSource = FakeLocalDataSource()
        detailRepositoryImp = DetailRepositoryImp(
            remoteDataSource,
            localDataSource,
            presentationMapper,
            localizationMapper,
            entityMapper
        )

        //when
        val actual = detailRepositoryImp.getHeroLocalizations(anyString())

        //then
        Truth.assertThat(actual).isInstanceOf(DetailState.Failure::class.java)
    }

    @Test
    fun `When updateLocalHero call updateHero from localDataSource`() = runTest{
        //given
        remoteDataSource = FakeRemoteDataSource(false)
        localDataSource = mockk()
        detailRepositoryImp = DetailRepositoryImp(
            remoteDataSource,
            localDataSource,
            presentationMapper,
            localizationMapper,
            entityMapper
        )
        every { localDataSource.updateHero(any() ) } returns Unit
        //when
        detailRepositoryImp.updateLocalHero(Generator.getHeroDetail())

        //then
        verify(exactly = 1) { localDataSource.updateHero(any()) }
    }
}