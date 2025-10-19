package com.example.feature.holdings.di

import com.example.feature.holdings.data.remote.api.HoldingsApi
import com.example.feature.holdings.data.repository.HoldingsRepositoryImpl
import com.example.feature.holdings.domain.repository.HoldingsRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class HoldingsModule {
    @Provides
    @Singleton
    fun provideHoldingsApi(retrofit: Retrofit): HoldingsApi =
        retrofit.create(HoldingsApi::class.java)

    @Provides
    @Singleton
    fun provideHoldingsRepository(api: HoldingsApi): HoldingsRepository =
        HoldingsRepositoryImpl(api)
}


