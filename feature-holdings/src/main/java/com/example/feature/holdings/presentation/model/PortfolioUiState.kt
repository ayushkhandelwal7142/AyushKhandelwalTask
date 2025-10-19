package com.example.feature.holdings.presentation.model

import com.example.feature.holdings.domain.model.Holding
import com.example.feature.holdings.domain.model.PortfolioSummary

data class PortfolioUiState(
    val holdings: List<Holding> = emptyList(),
    val summary: PortfolioSummary? = null,
    val isExpanded: Boolean = false,
    val uiRenderedState: PortfolioUiRenderedState = PortfolioUiRenderedState.LOADING,
)

sealed class PortfolioUiRenderedState {
    object LOADING : PortfolioUiRenderedState()
    data class ERROR(val message: String) : PortfolioUiRenderedState()
    object NO_INTERNET : PortfolioUiRenderedState()
    object SUCCESS : PortfolioUiRenderedState()
}
