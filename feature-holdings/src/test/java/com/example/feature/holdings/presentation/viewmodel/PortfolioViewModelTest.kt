package com.example.feature.holdings.presentation.viewmodel

import com.example.feature.holdings.domain.exception.NetworkException
import com.example.feature.holdings.domain.exception.NoInternetException
import com.example.feature.holdings.domain.model.Holding
import com.example.feature.holdings.domain.repository.HoldingsRepository
import com.example.feature.holdings.domain.usecase.ComputePortfolioSummary
import com.example.feature.holdings.presentation.model.PortfolioUiRenderedState
import com.example.feature.holdings.presentation.model.PortfolioUiRenderedState.SUCCESS
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PortfolioViewModelTest {

    private lateinit var repository: HoldingsRepository
    private lateinit var computePortfolioSummary: ComputePortfolioSummary
    private lateinit var systemUnderTest: PortfolioViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        computePortfolioSummary = ComputePortfolioSummary()
        systemUnderTest = PortfolioViewModel(repository, computePortfolioSummary)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `refresh updates state to success with data`() = runTest {
        val mockHoldings = listOf(
            Holding(
                symbol = "",
                quantity = 10,
                lastTradedPrice = 150.0,
                averagePrice = 140.0,
                closePrice = 155.0,
            )
        )
        coEvery { repository.fetchHoldings() } returns mockHoldings

        systemUnderTest.refresh()
        advanceUntilIdle()

        val state = systemUnderTest.state.value
        assertTrue(state.uiRenderedState is SUCCESS)
        assertEquals(1, state.holdings.size)
        assertEquals("", state.holdings[0].symbol)
        assertNotNull(state.summary)
        assertEquals(1500.0, state.summary!!.currentValue, 0.01)
        assertEquals(1400.0, state.summary.totalInvestment, 0.01)
        assertEquals(100.0, state.summary.totalPnl, 0.01)
    }

    @Test
    fun `refresh updates state to error when repository throws exception`() = runTest {
        val errorMessage = "Network error"
        coEvery { repository.fetchHoldings() } throws RuntimeException(errorMessage)

        systemUnderTest.refresh()
        advanceUntilIdle()

        val state = systemUnderTest.state.value
        assertTrue(state.uiRenderedState is PortfolioUiRenderedState.ERROR)
        val errorState = state.uiRenderedState as PortfolioUiRenderedState.ERROR
        assertEquals(errorMessage, errorState.message)
        assertTrue(state.holdings.isEmpty())
        assertTrue(state.summary == null)
    }

    @Test
    fun `refresh updates state to error with unknown error message`() = runTest {
        coEvery { repository.fetchHoldings() } throws RuntimeException()

        systemUnderTest.refresh()
        advanceUntilIdle()

        val state = systemUnderTest.state.value
        assertTrue(state.uiRenderedState is PortfolioUiRenderedState.ERROR)
        val errorState = state.uiRenderedState as PortfolioUiRenderedState.ERROR
        assertEquals("Unknown error", errorState.message)
    }

    @Test
    fun `toggleExpanded toggles expansion state`() = runTest {
        val mockHoldings = listOf(
            Holding(
                symbol = "",
                quantity = 10,
                lastTradedPrice = 150.0,
                averagePrice = 140.0,
                closePrice = 155.0,
            )
        )
        coEvery { repository.fetchHoldings() } returns mockHoldings

        systemUnderTest.refresh()
        advanceUntilIdle()

        val initialState = systemUnderTest.state.value
        assertTrue(!initialState.isExpanded)

        systemUnderTest.toggleExpanded()
        advanceUntilIdle()

        val expandedState = systemUnderTest.state.value
        assertTrue(expandedState.isExpanded)

        systemUnderTest.toggleExpanded()
        advanceUntilIdle()

        val collapsedState = systemUnderTest.state.value
        assertTrue(!collapsedState.isExpanded)
    }

    @Test
    fun `refresh with empty holdings shows success with empty data`() = runTest {
        coEvery { repository.fetchHoldings() } returns emptyList()

        systemUnderTest.refresh()
        advanceUntilIdle()

        val state = systemUnderTest.state.value
        assertTrue(state.uiRenderedState is SUCCESS)
        assertTrue(state.holdings.isEmpty())
        assertNotNull(state.summary)
        assertEquals(0.0, state.summary!!.currentValue, 0.01)
        assertEquals(0.0, state.summary.totalInvestment, 0.01)
        assertEquals(0.0, state.summary.totalPnl, 0.01)
    }

    @Test
    fun `refresh with multiple holdings calculates summary correctly`() = runTest {
        val mockHoldings = listOf(
            Holding(
                symbol = "",
                quantity = 10,
                lastTradedPrice = 150.0,
                averagePrice = 140.0,
                closePrice = 155.0,
            ),
            Holding(
                symbol = "GOOGLE",
                quantity = 5,
                lastTradedPrice = 200.0,
                averagePrice = 220.0,
                closePrice = 155.0,
            )
        )
        coEvery { repository.fetchHoldings() } returns mockHoldings

        systemUnderTest.refresh()
        advanceUntilIdle()

        val state = systemUnderTest.state.value
        assertTrue(state.uiRenderedState is SUCCESS)
        assertEquals(2, state.holdings.size)

        val summary = state.summary!!
        assertEquals(2500.0, summary.currentValue, 0.01)
        assertEquals(2500.0, summary.totalInvestment, 0.01)
        assertEquals(0.0, summary.totalPnl, 0.01)
    }

    @Test
    fun `refresh updates state to NO_INTERNET when NoInternetException is thrown`() = runTest {
        coEvery { repository.fetchHoldings() } throws NoInternetException()

        systemUnderTest.refresh()
        advanceUntilIdle()

        val state = systemUnderTest.state.value
        assertTrue(state.uiRenderedState is PortfolioUiRenderedState.NO_INTERNET)
        assertTrue(state.holdings.isEmpty())
        assertTrue(state.summary == null)
    }

    @Test
    fun `refresh updates state to ERROR when NetworkException is thrown`() = runTest {
        val errorMessage = "Network error occurred"
        coEvery { repository.fetchHoldings() } throws NetworkException(errorMessage)

        systemUnderTest.refresh()
        advanceUntilIdle()

        val state = systemUnderTest.state.value
        assertTrue(state.uiRenderedState is PortfolioUiRenderedState.ERROR)
        val errorState = state.uiRenderedState as PortfolioUiRenderedState.ERROR
        assertEquals(errorMessage, errorState.message)
        assertTrue(state.holdings.isEmpty())
        assertTrue(state.summary == null)
    }
}
