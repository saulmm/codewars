package com.saulmm.codewars.feature.challenges.di

import com.saulmm.codewars.feature.challenges.model.ChallengesRepository
import com.saulmm.codewars.feature.challenges.model.RemoteChallengesDatasource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@InternalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object ChallengesModule {

    @Provides
    @Singleton
    fun provideAuthoredChallengesRepository(
        remote: RemoteChallengesDatasource
    ): ChallengesRepository {
        return ChallengesRepository(remote)
    }
}