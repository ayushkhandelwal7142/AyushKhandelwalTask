package com.example.coreui

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily.Companion.Default
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import com.example.coreui.commonUtils.Dimens.textSize05
import com.example.coreui.commonUtils.Dimens.textSize16
import com.example.coreui.commonUtils.Dimens.textSize24
import com.example.coreui.commonUtils.Pink40
import com.example.coreui.commonUtils.Pink80
import com.example.coreui.commonUtils.Purple40
import com.example.coreui.commonUtils.Purple80
import com.example.coreui.commonUtils.PurpleGrey40
import com.example.coreui.commonUtils.PurpleGrey80

@Composable
fun AyushTaskTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

private val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Default,
        fontWeight = Normal,
        fontSize = textSize16,
        lineHeight = textSize24,
        letterSpacing = textSize05
    )
)


