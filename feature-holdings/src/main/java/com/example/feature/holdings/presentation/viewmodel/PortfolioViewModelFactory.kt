package com.example.feature.holdings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feature.holdings.domain.repository.HoldingsRepository
import com.example.feature.holdings.domain.usecase.ComputePortfolioSummary

class PortfolioViewModelFactory(
    private val repository: HoldingsRepository,
    private val computePortfolioSummary: ComputePortfolioSummary,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PortfolioViewModel::class.java)) {
            return PortfolioViewModel(repository, computePortfolioSummary) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
