package com.example.feature.holdings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.feature.holdings.domain.repository.HoldingsRepository
import com.example.feature.holdings.domain.usecase.ComputePortfolioSummary
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PortfolioViewModelFactoryTest {

    private lateinit var repository: HoldingsRepository
    private lateinit var computePortfolioSummary: ComputePortfolioSummary
    private lateinit var systemUnderTest: PortfolioViewModelFactory

    @Before
    fun setUp() {
        repository = mockk()
        computePortfolioSummary = ComputePortfolioSummary()
        systemUnderTest = PortfolioViewModelFactory(repository, computePortfolioSummary)
    }

    @Test
    fun `create returns PortfolioViewModel for correct class`() {
        val viewModel = systemUnderTest.create(PortfolioViewModel::class.java)

        assertNotNull(viewModel)
        assertTrue(viewModel is PortfolioViewModel)
    }

    @Test
    fun `create throws exception for incorrect class`() {
        try {
            systemUnderTest.create(TestViewModel::class.java)
            assertTrue("Expected IllegalArgumentException", false)
        } catch (e: IllegalArgumentException) {
            assertEquals("Unknown ViewModel class", e.message)
        }
    }

    @Test
    fun `create returns same instance for multiple calls`() {
        val viewModel1 = systemUnderTest.create(PortfolioViewModel::class.java)
        val viewModel2 = systemUnderTest.create(PortfolioViewModel::class.java)

        assertNotNull(viewModel1)
        assertNotNull(viewModel2)
        assertTrue(viewModel1 is PortfolioViewModel)
        assertTrue(viewModel2 is PortfolioViewModel)
        assertEquals(viewModel1::class.java, viewModel2::class.java)
    }

    @Test
    fun `create handles abstract ViewModel class`() {
        try {
            systemUnderTest.create(AbstractViewModel::class.java)
            assertTrue("Expected IllegalArgumentException", false)
        } catch (e: IllegalArgumentException) {
            assertEquals("Unknown ViewModel class", e.message)
        }
    }

    private class TestViewModel : ViewModel()
    private abstract class AbstractViewModel : ViewModel()
}
