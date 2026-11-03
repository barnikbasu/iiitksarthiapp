package com.example

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.feature.auth.LoginScreen
import com.example.ui.navigation.SarthiAppRoot
import com.example.ui.theme.SarthiTheme
import com.example.ui.viewmodel.SarthiAppViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: SarthiAppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val themePrefs = getSharedPreferences("sarthi_theme_prefs", Context.MODE_PRIVATE)
        val initialDark = themePrefs.getBoolean("is_dark_theme", false) // Follows the deployed website's signature canvas

        setContent {
            var isDarkTheme by remember { mutableStateOf(initialDark) }
            val isLoggedIn by viewModel.isLoggedIn.collectAsState()

            SarthiTheme(darkTheme = isDarkTheme) {
                if (!isLoggedIn) {
                    LoginScreen(
                        viewModel = viewModel,
                        isDarkTheme = isDarkTheme,
                        onToggleDarkTheme = { newTheme ->
                            isDarkTheme = newTheme
                            themePrefs.edit().putBoolean("is_dark_theme", newTheme).apply()
                        }
                    )
                } else {
                    SarthiAppRoot(
                        viewModel = viewModel,
                        isDarkTheme = isDarkTheme,
                        onToggleDarkTheme = { newTheme ->
                            isDarkTheme = newTheme
                            themePrefs.edit().putBoolean("is_dark_theme", newTheme).apply()
                        }
                    )
                }
            }
        }
    }
}
