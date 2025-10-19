package com.example.coreui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import com.example.coreui.R
import com.example.coreui.Strings.CD_PORTFOLIO_ICON
import com.example.coreui.Strings.NAV_PORTFOLIO

@Composable
fun PortfolioBottomNavigation(
    modifier: Modifier = Modifier,
    onPortfolioClick: () -> Unit = {},
    isPortfolioSelected: Boolean = true,
) {
    NavigationBar(
        modifier = modifier.fillMaxWidth(),
        containerColor = colorScheme.surface,
        contentColor = colorScheme.onSurface
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_portfolio),
                    contentDescription = CD_PORTFOLIO_ICON
                )
            },
            label = {
                Text(
                    text = NAV_PORTFOLIO,
                    fontWeight = if (isPortfolioSelected) FontWeight.Bold else FontWeight.Normal
                )
            },
            selected = isPortfolioSelected,
            onClick = onPortfolioClick,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colorScheme.primary,
                selectedTextColor = colorScheme.primary,
            ),

        )
    }
}
