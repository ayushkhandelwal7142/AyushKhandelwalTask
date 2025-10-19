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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feature.holdings.domain.model.Holding
import java.util.Locale

@Composable
fun HoldingRow(holding: Holding) {
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