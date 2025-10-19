package com.example.ayushkhandelwaltask.domain.repository

import com.example.ayushkhandelwaltask.domain.model.Holding

interface HoldingsRepository {
    suspend fun fetchHoldings(): List<Holding>
}


