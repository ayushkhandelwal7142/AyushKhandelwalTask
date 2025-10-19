package com.example.feature.holdings.data.repository

import com.example.feature.holdings.data.mapper.toDomain
import com.example.feature.holdings.data.remote.api.HoldingsApi
import com.example.feature.holdings.domain.model.Holding
import com.example.feature.holdings.domain.repository.HoldingsRepository

class HoldingsRepositoryImpl(
    private val api: HoldingsApi,
) : HoldingsRepository {
    override suspend fun fetchHoldings(): List<Holding> {
        val response = api.getHoldings()
        return response.data.userHolding.map { it.toDomain() }
    }
}


