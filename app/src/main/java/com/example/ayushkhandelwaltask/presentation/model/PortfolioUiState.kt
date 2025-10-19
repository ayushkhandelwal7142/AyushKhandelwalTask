package com.example.ayushkhandelwaltask.presentation.model

import com.example.ayushkhandelwaltask.domain.model.Holding
import com.example.ayushkhandelwaltask.domain.model.PortfolioSummary

data class PortfolioUiState(
    val uiRenderedState: PortfolioUiRenderedState,
    val holdings: List<Holding> = emptyList(),
    val summary: PortfolioSummary? = null,
    val isExpanded: Boolean = false
)