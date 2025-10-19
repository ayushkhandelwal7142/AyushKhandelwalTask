package com.example.ayushkhandelwaltask.di

import com.example.ayushkhandelwaltask.data.remote.api.HoldingsApi
import com.example.ayushkhandelwaltask.data.repository.HoldingsRepositoryImpl
import com.example.ayushkhandelwaltask.domain.repository.HoldingsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideHoldingsRepository(api: HoldingsApi): HoldingsRepository =
        HoldingsRepositoryImpl(api)
}


