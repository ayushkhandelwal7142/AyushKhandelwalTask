package com.example.ayushkhandelwaltask.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ayushkhandelwaltask.domain.repository.HoldingsRepository
import com.example.ayushkhandelwaltask.domain.usecase.ComputePortfolioSummary
import com.example.ayushkhandelwaltask.presentation.model.PortfolioUiRenderedState
import com.example.ayushkhandelwaltask.presentation.model.PortfolioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PortfolioViewModel(
    private val repository: HoldingsRepository,
    private val computePortfolioSummary: ComputePortfolioSummary
) : ViewModel() {

    private val _state =
        MutableStateFlow(value = PortfolioUiState(uiRenderedState = PortfolioUiRenderedState.LOADING))
    val state = _state.asStateFlow()

    init {
        refresh()
    }

    fun toggleExpanded() {
        _state.update {
            it.copy(isExpanded = _state.value.isExpanded.not())
        }
    }

    private fun refresh() {
        _state.update {
            it.copy(uiRenderedState = PortfolioUiRenderedState.LOADING)
        }

        viewModelScope.launch {
            try {
                val listOfHoldings = repository.fetchHoldings()
                val summary = computePortfolioSummary(holdings = listOfHoldings)
                _state.update {
                    it.copy(
                        uiRenderedState = PortfolioUiRenderedState.SUCCESS,
                        holdings = listOfHoldings,
                        summary = summary,
                    )
                }
            } catch (t: Throwable) {
                _state.update {
                    it.copy(uiRenderedState = PortfolioUiRenderedState.ERROR(message = t.message.orEmpty()))
                }
            }
        }
    }
}