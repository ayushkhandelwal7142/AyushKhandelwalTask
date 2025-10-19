package com.example.ayushkhandelwaltask.domain.usecase

import com.example.ayushkhandelwaltask.domain.model.Holding
import com.example.ayushkhandelwaltask.domain.model.PortfolioSummary

class ComputePortfolioSummary {
    operator fun invoke(holdings: List<Holding>): PortfolioSummary {
        var currentValue = 0.0
        var totalInvestment = 0.0
        var todayPnl = 0.0
        for (item in holdings) {
            currentValue += item.lastTradedPrice * item.quantity
            totalInvestment += item.averagePrice * item.quantity
            todayPnl += (item.closePrice - item.lastTradedPrice) * item.quantity
        }

        val totalPnl = currentValue - totalInvestment

        return PortfolioSummary(
            currentValue = currentValue,
            totalInvestment = totalInvestment,
            totalPnl = totalPnl,
            todayPnl = todayPnl,
        )
    }
}


