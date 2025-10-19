package com.example.feature.holdings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature.holdings.domain.exception.NetworkException
import com.example.feature.holdings.domain.exception.NoInternetException
import com.example.feature.holdings.domain.repository.HoldingsRepository
import com.example.feature.holdings.domain.usecase.ComputePortfolioSummary
import com.example.feature.holdings.presentation.model.PortfolioUiRenderedState
import com.example.feature.holdings.presentation.model.PortfolioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class PortfolioViewModel @Inject constructor(
    private val repository: HoldingsRepository,
    private val computePortfolioSummary: ComputePortfolioSummary,
) : ViewModel() {

    private val _state = MutableStateFlow(PortfolioUiState())
    val state = _state.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _state.update { it.copy(uiRenderedState = PortfolioUiRenderedState.LOADING) }
            try {
                val holdings = repository.fetchHoldings()
                val summary = computePortfolioSummary(holdings)
                _state.update {
                    it.copy(
                        holdings = holdings,
                        summary = summary,
                        uiRenderedState = PortfolioUiRenderedState.SUCCESS,
                    )
                }

            } catch (e: NoInternetException) {
                _state.update { it.copy(uiRenderedState = PortfolioUiRenderedState.NO_INTERNET) }
            } catch (e: NetworkException) {
                _state.update {
                    it.copy(
                        uiRenderedState = PortfolioUiRenderedState.ERROR(
                            e.message ?: "Network error"
                        ),
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        uiRenderedState = PortfolioUiRenderedState.ERROR(
                            e.message ?: "Unknown error"
                        ),
                    )
                }
            }
        }
    }

    fun toggleExpanded() {
        _state.update { it.copy(isExpanded = !_state.value.isExpanded) }
    }
}
