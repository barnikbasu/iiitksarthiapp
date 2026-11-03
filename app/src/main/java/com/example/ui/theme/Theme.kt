package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.designsystem.DarkActive
import com.example.core.designsystem.DarkBackground
import com.example.core.designsystem.DarkBorderPrimary
import com.example.core.designsystem.DarkBorderSecondary
import com.example.core.designsystem.DarkHover
import com.example.core.designsystem.DarkSurfaceCard
import com.example.core.designsystem.DarkSurfaceElevated
import com.example.core.designsystem.DarkSurfaceSecondary
import com.example.core.designsystem.DarkTextMuted
import com.example.core.designsystem.DarkTextPrimary
import com.example.core.designsystem.DarkTextSecondary
import com.example.core.designsystem.LightActive
import com.example.core.designsystem.LightBackground
import com.example.core.designsystem.LightBorder
import com.example.core.designsystem.LightBorderSecondary
import com.example.core.designsystem.LightSurface
import com.example.core.designsystem.LightSurfaceCard
import com.example.core.designsystem.LightSurfaceElevated
import com.example.core.designsystem.LightTextMuted
import com.example.core.designsystem.LightTextPrimary
import com.example.core.designsystem.LightTextSecondary
import com.example.core.designsystem.SarthiBlueContainer
import com.example.core.designsystem.SarthiBlueContainerDark
import com.example.core.designsystem.SarthiEmerald
import com.example.core.designsystem.SarthiRedSOS
import com.example.core.designsystem.SarthiRoyalBlue

// 1. MATERIAL 3 COLOR SCHEMES (Aligned with IIITK Sarthi Brand Identity)

val SarthiDarkColorScheme = darkColorScheme(
    primary = SarthiRoyalBlue,
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = SarthiBlueContainerDark,
    onPrimaryContainer = Color(0xFFBFDBFE),
    secondary = SarthiEmerald,
    onSecondary = Color(0xFF000000),
    secondaryContainer = DarkActive,
    onSecondaryContainer = DarkTextPrimary,
    background = DarkBackground,          // Pure AMOLED Black #000000
    onBackground = DarkTextPrimary,        // Crisp Pure White #FFFFFF
    surface = DarkSurfaceElevated,         // Header & Elevated Surface #0A0A0A
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceCard,      // Card & Module Container #0D0D0D
    onSurfaceVariant = DarkTextSecondary,  // Neutral readable text #D4D4D4
    outline = DarkBorderPrimary,           // Low-glare crisp border #222222
    outlineVariant = DarkBorderSecondary,  // Structural border #262626
    error = SarthiRedSOS,                  // Critical Helplines & SOS #DC2626
    onError = Color(0xFFFFFFFF)
)

val SarthiLightColorScheme = lightColorScheme(
    primary = SarthiRoyalBlue,             // Primary Interactive & Brand Action #1D4ED8
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = SarthiBlueContainer,// Accent Container #EFF6FF
    onPrimaryContainer = SarthiRoyalBlue,
    secondary = SarthiEmerald,             // Attendance Safe (>75%) & Verified #10B981
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = LightSurfaceElevated,
    onSecondaryContainer = LightTextPrimary,
    background = LightBackground,          // Signature Sarthi Canvas #F8F9FA
    onBackground = LightTextPrimary,        // High-contrast Navy/Slate #0F172A
    surface = LightSurface,                // Crisp White Surface #FFFFFF
    onSurface = LightTextPrimary,
    surfaceVariant = LightSurfaceCard,     // Surface Container #FFFFFF
    onSurfaceVariant = LightTextSecondary, // Secondary body #475569
    outline = LightBorder,                 // Clean card border #E2E8F0
    outlineVariant = LightBorderSecondary, // Secondary outline #CBD5E1
    error = SarthiRedSOS,                  // Emergency & Debarment #DC2626
    onError = Color(0xFFFFFFFF)
)

// 2. SARTHI TYPOGRAPHY DEFINITIONS (Humanist Sans with Tabular & Tracked Accents)

val SarthiTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 38.sp,
        letterSpacing = (-0.5).sp
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        lineHeight = 32.sp,
        letterSpacing = (-0.25).sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.2.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 13.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.3.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 15.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp,
        lineHeight = 15.sp,
        letterSpacing = 1.2.sp          // Upper-case section tracking
    )
)

// 3. SARTHI GEOMETRY & SHAPES (16dp Cards, 20dp Status Pills, 28dp Modals)

val SarthiShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),          // Signature Sarthi card border radius
    extraLarge = RoundedCornerShape(24.dp)      // Bottom sheets & dialog containers
)

// 4. CANONICAL SARTHI MATERIAL THEME WRAPPER

@Composable
fun SarthiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) SarthiDarkColorScheme else SarthiLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = SarthiTypography,
        shapes = SarthiShapes,
        content = content
    )
}
