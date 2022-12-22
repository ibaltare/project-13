package com.keepcoding.navi.dragonballapp.data

import com.google.common.truth.Truth
import com.keepcoding.navi.dragonballapp.data.local.AuthToken
import com.keepcoding.navi.dragonballapp.fakes.FakeRemoteDataSource
import com.keepcoding.navi.dragonballapp.ui.login.LoginState
import com.keepcoding.navi.dragonballapp.utils.Generator
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginRepositoryImpTest {

    //UUT / SUT
    private lateinit var loginRepositoryImp: LoginRepositoryImp

    @Test
    fun `When doLogin expects succes return token`() = runTest{
        //given
        val authToken: AuthToken = mockk()
        val remoteDataSource = FakeRemoteDataSource(true)
        loginRepositoryImp = LoginRepositoryImp(remoteDataSource,authToken)

        //when
        val actual = loginRepositoryImp.doLogin("user", "pass")

        //then
        Truth.assertThat(actual).isEqualTo(LoginState.Success("Token"))
    }

    @Test
    fun `When doLogin fail return error`() = runTest{
        //given
        val authToken: AuthToken = mockk()
        val remoteDataSource = FakeRemoteDataSource(false)
        loginRepositoryImp = LoginRepositoryImp(remoteDataSource,authToken)

        //when
        val actual = loginRepositoryImp.doLogin("user", "pass")

        //then
        Truth.assertThat(actual).isEqualTo(LoginState.Failure("Login Fail"))
    }

    @Test
    fun `When authTokenExist then success return true`() = runTest{
        //given
        val authToken: AuthToken = mockk()
        val remoteDataSource = FakeRemoteDataSource(true)
        loginRepositoryImp = LoginRepositoryImp(remoteDataSource,authToken)
        every { authToken.getToken() } returns Generator.getFakeToken()
        //when
        val actual = loginRepositoryImp.authTokenExist()

        //then
        Truth.assertThat(actual).isEqualTo(true)
    }

}