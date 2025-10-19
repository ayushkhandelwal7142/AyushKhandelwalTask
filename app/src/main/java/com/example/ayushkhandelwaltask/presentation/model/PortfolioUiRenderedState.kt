package com.example.ayushkhandelwaltask.presentation.model

sealed class PortfolioUiRenderedState {
    data object LOADING : PortfolioUiRenderedState()
    data object SUCCESS : PortfolioUiRenderedState()
    data class ERROR(val message: String) : PortfolioUiRenderedState()
}