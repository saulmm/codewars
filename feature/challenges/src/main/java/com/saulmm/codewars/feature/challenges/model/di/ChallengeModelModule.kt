package com.saulmm.codewars.feature.challenges.model.di

import com.saulmm.codewars.entity.Challenge
import com.saulmm.codewars.feature.challenges.model.ChallengePreviewParams
import com.saulmm.codewars.feature.challenges.model.local.ChallengesPreviewRoomDataSource
import com.saulmm.codewars.feature.challenges.model.remote.datasource.ChallengesPreviewApiDataSource
import com.saulmm.codewars.repository.CachingRepository
import com.saulmm.codewars.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ChallengeModelModule {

    @Provides
    @Singleton
    fun provideChallengePreviewRepository(
        remote: ChallengesPreviewApiDataSource,
        local: ChallengesPreviewRoomDataSource,
    ): Repository<ChallengePreviewParams, List<Challenge>> {
        return CachingRepository(
            remote = remote,
            local = local
        )
    }
}

