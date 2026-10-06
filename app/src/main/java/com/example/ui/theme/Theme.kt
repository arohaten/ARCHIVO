package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val ForensicDarkColorScheme = darkColorScheme(
    primary = PericialCyan,
    onPrimary = SlateBlack,
    primaryContainer = SlateSurface,
    onPrimaryContainer = PericialCyan,
    secondary = ArchivalMuted,
    onSecondary = SlateBlack,
    secondaryContainer = SlateCard,
    onSecondaryContainer = ArchivalPaper,
    background = SlateBlack,
    onBackground = ArchivalPaper,
    surface = SlateDark,
    onSurface = ArchivalPaper,
    surfaceVariant = SlateSurface,
    onSurfaceVariant = ArchivalMuted,
    error = RedactRed,
    onError = ArchivalPaper,
    errorContainer = RedactRedDark,
    onErrorContainer = ArchivalPaper
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ForensicDarkColorScheme,
        typography = ArchivalTypography,
        content = content
    )
}
