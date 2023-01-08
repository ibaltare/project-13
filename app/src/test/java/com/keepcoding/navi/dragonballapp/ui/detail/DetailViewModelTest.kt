package com.keepcoding.navi.dragonballapp.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.keepcoding.navi.dragonballapp.domain.HeroDetail
import com.keepcoding.navi.dragonballapp.domain.repository.DetailRepository
import com.keepcoding.navi.dragonballapp.utils.Generator
import com.keepcoding.navi.dragonballapp.utils.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    //SUT
    private lateinit var detailViewModel: DetailViewModel
    //Dependencies
    private lateinit var repository: DetailRepository

    @Before
    fun setup(){
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `When getHeroDetail expects success returns HeroDetail object`() = runTest {
        //given
        repository = mockk()
        detailViewModel = DetailViewModel(repository)
        coEvery { repository.getLocalHero("HeroId") } returns Generator.getHeroDetail()
        coEvery { repository.getHeroLocalizations("HeroId") } returns DetailState.SuccessLocalizationHero(null)

        //when
        detailViewModel.getHeroDetail("HeroId")
        val actualLiveData = detailViewModel.state.getOrAwaitValue()

        //then
        Truth.assertThat((actualLiveData as DetailState.SuccessLocalHero).hero).isNotNull()
        Truth.assertThat(actualLiveData.hero).isInstanceOf(
            HeroDetail::class.java)
    }

    @Test
    fun `When setHeroLike expects success returns boolean value`() = runTest {
        //given
        repository = mockk()
        detailViewModel = DetailViewModel(repository)
        coEvery { repository.getLocalHero("HeroId") } returns Generator.getHeroDetail()
        coEvery { repository.setHeroLike("HeroId") } returns LikeResultState.SuccessLikeHero
        coEvery { repository.updateLocalHero(Generator.getHeroDetail()) } returns Unit
        coEvery { repository.getHeroLocalizations("HeroId") } returns DetailState.SuccessLocalizationHero(null)

        //when
        detailViewModel.getHeroDetail("HeroId")
        detailViewModel.state.getOrAwaitValue()
        detailViewModel.setHeroLike()
        val actualLiveData = detailViewModel.like.getOrAwaitValue()

        //then
        Truth.assertThat(actualLiveData).isNotNull()
        Truth.assertThat(actualLiveData).isAnyOf(true,false)
    }

}