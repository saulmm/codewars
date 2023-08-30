package com.saulmm.codewars.feature.challenges.model.di

import android.content.Context
import androidx.room.Room
import com.saulmm.codewars.feature.challenges.model.local.ChallengeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChallengeDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): ChallengeDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = ChallengeDatabase::class.java,
            name = ChallengeDatabase.NAME
        ).build()
    }
}