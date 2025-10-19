package com.example.ayushkhandelwaltask.data.repository

import com.example.ayushkhandelwaltask.data.mapper.toDomain
import com.example.ayushkhandelwaltask.data.remote.api.HoldingsApi
import com.example.ayushkhandelwaltask.domain.model.Holding
import com.example.ayushkhandelwaltask.domain.repository.HoldingsRepository

class HoldingsRepositoryImpl(
    private val api: HoldingsApi,
) : HoldingsRepository {
    override suspend fun fetchHoldings(): List<Holding> {
        val response = api.getHoldings()
        return response.data.userHolding.map { it.toDomain() }
    }
}


