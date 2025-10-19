package com.example.feature.holdings.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.coreui.Dimens.SPACING_NORMAL
import com.example.coreui.LossRed
import com.example.coreui.ProfitGreen
import com.example.coreui.Strings.FORMAT_CURRENCY
import java.util.Locale

@Composable
fun SummaryRow(
    label: String,
    value: Double,
    bold: Boolean = false,
    icon: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = SPACING_NORMAL),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row {
            Text(text = label)

            icon?.invoke()
        }

        val text = String.format(
            locale = Locale.US,
            format = FORMAT_CURRENCY,
            value,
        )
        val color = if (value >= 0) ProfitGreen else LossRed

        Text(
            text = text,
            color = color,
            fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal,
        )
    }
}