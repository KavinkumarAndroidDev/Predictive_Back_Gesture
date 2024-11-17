package com.kkdev.predictivebackgesture.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp


data class AppColorScheme(
    val primary: Color,
    val onPrimary: Color,
    val surface: Color,
    val onSurface: Color,
    val background: Color,
    val onBackground: Color,
    val error: Color,
    val onError: Color,
    val tertiary: Color,
    val dbc: Color

)

data class AppShape(
    val container: Shape,
    val button: Shape

)

data class AppSize(
    val large: Dp,
    val medium: Dp,
    val normal: Dp,
    val small: Dp
)

val LocalAppColorScheme = staticCompositionLocalOf {
    AppColorScheme(
        primary = Color.Unspecified,
        onPrimary = Color.Unspecified,
        surface = Color.Unspecified,
        onSurface = Color.Unspecified,
        background = Color.Unspecified,
        onBackground = Color.Unspecified,
        error =Color.Unspecified,
        onError = Color.Unspecified,
        tertiary = Color.Unspecified,
        dbc = Color.Unspecified
    )
}

val LocalAppShape = staticCompositionLocalOf {
    AppShape(
        container = RectangleShape,
        button = RectangleShape
    )
}

val LocalAppSize = staticCompositionLocalOf {
    AppSize(
        large = Dp.Unspecified,
        medium = Dp.Unspecified,
        normal = Dp.Unspecified,
        small = Dp.Unspecified
    )
}

