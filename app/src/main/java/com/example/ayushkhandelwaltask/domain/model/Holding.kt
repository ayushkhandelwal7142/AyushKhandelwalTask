package com.example.ayushkhandelwaltask.domain.model

data class Holding(
    val symbol: String,
    val quantity: Int,
    val lastTradedPrice: Double,
    val averagePrice: Double,
    val closePrice: Double,
)


