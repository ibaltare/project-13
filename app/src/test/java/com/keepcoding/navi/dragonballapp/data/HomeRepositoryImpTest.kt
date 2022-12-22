package com.keepcoding.navi.dragonballapp.data

import com.google.common.truth.Truth
import com.keepcoding.navi.dragonballapp.data.local.AuthToken
import com.keepcoding.navi.dragonballapp.data.local.LocalDataSource
import com.keepcoding.navi.dragonballapp.data.mappers.EntityMapper
import com.keepcoding.navi.dragonballapp.data.mappers.PresentationMapper
import com.keepcoding.navi.dragonballapp.fakes.FakeLocalDataSource
import com.keepcoding.navi.dragonballapp.fakes.FakeRemoteDataSource
import com.keepcoding.navi.dragonballapp.ui.home.HomeState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeRepositoryImpTest {
    // SUT
    private lateinit var homeRepositoryImp: HomeRepositoryImp
    private lateinit var authToken: AuthToken
    private lateinit var presentationMapper: PresentationMapper
    private lateinit var entityMapper: EntityMapper
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp(){
        authToken = mockk()
        presentationMapper = PresentationMapper()
        entityMapper = EntityMapper()
        localDataSource = FakeLocalDataSource()
    }

    @Test
    fun `When getRemoteHeroes expects success return HomeState_Success`() = runTest{
        //given
        val remoteDataSource = FakeRemoteDataSource(true)
        homeRepositoryImp = HomeRepositoryImp(
            remoteDataSource,
            localDataSource,
            authToken,
            presentationMapper,
            entityMapper
        )

        //when
        val actual = homeRepositoryImp.getRemoteHeroes()

        //then
        Truth.assertThat(actual).isInstanceOf(HomeState::class.java)
        Truth.assertThat(actual).isInstanceOf(HomeState.Success::class.java)
    }

    @Test
    fun `When getHeroes expects success return local heroes`() = runTest{
        //given
        val remoteDataSource = FakeRemoteDataSource(true)
        homeRepositoryImp = HomeRepositoryImp(
            remoteDataSource,
            localDataSource,
            authToken,
            presentationMapper,
            entityMapper
        )

        //when
        val actual = homeRepositoryImp.getHeroes()

        //then
        Truth.assertThat(actual).isInstanceOf(HomeState::class.java)
        Truth.assertThat(actual).isInstanceOf(HomeState.Success::class.java)
        Truth.assertThat((actual as HomeState.Success).heroes.count()).isGreaterThan(0)
    }

    @Test
    fun `When getRemoteHeroes fail return HomeState_Failure`() = runTest{
        //given
        val remoteDataSource = FakeRemoteDataSource(false)
        homeRepositoryImp = HomeRepositoryImp(
            remoteDataSource,
            localDataSource,
            authToken,
            presentationMapper,
            entityMapper
        )

        //when
        val actual = homeRepositoryImp.getRemoteHeroes()

        //then
        Truth.assertThat(actual).isInstanceOf(HomeState::class.java)
        Truth.assertThat(actual).isInstanceOf(HomeState.Failure::class.java)
    }

    @Test
    fun `When deleteLocalData call deleteToken`() = runTest{
        //given
        val remoteDataSource = FakeRemoteDataSource(true)
        homeRepositoryImp = HomeRepositoryImp(
            remoteDataSource,
            localDataSource,
            authToken,
            presentationMapper,
            entityMapper
        )
        every { authToken.deleteToken() } returns Unit
        //when
        homeRepositoryImp.deleteLocalData()

        //then
        verify(exactly = 1) { authToken.deleteToken() }
    }
}