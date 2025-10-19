package com.example.feature.holdings.data.mapper

import com.example.feature.holdings.data.remote.dto.HoldingDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class HoldingMapperTest {

    @Test
    fun `toDomain maps HoldingDto to Holding correctly`() {
        val dto = HoldingDto(
            symbol = "",
            quantity = 10,
            ltp = 150.0,
            averagePrice = 140.0,
            close = 145.0
        )

        val result = dto.toDomain()

        assertNotNull(result)
        assertEquals("", result.symbol)
        assertEquals(10, result.quantity)
        assertEquals(150.0, result.lastTradedPrice, 0.01)
        assertEquals(140.0, result.averagePrice, 0.01)
    }

    @Test
    fun `toDomain handles zero values correctly`() {
        val dto = HoldingDto(
            symbol = "ZERO",
            quantity = 0,
            ltp = 0.0,
            averagePrice = 0.0,
            close = 0.0
        )

        val result = dto.toDomain()

        assertNotNull(result)
        assertEquals("ZERO", result.symbol)
        assertEquals(0, result.quantity)
        assertEquals(0.0, result.lastTradedPrice, 0.01)
        assertEquals(0.0, result.averagePrice, 0.01)
    }

    @Test
    fun `toDomain handles negative values correctly`() {
        val dto = HoldingDto(
            symbol = "NEGATIVE",
            quantity = -5,
            ltp = -100.0,
            averagePrice = -90.0,
            close = -95.0
        )

        val result = dto.toDomain()

        assertNotNull(result)
        assertEquals("NEGATIVE", result.symbol)
        assertEquals(-5, result.quantity)
        assertEquals(-100.0, result.lastTradedPrice, 0.01)
        assertEquals(-90.0, result.averagePrice, 0.01)
    }

    @Test
    fun `toDomain handles large values correctly`() {
        val dto = HoldingDto(
            symbol = "LARGE",
            quantity = 1000000,
            ltp = 999999.99,
            averagePrice = 888888.88,
            close = 777777.77
        )

        val result = dto.toDomain()

        assertNotNull(result)
        assertEquals("LARGE", result.symbol)
        assertEquals(1000000, result.quantity)
        assertEquals(999999.99, result.lastTradedPrice, 0.01)
        assertEquals(888888.88, result.averagePrice, 0.01)
    }

    @Test
    fun `toDomain handles decimal precision correctly`() {
        val dto = HoldingDto(
            symbol = "DECIMAL",
            quantity = 1,
            ltp = 123.456789,
            averagePrice = 987.654321,
            close = 555.123456
        )

        val result = dto.toDomain()

        assertNotNull(result)
        assertEquals("DECIMAL", result.symbol)
        assertEquals(1, result.quantity)
        assertEquals(123.456789, result.lastTradedPrice, 0.000001)
        assertEquals(987.654321, result.averagePrice, 0.000001)
    }

    @Test
    fun `toDomain handles empty symbol correctly`() {
        val dto = HoldingDto(
            symbol = "",
            quantity = 10,
            ltp = 100.0,
            averagePrice = 90.0,
            close = 95.0
        )

        val result = dto.toDomain()

        assertNotNull(result)
        assertEquals("", result.symbol)
        assertEquals(10, result.quantity)
        assertEquals(100.0, result.lastTradedPrice, 0.01)
        assertEquals(90.0, result.averagePrice, 0.01)
    }

    @Test
    fun `toDomain handles special characters in symbol correctly`() {
        val dto = HoldingDto(
            symbol = "STOCK-123_ABC",
            quantity = 5,
            ltp = 50.0,
            averagePrice = 45.0,
            close = 48.0
        )

        val result = dto.toDomain()

        assertNotNull(result)
        assertEquals("STOCK-123_ABC", result.symbol)
        assertEquals(5, result.quantity)
        assertEquals(50.0, result.lastTradedPrice, 0.01)
        assertEquals(45.0, result.averagePrice, 0.01)
    }
}
