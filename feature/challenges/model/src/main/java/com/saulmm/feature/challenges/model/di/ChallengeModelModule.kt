package com.saulmm.feature.challenges.model.di

import com.saulmm.codewars.entity.Challenge
import com.saulmm.codewars.entity.ChallengeDetail
import com.saulmm.feature.challenges.model.local.datasources.ChallengeDetailRoomDataSource
import com.saulmm.feature.challenges.model.params.ChallengePreviewParams
import com.saulmm.feature.challenges.model.local.datasources.ChallengesPreviewRoomDataSource
import com.saulmm.feature.challenges.model.params.ChallengeDetailParams
import com.saulmm.feature.challenges.model.remote.datasource.ChallengeDetailApiDataSource
import com.saulmm.feature.challenges.model.remote.datasource.ChallengesPreviewApiDataSource
import com.saulmm.codewars.repository.CachingRepository
import com.saulmm.codewars.repository.LocalSearchRepository
import com.saulmm.codewars.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ChallengeModelModule {

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

    @Provides
    @Singleton
    fun provideSearchRepository(
        local: ChallengesPreviewRoomDataSource,
    ): LocalSearchRepository<ChallengePreviewParams, List<Challenge>> {
        return LocalSearchRepository<ChallengePreviewParams, List<Challenge>>(
            local = local
        )
    }

    @Provides
    @Singleton
    fun provideChallengeDetailRepository(
        remote: ChallengeDetailApiDataSource,
        local: ChallengeDetailRoomDataSource,
    ): Repository<ChallengeDetailParams, ChallengeDetail> {
        return CachingRepository(
            remote = remote,
            local = local
        )
    }

}

