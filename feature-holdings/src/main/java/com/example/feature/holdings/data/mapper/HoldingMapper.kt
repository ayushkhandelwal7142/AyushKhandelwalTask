package com.example.feature.holdings.data.mapper

import com.example.feature.holdings.data.remote.dto.HoldingDto
import com.example.feature.holdings.domain.model.Holding

fun HoldingDto.toDomain() = Holding(
    symbol = symbol,
    quantity = quantity,
    lastTradedPrice = ltp,
    averagePrice = averagePrice,
    closePrice = close,
)


