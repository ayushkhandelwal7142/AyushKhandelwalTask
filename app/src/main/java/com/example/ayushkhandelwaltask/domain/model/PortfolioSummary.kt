package com.example.ayushkhandelwaltask.domain.model

data class PortfolioSummary(
    val currentValue: Double,
    val totalInvestment: Double,
    val totalPnl: Double,
    val todayPnl: Double
)