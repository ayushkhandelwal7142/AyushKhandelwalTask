package com.example.feature.holdings.presentation.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.feature.holdings.R
import com.example.feature.holdings.domain.model.Holding
import com.example.feature.holdings.domain.model.PortfolioSummary

@Composable
fun PortfolioContent(
    holdings: List<Holding>,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    summary: PortfolioSummary?
) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.weight(weight = 1f)) {
            items(items = holdings) { holding ->
                HoldingRow(holding = holding)
            }
        }
        HorizontalDivider()
        Column(
            Modifier
                .fillMaxWidth()
                .clickable { onToggle() }
                .animateContentSize()
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(size = 8.dp),
                )
                .padding(horizontal = 10.dp),
        ) {
            if (summary != null) {
                if (isExpanded) {
                    SummaryRow(label = "Current value*", value = summary.currentValue)
                    SummaryRow(label = "Total investment*", value = summary.totalInvestment)
                    SummaryRow(label = "Today's Profit & Loss*", value = summary.todayPnl)
                    HorizontalDivider()
                }
                SummaryRow(label = "Profit & Loss*", value = summary.totalPnl, bold = true) {
                    Icon(
                        painter = painterResource(id = if (isExpanded) R.drawable.ic_arrow_down else R.drawable.ic_arrow_up),
                        contentDescription = "Up/Down arrow",
                    )
                }
            }
        }
    }
}