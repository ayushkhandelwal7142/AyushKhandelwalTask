package com.example.ayushkhandelwaltask.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ayushkhandelwaltask.R
import com.example.ayushkhandelwaltask.domain.model.Holding
import com.example.ayushkhandelwaltask.domain.model.PortfolioSummary
import com.example.ayushkhandelwaltask.presentation.model.PortfolioUiRenderedState.ERROR
import com.example.ayushkhandelwaltask.presentation.model.PortfolioUiRenderedState.LOADING
import com.example.ayushkhandelwaltask.presentation.model.PortfolioUiRenderedState.SUCCESS
import com.example.ayushkhandelwaltask.presentation.viewModel.PortfolioViewModel
import java.util.Locale

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
                ErrorText(message = (state.uiRenderedState as ERROR).message)
            }

            is SUCCESS -> {
                PortfolioContent(
                    holdings = state.holdings,
                    isExpanded = state.isExpanded,
                    onToggle = viewModel::toggleExpanded,
                    summary = state.summary,
                )
            }
        }
    }
}

@Composable
private fun PortfolioContent(
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

@Composable
private fun SummaryRow(
    label: String,
    value: Double,
    bold: Boolean = false,
    icon: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row {
            Text(text = label)

            icon?.invoke()
        }

        val text = String.format(
            locale = Locale.US,
            format = "₹ %, .2f",
            value,
        )
        val color = if (value >= 0)
            Color(color = 0xFF1B5E20)
        else
            Color(color = 0xFFC62828)

        Text(
            text = text,
            color = color,
            fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal,
        )
    }
}

@Composable
private fun HoldingRow(holding: Holding) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = holding.symbol,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 12.sp,
                                color = Color.Gray,
                            )
                        ) {
                            append("NET QTY: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                            )
                        ) {
                            append("${holding.quantity}")
                        }
                    },
                    textAlign = TextAlign.Center,
                )
            }
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center,
            ) {
                val formattedLtpString = String.format(
                    locale = Locale.US,
                    format = "%.2f",
                    holding.lastTradedPrice,
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 12.sp,
                                color = Color.Gray,
                            )
                        ) {
                            append("LTP: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 18.sp,
                            )
                        ) {
                            append("₹ $formattedLtpString")
                        }
                    },
                    textAlign = TextAlign.Center,
                )

                val pnl = (holding.lastTradedPrice - holding.averagePrice) * holding.quantity
                val color = if (pnl >= 0) Color(color = 0xFF1B5E20) else Color(color = 0xFFC62828)
                val formattedPnlString = String.format(
                    locale = Locale.US,
                    format = "%.2f",
                    pnl,
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 12.sp,
                                color = Color.Gray,
                            )
                        ) {
                            append("P&L: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 18.sp,
                                color = color,
                            )
                        ) {
                            append("₹ $formattedPnlString")
                        }
                    },
                    textAlign = TextAlign.Center,
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        HorizontalDivider(modifier = Modifier.background(Color(color = 0x11000000)))
    }
}

@Composable
private fun Loading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorText(message: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = message, color = Color.Red)
    }
}


