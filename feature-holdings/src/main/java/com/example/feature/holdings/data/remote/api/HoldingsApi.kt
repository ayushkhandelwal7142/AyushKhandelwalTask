package com.example.feature.holdings.data.remote.api

import com.example.feature.holdings.data.remote.dto.HoldingsResponseDto
import retrofit2.http.GET

interface HoldingsApi {
    @GET("/")
    suspend fun getHoldings(): HoldingsResponseDto
}


