package com.saulmm.feature.challenges.model.remote.di

import com.saulmm.codewars.entity.build.AppVariant
import com.saulmm.codewars.entity.build.AppVariant.*
import com.saulmm.feature.challenges.model.remote.api.CodewarsApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CodewarsApiModule {

    @Provides
    @Named("endpoint")
    fun provideEndpoint(): String {
        return "https://www.codewars.com"
    }

    @Provides
    @Singleton
    fun provideCodewarsApiService(
        @Named("endpoint") endPoint: String,
        clientBuilder: OkHttpClient.Builder,
        moshi: Moshi,
        appVariant: AppVariant
    ): CodewarsApi {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(clientBuilder.build())
            .apply {
                validateEagerly(
                    when (appVariant) {
                        DEBUG -> true
                        RELEASE -> false
                    }
                )
            }
            .baseUrl(endPoint)
            .build()
            .create(CodewarsApi::class.java)
    }
}