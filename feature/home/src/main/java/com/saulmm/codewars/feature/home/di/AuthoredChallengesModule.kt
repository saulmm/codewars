package com.saulmm.codewars.feature.home.di

import com.saulmm.codewars.feature.home.model.AuthoredChallengesRepository
import com.saulmm.codewars.feature.home.model.RemoteAuthoredChallengesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@InternalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object AuthoredChallengesModule {

    @Provides
    @Singleton
    fun provideAuthoredChallengesRepository(
        remote: RemoteAuthoredChallengesDataSource
    ): AuthoredChallengesRepository {
        return AuthoredChallengesRepository(remote)
    }
}