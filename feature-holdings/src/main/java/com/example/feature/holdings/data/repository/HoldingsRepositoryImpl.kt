package com.example.feature.holdings.data.repository

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.example.coreui.commonUtils.NetworkUtils
import com.example.feature.holdings.data.mapper.toDomain
import com.example.feature.holdings.data.remote.api.HoldingsApi
import com.example.feature.holdings.domain.exception.NetworkException
import com.example.feature.holdings.domain.exception.NoInternetException
import com.example.feature.holdings.domain.model.Holding
import com.example.feature.holdings.domain.repository.HoldingsRepository
import java.io.IOException

class HoldingsRepositoryImpl(
    private val api: HoldingsApi,
    private val context: Context,
) : HoldingsRepository {

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun fetchHoldings(): List<Holding> {
        if (NetworkUtils.isInternetAvailable(context).not()) {
            throw NoInternetException()
        }

        return try {
            val response = api.getHoldings()
            response.data.userHolding.map { it.toDomain() }
        } catch (e: IOException) {
            if (NetworkUtils.isInternetAvailable(context).not()) {
                throw NoInternetException()
            } else {
                throw NetworkException("Network error: ${e.message}")
            }
        } catch (e: Exception) {
            throw NetworkException("Failed to fetch holdings: ${e.message}")
        }
    }
}


