package com.saulmm.codewars.di

import com.saulmm.codewars.BuildConfig
import com.saulmm.codewars.entity.build.AppVariant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppVariantModule {

    @Provides
    fun appVariantProvider(): AppVariant {
        return when (BuildConfig.DEBUG) {
            true -> AppVariant.DEBUG
            else -> AppVariant.RELEASE
        }
    }
}