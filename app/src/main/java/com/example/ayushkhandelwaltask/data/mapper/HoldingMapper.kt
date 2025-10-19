package com.example.ayushkhandelwaltask.data.mapper

import com.example.ayushkhandelwaltask.data.remote.dto.HoldingDto
import com.example.ayushkhandelwaltask.domain.model.Holding

fun HoldingDto.toDomain() = Holding(
    symbol = symbol,
    quantity = quantity,
    lastTradedPrice = ltp,
    averagePrice = averagePrice,
    closePrice = close,
)


