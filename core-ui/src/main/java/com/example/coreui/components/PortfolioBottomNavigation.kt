package com.example.coreui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import com.example.coreui.R
import com.example.coreui.commonUtils.Dimens.size1
import com.example.coreui.commonUtils.Dimens.size4
import com.example.coreui.commonUtils.Dimens.size72
import com.example.coreui.commonUtils.LightGray
import com.example.coreui.commonUtils.Purple40
import com.example.coreui.commonUtils.Strings.CD_PORTFOLIO_ICON
import com.example.coreui.commonUtils.Strings.NAV_PORTFOLIO

@Composable
fun PortfolioBottomNavigation(
    modifier: Modifier = Modifier,
    onPortfolioClick: () -> Unit = {},
    isSelected: Boolean = true,
) {
    NavigationBar(
        modifier = modifier.fillMaxWidth(),
        containerColor = LightGray,
        contentColor = colorScheme.onSurface
    ) {
        Box(contentAlignment = Alignment.TopCenter) {
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .width(width = size72)
                        .height(height = size4)
                        .background(
                            color = Purple40,
                            shape = RoundedCornerShape(size = size1),
                        )
                )
            }

            this@NavigationBar.NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_portfolio),
                        contentDescription = CD_PORTFOLIO_ICON,
                        tint = Purple40,
                    )
                },
                label = {
                    Text(
                        text = NAV_PORTFOLIO,
                        fontWeight = FontWeight.Bold,
                        color = Purple40,
                    )
                },
                selected = isSelected,
                onClick = onPortfolioClick,
            )
        }
    }
}
