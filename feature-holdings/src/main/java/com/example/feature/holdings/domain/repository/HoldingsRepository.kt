package com.example.feature.holdings.domain.repository

import com.example.feature.holdings.domain.model.Holding

interface HoldingsRepository {
    suspend fun fetchHoldings(): List<Holding>
}


