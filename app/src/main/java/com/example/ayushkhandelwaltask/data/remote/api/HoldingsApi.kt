package com.example.ayushkhandelwaltask.data.remote.api

import com.example.ayushkhandelwaltask.data.remote.dto.HoldingsResponseDto
import retrofit2.http.GET

interface HoldingsApi {
    @GET("/")
    suspend fun getHoldings(): HoldingsResponseDto
}