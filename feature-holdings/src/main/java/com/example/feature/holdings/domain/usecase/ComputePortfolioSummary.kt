package com.example.feature.holdings.domain.usecase

import com.example.feature.holdings.domain.model.Holding
import com.example.feature.holdings.domain.model.PortfolioSummary
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.abs

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

    fun formatCurrency(
        amount: Double,
        currencySymbol: String = "₹",
    ): String {
        val locale = Locale("en", "IN")

        val formatter = NumberFormat.getNumberInstance(locale).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }

        val formattedAmount = formatter.format(abs(amount))

        return if (amount >= 0) {
            "$currencySymbol$formattedAmount"
        } else {
            "–$currencySymbol$formattedAmount"
        }
    }
}


