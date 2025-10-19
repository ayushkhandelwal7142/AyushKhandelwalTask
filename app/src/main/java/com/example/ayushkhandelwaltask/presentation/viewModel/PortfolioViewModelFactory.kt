package com.example.ayushkhandelwaltask.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ayushkhandelwaltask.domain.repository.HoldingsRepository
import com.example.ayushkhandelwaltask.domain.usecase.ComputePortfolioSummary

class PortfolioViewModelFactory(
    private val repository: HoldingsRepository,
    private val compute: ComputePortfolioSummary
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PortfolioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PortfolioViewModel(
                repository = repository,
                computePortfolioSummary = compute,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}