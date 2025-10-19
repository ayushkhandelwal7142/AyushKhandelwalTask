package com.example.ayushkhandelwaltask.data.remote.dto

import com.google.gson.annotations.SerializedName

data class HoldingsResponseDto(
    @SerializedName("data") val data: HoldingsDataDto,
)

data class HoldingsDataDto(
    @SerializedName("userHolding") val userHolding: List<HoldingDto>,
)

data class HoldingDto(
    @SerializedName("symbol") val symbol: String,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("ltp") val ltp: Double,
    @SerializedName("avgPrice") val averagePrice: Double,
    @SerializedName("close") val close: Double,
)


