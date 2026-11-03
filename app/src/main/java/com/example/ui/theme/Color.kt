package com.example.core.designsystem

import androidx.compose.ui.graphics.Color

// Light Mode - Authentic Deployed Sarthi Canvas (Clean Off-White & Crisp Surfaces)
val LightBackground = Color(0xFFF8F9FA)
val LightSurface = Color(0xFFFFFFFF)
val LightSurfaceElevated = Color(0xFFF1F5F9)
val LightSurfaceCard = Color(0xFFFFFFFF)
val LightHover = Color(0xFFF1F5F9)
val LightActive = Color(0xFFE2E8F0)

val LightBorder = Color(0xFFE2E8F0)
val LightBorderSecondary = Color(0xFFCBD5E1)

val LightTextPrimary = Color(0xFF0F172A)
val LightTextSecondary = Color(0xFF475569)
val LightTextTertiary = Color(0xFF64748B)
val LightTextMuted = Color(0xFF94A3B8)

// Dark Mode - True Neutral Black & Pure AMOLED (Zero Slate/Bluish Tints)
val DarkBackground = Color(0xFF000000)        // Pure AMOLED Black Canvas
val DarkSurfaceSecondary = Color(0xFF050505) // Deep Neutral Background
val DarkSurfaceElevated = Color(0xFF0A0A0A)  // Header & Navigation Surface
val DarkSurfaceCard = Color(0xFF0D0D0D)      // Cards & Panels (#0A0A0A - #0D0D0D)
val DarkHover = Color(0xFF141414)
val DarkActive = Color(0xFF1A1A1A)

val DarkBorderPrimary = Color(0xFF222222)    // Crisp low-glare borders (#222222)
val DarkBorderSecondary = Color(0xFF262626)

val DarkTextPrimary = Color(0xFFFFFFFF)      // Pure crisp white text
val DarkTextSecondary = Color(0xFFD4D4D4)    // Neutral light grey
val DarkTextTertiary = Color(0xFFA3A3A3)     // Neutral medium grey
val DarkTextMuted = Color(0xFF888888)        // Neutral muted grey

// Core Brand Accents (Sarthi Deployed Website & Campus OS Spec)
val SarthiRoyalBlue = Color(0xFF1D4ED8) // Primary Interactive & Brand Action
val SarthiBlueLight = Color(0xFF2563EB)
val SarthiBlueContainer = Color(0xFFEFF6FF)
val SarthiBlueContainerDark = Color(0xFF1E3A8A).copy(alpha = 0.35f)

val SarthiEmerald = Color(0xFF10B981) // Positive States & Safe Margin (+7.5%)
val SarthiEmeraldDark = Color(0xFF059669)
val SarthiEmeraldContainer = Color(0xFFECFDF5)
val SarthiEmeraldContainerDark = Color(0xFF064E3B).copy(alpha = 0.35f)

val SarthiRedSOS = Color(0xFFDC2626) // Critical Helplines, Debarment & SOS
val SarthiRedContainer = Color(0xFFFEF2F2)
val SarthiRedContainerDark = Color(0xFF7F1D1D).copy(alpha = 0.35f)

val SarthiAmber = Color(0xFFD97706) // Borderline & Warning States
val SarthiAmberContainer = Color(0xFFFFFBEB)

val SarthiGold = Color(0xFFF5A623) // Institute Seal & Honor Badges
val SarthiGoldMuted = Color(0xFFD97706)
val SarthiGoldContainerDark = Color(0xFF241A08)
val SarthiGoldContainerLight = Color(0xFFFEF3C7)

val SarthiVerifiedGreen = SarthiEmerald
val SarthiWarningAmber = SarthiAmber
val SarthiErrorRed = SarthiRedSOS
val SarthiInfoBlue = SarthiBlueLight
