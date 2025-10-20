package com.example.feature.holdings.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.coreui.commonUtils.Black
import com.example.coreui.commonUtils.Dimens.size12
import com.example.coreui.commonUtils.LossRed
import com.example.coreui.commonUtils.ProfitGreen

@Composable
fun SummaryRow(
    label: String,
    value: Double,
    bold: Boolean = false,
    formatCurrency: (Double) -> String,
    icon: @Composable (() -> Unit)? = null,
    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = size12),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row {
            Text(
                text = label,
                color = Black
            )

            icon?.invoke()
        }

        val formattedCurrency = formatCurrency(value)
        val color = if (value >= 0) ProfitGreen else LossRed

        Text(
            text = formattedCurrency,
            color = color,
            fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal,
        )
    }
}

@Preview
@Composable
private fun SummaryRowUiPreview() {
    SummaryRow(
        label = "Profit & Loss*",
        value = 1200.0,
        bold = true,
        formatCurrency = { amount -> "" },
        icon = { },
    )
}