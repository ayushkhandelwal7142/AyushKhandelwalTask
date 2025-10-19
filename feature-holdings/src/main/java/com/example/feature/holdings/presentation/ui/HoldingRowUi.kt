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
import com.example.coreui.Dimens.SPACING_LARGE
import com.example.coreui.Dimens.SPACING_NORMAL
import com.example.coreui.Dimens.SPACING_SMALL
import com.example.coreui.Dimens.TEXT_SIZE_LARGE
import com.example.coreui.Dimens.TEXT_SIZE_SMALL
import com.example.coreui.DividerGray
import com.example.coreui.Gray
import com.example.coreui.LossRed
import com.example.coreui.ProfitGreen
import com.example.coreui.Strings.CURRENCY_SYMBOL
import com.example.coreui.Strings.FORMAT_DECIMAL
import com.example.coreui.Strings.LABEL_LTP
import com.example.coreui.Strings.LABEL_NET_QTY
import com.example.coreui.Strings.LABEL_PNL
import com.example.feature.holdings.domain.model.Holding
import java.util.Locale

@Composable
fun HoldingRow(holding: Holding) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = SPACING_LARGE,
                vertical = SPACING_NORMAL
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
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = TEXT_SIZE_SMALL,
                                color = Gray,
                            )
                        ) {
                            append(LABEL_NET_QTY)
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = TEXT_SIZE_LARGE,
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
                                fontSize = TEXT_SIZE_SMALL,
                                color = Gray,
                            )
                        ) {
                            append(LABEL_LTP)
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = TEXT_SIZE_LARGE,
                            )
                        ) {
                            append("$CURRENCY_SYMBOL $formattedLtpString")
                        }
                    },
                    textAlign = TextAlign.Center,
                )

                val pnl = (holding.lastTradedPrice - holding.averagePrice) * holding.quantity
                val color = if (pnl >= 0) ProfitGreen else LossRed
                val formattedPnlString = String.format(
                    locale = Locale.US,
                    format = FORMAT_DECIMAL,
                    pnl,
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = TEXT_SIZE_SMALL,
                                color = Gray,
                            )
                        ) {
                            append(LABEL_PNL)
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = TEXT_SIZE_LARGE,
                                color = color,
                            )
                        ) {
                            append("$CURRENCY_SYMBOL $formattedPnlString")
                        }
                    },
                    textAlign = TextAlign.Center,
                )
            }
        }
        Spacer(Modifier.height(SPACING_SMALL))
        HorizontalDivider(modifier = Modifier.background(DividerGray))
    }
}