package com.example.feature.holdings.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coreui.commonUtils.Dimens.size12
import com.example.coreui.commonUtils.Dimens.size16
import com.example.coreui.commonUtils.Dimens.size8
import com.example.coreui.commonUtils.Dimens.textSize12
import com.example.coreui.commonUtils.Dimens.textSize18
import com.example.coreui.commonUtils.DividerGray
import com.example.coreui.commonUtils.Gray
import com.example.coreui.commonUtils.LossRed
import com.example.coreui.commonUtils.ProfitGreen
import com.example.coreui.commonUtils.Strings.CURRENCY_SYMBOL
import com.example.coreui.commonUtils.Strings.FORMAT_DECIMAL
import com.example.coreui.commonUtils.Strings.LABEL_LTP
import com.example.coreui.commonUtils.Strings.LABEL_NET_QTY
import com.example.coreui.commonUtils.Strings.LABEL_PNL
import com.example.feature.holdings.domain.model.Holding
import java.util.Locale

@Composable
fun HoldingRow(holding: Holding) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = size16,
                vertical = size12
            ),
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

                Spacer(modifier = Modifier.padding(vertical = 4.dp))

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = textSize12,
                                color = Gray,
                            )
                        ) {
                            append(LABEL_NET_QTY)
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = textSize18,
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
                    format = FORMAT_DECIMAL,
                    holding.lastTradedPrice,
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = textSize12,
                                color = Gray,
                            )
                        ) {
                            append(LABEL_LTP)
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = textSize18,
                            )
                        ) {
                            append("$CURRENCY_SYMBOL $formattedLtpString")
                        }
                    },
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.padding(vertical = 4.dp))

                val pnl = (holding.lastTradedPrice - holding.averagePrice) * holding.quantity
                val formattedPnlString = String.format(
                    locale = Locale.US,
                    format = FORMAT_DECIMAL,
                    pnl,
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = textSize12,
                                color = Gray,
                            )
                        ) {
                            append(LABEL_PNL)
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = textSize18,
                                color = if (pnl >= 0) ProfitGreen else LossRed,
                            )
                        ) {
                            append("$CURRENCY_SYMBOL $formattedPnlString")
                        }
                    },
                    textAlign = TextAlign.Center,
                )
            }
        }
        Spacer(Modifier.height(size8))
        HorizontalDivider(modifier = Modifier.background(DividerGray))
    }
}

private val holdingPreviewData = Holding(
    symbol = "ICICI",
    quantity = 50,
    lastTradedPrice = 150.0,
    averagePrice = 120.0,
    closePrice = 140.0
)

@Preview
@Composable
fun HoldingRowPreview() {
    HoldingRow(holding = holdingPreviewData)
}