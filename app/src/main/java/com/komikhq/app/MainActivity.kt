package com.komikhq.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.komikhq.app.data.local.TokenStorage
import com.komikhq.app.ui.auth.LoginScreen
import com.komikhq.app.ui.home.HomeScreen
import com.komikhq.app.ui.navigation.Routes
import com.komikhq.app.ui.theme.KomikHQTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var tokenStorage: TokenStorage

    private var isReady by mutableStateOf(false)
    private var startRoute by mutableStateOf(Routes.AUTH)

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        // Hold splash until session check completes
        splashScreen.setKeepOnScreenCondition { !isReady }

        // Check existing session token
        lifecycleScope.launch {
            val token = tokenStorage.getToken()
            startRoute = if (!token.isNullOrBlank()) Routes.HOME else Routes.AUTH
            isReady = true
        }

        enableEdgeToEdge()

        setContent {
            KomikHQTheme {
                if (isReady) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = startRoute
                    ) {
                        composable(Routes.AUTH) {
                            LoginScreen(
                                onLoginSuccess = {
                                    navController.navigate(Routes.HOME) {
                                        popUpTo(Routes.AUTH) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(Routes.HOME) {
                            HomeScreen(
                                tokenStorage = tokenStorage,
                                onLogout = {
                                    navController.navigate(Routes.AUTH) {
                                        popUpTo(Routes.HOME) { inclusive = true }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
