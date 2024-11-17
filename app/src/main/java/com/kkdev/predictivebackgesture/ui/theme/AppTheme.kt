package com.kkdev.predictivebackgesture.ui.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


private val lightColorScheme = AppColorScheme(
    primary = primaryColor, // Text color
    onPrimary = onPrimaryColor, // Text color on primary background
    surface = secondaryColor, // Background color
    onSurface = onSecondaryColor, // Text color on surface background
    background = onTertiaryColor, // Main background color
    onBackground = tertiaryColor, // Text color on main background
    error = Color.Red, // Button color
    onError = onPrimaryColor, // Text color on buttons
    tertiary = bodyTextColor,
    dbc = dbc
)

private val darkColorScheme = AppColorScheme(
    primary = primaryColor, // Text color
    onPrimary = onPrimaryColor, // Text color on primary background
    surface = secondaryColor, // Background color
    onSurface = onSecondaryColor, // Text color on surface background
    background = onTertiaryColor, // Main background color
    onBackground = tertiaryColor, // Text color on main background
    error = Color.Red, // Button color
    onError = onPrimaryColor, // Text color on buttons
    tertiary = bodyTextColor,
    dbc = dbc

)

/*
primary = dPrimary, // Text color
    onPrimary = dOnPrimary, // Text color on primary background
    surface = dSurface, // Background color
    onSurface = dOnSurface, // Text color on surface background
    background = dBackground, // Main background color
    onBackground = dOnBackground, // Text color on main background
    error = dError, // Button color
    onError = dOnError // Text color on buttons
 */

private val shape = AppShape(
    button = RoundedCornerShape(50.dp),
    container = RoundedCornerShape(14.dp)
)

private val size = AppSize(
    large = 24.dp,
    medium = 16.dp,
    normal = 12.dp,
    small = 8.dp
)

@Composable
fun AppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
){
    val colorScheme = if (isDarkTheme) darkColorScheme else lightColorScheme
//    val rippleIndication = rememberRipple()

    CompositionLocalProvider (
        LocalAppColorScheme provides colorScheme,
        LocalAppShape provides shape,
        LocalAppSize provides size,
        LocalIndication provides LocalIndication.current, // Updated to use the modern API
        content = content
    )
}


object AppTheme{
    val colorScheme: AppColorScheme
        @Composable get() = LocalAppColorScheme.current

    val shape:AppShape
        @Composable get() = LocalAppShape.current

    val size: AppSize
        @Composable get() = LocalAppSize.current
}