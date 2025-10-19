package com.example.feature.holdings.domain.usecase

import com.example.feature.holdings.domain.model.Holding
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ComputePortfolioSummaryTest {

    private lateinit var systemUnderTest: ComputePortfolioSummary

    @Before
    fun setUp() {
        systemUnderTest = ComputePortfolioSummary()
    }

    @Test
    fun `systemUnderTest with empty holdings returns zero values`() {
        val emptyHoldings = emptyList<Holding>()

        val result = systemUnderTest(emptyHoldings)

        assertEquals(0.0, result.currentValue, 0.01)
        assertEquals(0.0, result.totalInvestment, 0.01)
        assertEquals(0.0, result.todayPnl, 0.01)
        assertEquals(0.0, result.totalPnl, 0.01)
    }

    @Test
    fun `systemUnderTest with single holding calculates correctly`() {
        val holdings = listOf(
            Holding(
                symbol = "",
                quantity = 10,
                lastTradedPrice = 150.0,
                averagePrice = 140.0,
                closePrice = 150.0,
            )
        )

        val result = systemUnderTest(holdings)

        assertEquals(1500.0, result.currentValue, 0.01)
        assertEquals(1400.0, result.totalInvestment, 0.01)
        assertEquals(0.0, result.todayPnl, 0.01)
        assertEquals(100.0, result.totalPnl, 0.01)
    }

    @Test
    fun `systemUnderTest with multiple holdings calculates correctly`() {
        val holdings = listOf(
            Holding(
                symbol = "",
                quantity = 10,
                lastTradedPrice = 150.0,
                averagePrice = 140.0,
                closePrice = 150.0,
            ),
            Holding(
                symbol = "",
                quantity = 5,
                lastTradedPrice = 200.0,
                averagePrice = 220.0,
                closePrice = 200.0,
            )
        )

        val result = systemUnderTest(holdings)

        assertEquals(2500.0, result.currentValue, 0.01)
        assertEquals(2500.0, result.totalInvestment, 0.01)
        assertEquals(0.0, result.todayPnl, 0.01)
        assertEquals(0.0, result.totalPnl, 0.01)
    }

    @Test
    fun `systemUnderTest with negative P&L calculates correctly`() {
        val holdings = listOf(
            Holding(
                symbol = "LOSS",
                quantity = 100,
                lastTradedPrice = 50.0,
                averagePrice = 60.0,
                closePrice = 50.0,
            )
        )

        val result = systemUnderTest(holdings)

        assertEquals(5000.0, result.currentValue, 0.01)
        assertEquals(6000.0, result.totalInvestment, 0.01)
        assertEquals(0.0, result.todayPnl, 0.01)
        assertEquals(-1000.0, result.totalPnl, 0.01)
    }

    @Test
    fun `systemUnderTest with zero quantity handles correctly`() {
        val holdings = listOf(
            Holding(
                symbol = "ZERO",
                quantity = 0,
                lastTradedPrice = 100.0,
                averagePrice = 90.0,
                closePrice = 155.0,
            )
        )

        val result = systemUnderTest(holdings)

        assertEquals(0.0, result.currentValue, 0.01)
        assertEquals(0.0, result.totalInvestment, 0.01)
        assertEquals(0.0, result.todayPnl, 0.01)
        assertEquals(0.0, result.totalPnl, 0.01)
    }

    @Test
    fun `systemUnderTest with large numbers handles correctly`() {
        val holdings = listOf(
            Holding(
                symbol = "LARGE",
                quantity = 1000000,
                lastTradedPrice = 1000.0,
                averagePrice = 999.0,
                closePrice = 155.0,
            )
        )

        val result = systemUnderTest(holdings)

        assertEquals(1000000000.0, result.currentValue, 0.01)
        assertEquals(999000000.0, result.totalInvestment, 0.01)
        assertEquals(-8.45E8, result.todayPnl, 0.01)
        assertEquals(1000000.0, result.totalPnl, 0.01)
    }
}
