package com.saulmm.codewars.common.network.di

import com.saulmm.codewars.entity.build.AppVariant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val READ_TIMEOUT_LIMIT_MINS = 2L
    private const val CONNECT_TIMEOUT_LIMIT_MINS = 3L

    @Provides
    fun provideOkHttpLoggingInterceptor(
        appVariant: AppVariant
    ): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = when (appVariant) {
                AppVariant.RELEASE -> HttpLoggingInterceptor.Level.BODY
                AppVariant.DEBUG -> HttpLoggingInterceptor.Level.BASIC
                else -> throw IllegalStateException("Unknown build type $appVariant")
            }
        }
    }

    @Provides
    fun provideBaseOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(CONNECT_TIMEOUT_LIMIT_MINS, TimeUnit.MINUTES)
            .connectTimeout(READ_TIMEOUT_LIMIT_MINS, TimeUnit.MINUTES)
    }
}