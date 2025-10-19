package com.example.ayushkhandelwaltask

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.ayushkhandelwaltask.domain.model.Holding
import com.example.ayushkhandelwaltask.domain.repository.HoldingsRepository
import com.example.ayushkhandelwaltask.domain.usecase.ComputePortfolioSummary
import com.example.ayushkhandelwaltask.presentation.viewModel.PortfolioViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PortfolioViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Test
    fun `calculates summary correctly`() = scope.runTest {
        val repo: HoldingsRepository = mockk()
        val holdings = listOf(
            Holding("ABC", 2, 100.0, 80.0, 95.0),
            Holding("XYZ", 1, 50.0, 60.0, 55.0)
        )
        coEvery { repo.fetchHoldings() } returns holdings

        val vm = PortfolioViewModel(repo, ComputePortfolioSummary())

        val state = vm.state.first { !it.isLoading }
        val summary = state.summary!!
        assertEquals(250.0, summary.currentValue, 0.01)
        assertEquals(220.0, summary.totalInvestment, 0.01)
        assertEquals(30.0, summary.totalPnl, 0.01)
        assertEquals((95.0 - 100.0) * 2 + (55.0 - 50.0) * 1, summary.todayPnl, 0.01)
    }
}


