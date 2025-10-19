package com.example.ayushkhandelwaltask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.coreui.AyushTaskTheme
import com.example.coreui.components.PortfolioBottomNavigation
import com.example.coreui.components.PortfolioTopAppBar
import com.example.feature.holdings.domain.repository.HoldingsRepository
import com.example.feature.holdings.domain.usecase.ComputePortfolioSummary
import com.example.feature.holdings.presentation.ui.PortfolioScreen
import com.example.feature.holdings.presentation.viewmodel.PortfolioViewModel
import com.example.feature.holdings.presentation.viewmodel.PortfolioViewModelFactory
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var repository: HoldingsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = application as App
        app.appComponent.inject(this)

        val factory = PortfolioViewModelFactory(repository, ComputePortfolioSummary())
        val viewModel: PortfolioViewModel by viewModels(factoryProducer = { factory })

        setContent {
            AyushTaskTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background,
                    topBar = {
                        PortfolioTopAppBar()
                    },
                    bottomBar = {
                        PortfolioBottomNavigation()
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        PortfolioScreen(
                            modifier = Modifier.fillMaxSize(),
                            viewModel = viewModel,
                        )
                    }
                }
            }
        }
    }
}