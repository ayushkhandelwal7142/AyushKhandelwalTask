package com.example.feature.holdings.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.feature.holdings.presentation.model.PortfolioUiRenderedState.ERROR
import com.example.feature.holdings.presentation.model.PortfolioUiRenderedState.LOADING
import com.example.feature.holdings.presentation.model.PortfolioUiRenderedState.NO_INTERNET
import com.example.feature.holdings.presentation.model.PortfolioUiRenderedState.SUCCESS
import com.example.feature.holdings.presentation.viewmodel.PortfolioViewModel

@Composable
fun PortfolioScreen(
    modifier: Modifier = Modifier,
    viewModel: PortfolioViewModel,
) {
    val state by viewModel.state.collectAsState()

    Surface(modifier = modifier.fillMaxSize()) {
        when (state.uiRenderedState) {
            is LOADING -> {
                Loading()
            }

            is ERROR -> {
                ErrorStateText(message = (state.uiRenderedState as ERROR).message)
            }

            is NO_INTERNET -> {
                NoInternetState(onRetry = viewModel::refresh)
            }

            is SUCCESS -> {
                PortfolioContent(
                    holdings = state.holdings,
                    isExpanded = state.isExpanded,
                    onToggle = viewModel::toggleExpanded,
                    summary = state.summary,
                    formatCurrency = viewModel::formatCurrency
                )
            }
        }
    }
}
