package com.example.feature.holdings.data.repository

import android.content.Context
import com.example.coreui.commonUtils.NetworkUtils
import com.example.feature.holdings.data.remote.api.HoldingsApi
import com.example.feature.holdings.data.remote.dto.HoldingDto
import com.example.feature.holdings.data.remote.dto.HoldingsDataDto
import com.example.feature.holdings.data.remote.dto.HoldingsResponseDto
import com.example.feature.holdings.domain.exception.NetworkException
import com.example.feature.holdings.domain.exception.NoInternetException
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

class HoldingsRepositoryImplTest {

    private lateinit var api: HoldingsApi
    private lateinit var context: Context
    private lateinit var systemUnderTest: HoldingsRepositoryImpl

    @Before
    fun setUp() {
        api = mockk()
        context = mockk()
        systemUnderTest = HoldingsRepositoryImpl(api, context)

        mockkObject(NetworkUtils)
        every { NetworkUtils.isInternetAvailable(context) } returns true
    }

    @Test
    fun `fetchHoldings returns successful response`() = runTest {
        val mockResponse = HoldingsResponseDto(
            data = HoldingsDataDto(
                userHolding = listOf(
                    HoldingDto(
                        symbol = "",
                        quantity = 10,
                        ltp = 150.0,
                        averagePrice = 140.0,
                        close = 150.0
                    ),
                    HoldingDto(
                        symbol = "GOOGLE",
                        quantity = 5,
                        ltp = 200.0,
                        averagePrice = 220.0,
                        close = 200.0
                    )
                )
            )
        )
        coEvery { api.getHoldings() } returns mockResponse

        val result = systemUnderTest.fetchHoldings()

        assertNotNull(result)
        assertEquals(2, result.size)

        val firstHolding = result[0]
        assertEquals("", firstHolding.symbol)
        assertEquals(10, firstHolding.quantity)
        assertEquals(150.0, firstHolding.lastTradedPrice, 0.01)
        assertEquals(140.0, firstHolding.averagePrice, 0.01)

        val secondHolding = result[1]
        assertEquals("GOOGLE", secondHolding.symbol)
        assertEquals(5, secondHolding.quantity)
        assertEquals(200.0, secondHolding.lastTradedPrice, 0.01)
        assertEquals(220.0, secondHolding.averagePrice, 0.01)
    }

    @Test
    fun `fetchHoldings with empty response returns empty list`() = runTest {
        val mockResponse = HoldingsResponseDto(
            data = HoldingsDataDto(
                userHolding = emptyList()
            )
        )
        coEvery { api.getHoldings() } returns mockResponse

        val result = systemUnderTest.fetchHoldings()

        assertNotNull(result)
        assertTrue(result.isEmpty())
    }

    @Test
    fun `fetchHoldings with null holdings returns empty list`() = runTest {
        val mockResponse = HoldingsResponseDto(
            data = HoldingsDataDto(
                userHolding = listOf()
            )
        )
        coEvery { api.getHoldings() } returns mockResponse

        val result = systemUnderTest.fetchHoldings()

        assertNotNull(result)
        assertTrue(result.isEmpty())
    }

    @Test
    fun `fetchHoldings propagates API exceptions`() = runTest {
        val exception = RuntimeException("Network error")
        coEvery { api.getHoldings() } throws exception

        try {
            systemUnderTest.fetchHoldings()
            assertTrue("Expected exception to be thrown", false)
        } catch (e: RuntimeException) {
            assertEquals("Network error", e.message)
        }
    }

    @Test
    fun `fetchHoldings handles malformed data gracefully`() = runTest {
        val mockResponse = HoldingsResponseDto(
            data = HoldingsDataDto(
                userHolding = listOf(
                    HoldingDto(
                        symbol = "MALFORMED",
                        quantity = -1, // Invalid quantity
                        ltp = -100.0, // Invalid price
                        averagePrice = 0.0, // Zero price
                        close = 0.0 // Null close price
                    )
                )
            )
        )
        coEvery { api.getHoldings() } returns mockResponse

        val result = systemUnderTest.fetchHoldings()

        assertNotNull(result)
        assertEquals(1, result.size)

        val holding = result[0]
        assertEquals("MALFORMED", holding.symbol)
        assertEquals(-1, holding.quantity) // Repository doesn't validate, just maps
        assertEquals(-100.0, holding.lastTradedPrice, 0.01)
        assertEquals(0.0, holding.averagePrice, 0.01)
    }

    @Test
    fun `fetchHoldings handles large dataset`() = runTest {
        val largeHoldingsList = (1..1000).map { index ->
            HoldingDto(
                symbol = "STOCK$index",
                quantity = index,
                ltp = index * 10.0,
                averagePrice = index * 9.5,
                close = index * 10.0
            )
        }
        val mockResponse = HoldingsResponseDto(
            data = HoldingsDataDto(userHolding = largeHoldingsList)
        )
        coEvery { api.getHoldings() } returns mockResponse

        val result = systemUnderTest.fetchHoldings()

        assertNotNull(result)
        assertEquals(1000, result.size)

        val firstHolding = result[0]
        assertEquals("STOCK1", firstHolding.symbol)
        assertEquals(1, firstHolding.quantity)
        assertEquals(10.0, firstHolding.lastTradedPrice, 0.01)

        val lastHolding = result[999]
        assertEquals("STOCK1000", lastHolding.symbol)
        assertEquals(1000, lastHolding.quantity)
        assertEquals(10000.0, lastHolding.lastTradedPrice, 0.01)
    }

    @Test
    fun `fetchHoldings throws NoInternetException when no internet`() = runTest {
        every { NetworkUtils.isInternetAvailable(context) } returns false

        try {
            systemUnderTest.fetchHoldings()
            assertTrue("Expected NoInternetException", false)
        } catch (e: NoInternetException) {
            // Expected exception
        }
    }

    @Test
    fun `fetchHoldings throws NetworkException on IOException`() = runTest {
        every { NetworkUtils.isInternetAvailable(context) } returns true
        coEvery { api.getHoldings() } throws IOException("Network error")

        try {
            systemUnderTest.fetchHoldings()
            assertTrue("Expected NetworkException", false)
        } catch (e: NetworkException) {
            assertTrue(e.message?.contains("Network error") == true)
        }
    }

    @Test
    fun `fetchHoldings throws NoInternetException on IOException when no internet`() = runTest {
        every { NetworkUtils.isInternetAvailable(context) } returns false
        coEvery { api.getHoldings() } throws IOException("Network error")

        try {
            systemUnderTest.fetchHoldings()
            assertTrue("Expected NoInternetException", false)
        } catch (e: NoInternetException) {
            // Expected exception
        }
    }
}
