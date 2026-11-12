package com.komikhq.app

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.komikhq.app.data.local.TokenStorage
import com.komikhq.app.feature.main.MainScreen
import com.komikhq.app.navigation.Routes
import com.komikhq.app.ui.theme.KomikHQTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var tokenStorage: TokenStorage

    private var isReady by mutableStateOf(false)
    private var startRoute by mutableStateOf(Routes.ONBOARDING_SPLASH)

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        // Smooth exit animation for splash screen
        splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->
            val fadeOut = ObjectAnimator.ofFloat(splashScreenViewProvider.view, View.ALPHA, 1f, 0f)
            fadeOut.duration = 250L
            fadeOut.interpolator = AccelerateDecelerateInterpolator()
            fadeOut.doOnEnd { splashScreenViewProvider.remove() }
            fadeOut.start()
        }

        // Hold splash until session check completes
        splashScreen.setKeepOnScreenCondition { !isReady }

        // Check existing session — if token exists, skip onboarding → go to Home
        lifecycleScope.launch {
            val token = tokenStorage.getToken()
            startRoute = if (!token.isNullOrBlank()) {
                Routes.HOME
            } else {
                Routes.ONBOARDING_SPLASH
            }
            isReady = true
        }

        enableEdgeToEdge()

        setContent {
            val themeMode by tokenStorage.themeModeFlow.collectAsStateWithLifecycle(initialValue = "system")
            val isDark = when (themeMode) {
                "dark" -> true
                "light" -> false
                else -> isSystemInDarkTheme()
            }

            KomikHQTheme(darkTheme = isDark) {
                if (isReady) {
                    MainScreen(startDestination = startRoute)
                }
            }
        }
    }
}
