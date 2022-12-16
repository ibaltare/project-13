package com.keepcoding.navi.dragonballapp.dependencyinjection

import com.keepcoding.navi.dragonballapp.data.local.AuthToken
import com.keepcoding.navi.dragonballapp.data.remote.DragonBallAPI
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor,authToken: AuthToken): OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                var token = authToken.getToken()
                val newRequest = chain.request().newBuilder().apply {
                    header("Content-Type", "Application/Json")
                    if (token != null && !token.isBlank()) header("Authorization", "Bearer $token")
                }.build()
                chain.proceed(newRequest)

                /*val originalRequest = chain.request()
                val newRequest = originalRequest.newBuilder()
//                .header("Authorization", "Bearer $TOKEN")
                    .header("Content-Type", "Application/Json")
                    .build()

                chain.proceed(newRequest)*/
            }
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dragonball.keepcoding.education/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .build()
    }

    @Provides
    fun provideAPI(retrofit: Retrofit): DragonBallAPI {
        return retrofit.create(DragonBallAPI::class.java)
    }
}