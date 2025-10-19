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
import androidx.compose.ui.res.painterResource
import com.example.coreui.Dimens.CORNER_RADIUS_MEDIUM
import com.example.coreui.Dimens.SPACING_MEDIUM
import com.example.coreui.LightGray
import com.example.coreui.Strings.CD_ARROW_DOWN
import com.example.coreui.Strings.CD_ARROW_UP
import com.example.coreui.Strings.LABEL_CURRENT_VALUE
import com.example.coreui.Strings.LABEL_TODAY_PNL
import com.example.coreui.Strings.LABEL_TOTAL_INVESTMENT
import com.example.coreui.Strings.LABEL_TOTAL_PNL
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
                    color = LightGray,
                    shape = RoundedCornerShape(size = CORNER_RADIUS_MEDIUM),
                )
                .padding(horizontal = SPACING_MEDIUM),
        ) {
            if (summary != null) {
                if (isExpanded) {
                    SummaryRow(label = LABEL_CURRENT_VALUE, value = summary.currentValue)
                    SummaryRow(label = LABEL_TOTAL_INVESTMENT, value = summary.totalInvestment)
                    SummaryRow(label = LABEL_TODAY_PNL, value = summary.todayPnl)
                    HorizontalDivider()
                }
                SummaryRow(label = LABEL_TOTAL_PNL, value = summary.totalPnl, bold = true) {
                    Icon(
                        painter = painterResource(id = if (isExpanded) R.drawable.ic_arrow_down else R.drawable.ic_arrow_up),
                        contentDescription = if (isExpanded) CD_ARROW_DOWN else CD_ARROW_UP,
                    )
                }
            }
        }
    }
}