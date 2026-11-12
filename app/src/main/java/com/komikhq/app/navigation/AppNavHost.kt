package com.komikhq.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.komikhq.app.feature.auth.AuthErrorDialog
import com.komikhq.app.feature.auth.AuthSuccessScreen
import com.komikhq.app.feature.auth.EmailSentScreen
import com.komikhq.app.feature.auth.EmailVerificationScreen
import com.komikhq.app.feature.auth.ForgotPasswordScreen
import com.komikhq.app.feature.auth.PasswordSuccessScreen
import com.komikhq.app.feature.auth.ResetPasswordScreen
import com.komikhq.app.feature.auth.SignInScreen
import com.komikhq.app.feature.auth.SignUpScreen
import com.komikhq.app.feature.auth.VerificationExpiredScreen
import com.komikhq.app.feature.detail.ComicDetailScreen
import com.komikhq.app.feature.detail.ComicDetailViewModel
import com.komikhq.app.feature.explore.CatalogCompletedScreen
import com.komikhq.app.feature.explore.CatalogPopularScreen
import com.komikhq.app.feature.explore.CatalogTrendingScreen
import com.komikhq.app.feature.explore.ExploreScreen
import com.komikhq.app.feature.explore.ExploreViewModel
import com.komikhq.app.feature.home.HomeScreen
import com.komikhq.app.feature.home.HomeViewModel
import com.komikhq.app.feature.legal.AboutScreen
import com.komikhq.app.feature.legal.ContactScreen
import com.komikhq.app.feature.legal.CommunityGuidelinesScreen
import com.komikhq.app.feature.legal.CreditsScreen
import com.komikhq.app.feature.legal.LicensesScreen
import com.komikhq.app.feature.legal.PrivacyPolicyScreen
import com.komikhq.app.feature.legal.SupportFormScreen
import com.komikhq.app.feature.legal.SupportSentScreen
import com.komikhq.app.feature.legal.TermsScreen
import com.komikhq.app.feature.library.LibraryScreen
import com.komikhq.app.feature.library.LibraryViewModel
import com.komikhq.app.feature.onboarding.GuestAccessScreen
import com.komikhq.app.feature.onboarding.IntroductionScreen
import com.komikhq.app.feature.onboarding.SplashScreen
import com.komikhq.app.feature.onboarding.WelcomeScreen
import com.komikhq.app.feature.profile.AvatarPickerScreen
import com.komikhq.app.feature.profile.EditProfileScreen
import com.komikhq.app.feature.profile.ProfileScreen
import com.komikhq.app.feature.reader.ReaderScreen
import com.komikhq.app.feature.reader.ReaderViewModel
import com.komikhq.app.feature.settings.AccountDeletedScreen
import com.komikhq.app.feature.settings.AccountInfoScreen
import com.komikhq.app.feature.settings.ChangePasswordScreen
import com.komikhq.app.feature.settings.DeleteAccountScreen
import com.komikhq.app.feature.settings.LanguageScreen
import com.komikhq.app.feature.settings.NotificationSettingsScreen
import com.komikhq.app.feature.settings.PrivacyScreen
import com.komikhq.app.feature.settings.ReadingPreferencesScreen
import com.komikhq.app.feature.settings.ReadingStatsScreen
import com.komikhq.app.feature.settings.SecurityScreen
import com.komikhq.app.feature.settings.ThemeScreen
import com.komikhq.app.feature.system.ErrorScreen
import com.komikhq.app.feature.system.NotificationPermissionScreen
import com.komikhq.app.feature.system.NotificationsScreen
import com.komikhq.app.feature.system.OfflineScreen
import com.komikhq.app.ui.auth.AuthViewModel

/**
 * Root NavHost wiring all 62 KomikHQ screen destinations.
 */
@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // ── Onboarding (KHQ 01-04) ──
        composable(Routes.ONBOARDING_SPLASH) {
            SplashScreen(
                onStartExploring = {
                    navController.navigate(Routes.ONBOARDING_WELCOME) {
                        popUpTo(Routes.ONBOARDING_SPLASH) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.ONBOARDING_WELCOME) {
            WelcomeScreen(
                onGetStarted = { navController.navigate(Routes.ONBOARDING_INTRO) },
                onSignIn = { navController.navigate(Routes.AUTH_SIGN_IN) }
            )
        }
        composable(Routes.ONBOARDING_INTRO) {
            IntroductionScreen(
                onFinish = { navController.navigate(Routes.ONBOARDING_GUEST) }
            )
        }
        composable(Routes.ONBOARDING_GUEST) {
            GuestAccessScreen(
                onRegister = { navController.navigate(Routes.AUTH_SIGN_UP) },
                onContinueGuest = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // ── Auth (KHQ 05-14, 58) ──
        composable(Routes.AUTH_SIGN_IN) {
            val authViewModel: AuthViewModel = hiltViewModel()
            SignInScreen(
                viewModel = authViewModel,
                onSignInSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onNavigateToSignUp = { navController.navigate(Routes.AUTH_SIGN_UP) },
                onNavigateToForgotPassword = { navController.navigate(Routes.AUTH_FORGOT_PASSWORD) },
                onContinueAsGuest = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.AUTH_SIGN_UP) {
            val authViewModel: AuthViewModel = hiltViewModel()
            SignUpScreen(
                viewModel = authViewModel,
                onSignUpSuccess = { navController.navigate(Routes.AUTH_VERIFY_OTP) },
                onNavigateToSignIn = { navController.popBackStack() }
            )
        }
        composable(Routes.AUTH_FORGOT_PASSWORD) {
            ForgotPasswordScreen(
                onSendResetLink = { /* send reset */ navController.navigate(Routes.AUTH_EMAIL_SENT) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.AUTH_EMAIL_SENT) {
            EmailSentScreen(
                email = "",
                onResend = { /* resend */ },
                onBackToSignIn = { navController.popBackStack() }
            )
        }
        composable(Routes.AUTH_RESET_PASSWORD) {
            ResetPasswordScreen(
                onPasswordReset = { navController.navigate(Routes.AUTH_PASSWORD_SUCCESS) },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Routes.AUTH_VERIFY_OTP) {
            EmailVerificationScreen(
                email = "",
                onVerify = { navController.navigate(Routes.AUTH_SUCCESS) },
                onResendCode = { /* resend */ },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Routes.AUTH_SUCCESS) {
            AuthSuccessScreen(
                onContinue = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.AUTH_PASSWORD_SUCCESS) {
            PasswordSuccessScreen(
                onBackToSignIn = {
                    navController.navigate(Routes.AUTH_SIGN_IN) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.AUTH_EXPIRED) {
            VerificationExpiredScreen(
                onResend = { navController.popBackStack() },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // ── Main Tabbed Screens ──
        // Note: MAIN route is handled by MainScreen which hosts this NavHost.
        // Home/Explore/Library are direct destinations within the single NavHost.

        composable(Routes.HOME) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                viewModel = homeViewModel,
                onComicClick = { slug -> navController.navigate(Routes.comicDetail(slug)) },
                onContinueReading = { slug, ch -> navController.navigate(Routes.comicReader(slug, ch)) },
                onNotificationsClick = { navController.navigate(Routes.NOTIFICATIONS) },
                onViewAllLatest = { navController.navigate(Routes.CATALOG_TRENDING) },
                onViewAllPopular = { navController.navigate(Routes.CATALOG_POPULAR) },
                onProfileClick = { navController.navigate(Routes.PROFILE) }
            )
        }
        composable(Routes.HOME_EDITORIAL) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                viewModel = homeViewModel,
                onComicClick = { slug -> navController.navigate(Routes.comicDetail(slug)) },
                onContinueReading = { slug, ch -> navController.navigate(Routes.comicReader(slug, ch)) },
                onNotificationsClick = { navController.navigate(Routes.NOTIFICATIONS) },
                onViewAllLatest = { navController.navigate(Routes.CATALOG_TRENDING) },
                onViewAllPopular = { navController.navigate(Routes.CATALOG_POPULAR) },
                onProfileClick = { navController.navigate(Routes.PROFILE) }
            )
        }
        composable(Routes.EXPLORE) {
            val exploreViewModel: ExploreViewModel = hiltViewModel()
            ExploreScreen(
                viewModel = exploreViewModel,
                onComicClick = { slug -> navController.navigate(Routes.comicDetail(slug)) },
                onFilterClick = { /* show filter dialog */ }
            )
        }

        // ── Catalog Sub-Screens (KHQ 60-62) ──
        composable(Routes.CATALOG_TRENDING) {
            val exploreViewModel: ExploreViewModel = hiltViewModel()
            CatalogTrendingScreen(
                viewModel = exploreViewModel,
                onComicClick = { slug -> navController.navigate(Routes.comicDetail(slug)) },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Routes.CATALOG_POPULAR) {
            val exploreViewModel: ExploreViewModel = hiltViewModel()
            CatalogPopularScreen(
                viewModel = exploreViewModel,
                onComicClick = { slug -> navController.navigate(Routes.comicDetail(slug)) },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Routes.CATALOG_COMPLETED) {
            val exploreViewModel: ExploreViewModel = hiltViewModel()
            CatalogCompletedScreen(
                viewModel = exploreViewModel,
                onComicClick = { slug -> navController.navigate(Routes.comicDetail(slug)) },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Routes.LIBRARY) {
            val libraryViewModel: LibraryViewModel = hiltViewModel()
            LibraryScreen(
                viewModel = libraryViewModel,
                onComicClick = { slug -> navController.navigate(Routes.comicDetail(slug)) },
                onExploreClick = { navController.navigate(Routes.EXPLORE) }
            )
        }

        // ── Comic Detail (KHQ 55) ──
        composable(
            route = Routes.COMIC_DETAIL,
            arguments = listOf(navArgument("slug") { type = NavType.StringType })
        ) {
            val viewModel: ComicDetailViewModel = hiltViewModel()
            ComicDetailScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onReadChapter = { slug, ch -> navController.navigate(Routes.comicReader(slug, ch)) }
            )
        }

        // ── Reader (KHQ 56-57) ──
        composable(
            route = Routes.COMIC_READER,
            arguments = listOf(
                navArgument("slug") { type = NavType.StringType },
                navArgument("chapterNumber") { type = NavType.StringType }
            )
        ) {
            val viewModel: ReaderViewModel = hiltViewModel()
            ReaderScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // ── Profile (KHQ 21-22) ──
        composable(Routes.PROFILE) {
            ProfileScreen(
                userName = null,
                userEmail = null,
                avatarUrl = null,
                isGuest = false,
                onEditProfile = { navController.navigate(Routes.PROFILE_EDIT) },
                onAccountInfo = { navController.navigate(Routes.PROFILE_INFO) },
                onReadingStats = { navController.navigate(Routes.PROFILE_STATS) },
                onReadingPrefs = { navController.navigate(Routes.SETTINGS_READING) },
                onSecurity = { navController.navigate(Routes.SETTINGS_SECURITY) },
                onNotifications = { navController.navigate(Routes.SETTINGS_NOTIFICATIONS) },
                onTheme = { navController.navigate(Routes.SETTINGS_THEME) },
                onLanguage = { navController.navigate(Routes.SETTINGS_LANGUAGE) },
                onAbout = { navController.navigate(Routes.LEGAL_ABOUT) },
                onLogout = { navController.navigate(Routes.AUTH_SIGN_IN) { popUpTo(0) { inclusive = true } } },
                onSignIn = { navController.navigate(Routes.AUTH_SIGN_IN) }
            )
        }
        composable(Routes.PROFILE_EDIT) {
            EditProfileScreen(
                currentName = "",
                currentUsername = "",
                currentBio = "",
                avatarUrl = null,
                onSave = { _, _, _ -> navController.popBackStack() },
                onAvatarClick = { navController.navigate(Routes.PROFILE_AVATAR) },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Routes.PROFILE_AVATAR) {
            AvatarPickerScreen(
                currentAvatarUrl = null,
                onAvatarSelected = { navController.popBackStack() },
                onUploadPhoto = { /* open photo picker */ },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // ── Settings (KHQ 25-33, 35-36) ──
        composable(Routes.PROFILE_INFO) {
            AccountInfoScreen(
                name = "", email = "", role = "", joinedDate = "",
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Routes.PROFILE_STATS) {
            ReadingStatsScreen(
                chaptersRead = 0, readingStreak = 0, favoriteGenres = emptyList(),
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Routes.SETTINGS_READING) {
            ReadingPreferencesScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Routes.SETTINGS_PASSWORD) {
            ChangePasswordScreen(
                onNavigateBack = { navController.popBackStack() },
                onSave = { _, _ -> navController.popBackStack() }
            )
        }
        composable(Routes.SETTINGS_PRIVACY) {
            PrivacyScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Routes.SETTINGS_SECURITY) {
            SecurityScreen(
                onNavigateBack = { navController.popBackStack() },
                onChangePassword = { navController.navigate(Routes.SETTINGS_PASSWORD) },
                onDeleteAccount = { navController.navigate(Routes.SETTINGS_DELETE_ACCOUNT) }
            )
        }
        composable(Routes.SETTINGS_NOTIFICATIONS) {
            NotificationSettingsScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Routes.SETTINGS_THEME) {
            ThemeScreen(
                currentTheme = "system",
                onThemeSelected = {},
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Routes.SETTINGS_LANGUAGE) {
            LanguageScreen(
                currentLanguage = "id",
                onLanguageSelected = {},
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Routes.SETTINGS_DELETE_ACCOUNT) {
            DeleteAccountScreen(
                onConfirmDelete = { navController.navigate(Routes.SETTINGS_ACCOUNT_DELETED) },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Routes.SETTINGS_ACCOUNT_DELETED) {
            AccountDeletedScreen(
                onExit = {
                    navController.navigate(Routes.ONBOARDING_SPLASH) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // ── Legal (KHQ 37-42) ──
        composable(Routes.LEGAL_ABOUT) {
            AboutScreen(versionName = "1.0.0", versionCode = 1, onNavigateBack = { navController.popBackStack() })
        }
        composable(Routes.LEGAL_TERMS) {
            TermsScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Routes.LEGAL_PRIVACY) {
            PrivacyPolicyScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Routes.LEGAL_GUIDELINES) {
            CommunityGuidelinesScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Routes.LEGAL_LICENSES) {
            LicensesScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Routes.LEGAL_CREDITS) {
            CreditsScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ── Support (KHQ 43-45) ──
        composable(Routes.SUPPORT_CONTACT) {
            ContactScreen(
                onNavigateBack = { navController.popBackStack() },
                onSubmitSupport = { navController.navigate(Routes.SUPPORT_FORM) }
            )
        }
        composable(Routes.SUPPORT_FORM) {
            SupportFormScreen(
                onNavigateBack = { navController.popBackStack() },
                onSubmit = { _, _ -> navController.navigate(Routes.SUPPORT_SENT) }
            )
        }
        composable(Routes.SUPPORT_SENT) {
            SupportSentScreen(
                onDone = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = false }
                    }
                }
            )
        }

        // ── System (KHQ 46, 49-52) ──
        composable(Routes.SYSTEM_OFFLINE) {
            OfflineScreen(
                onRetry = { navController.popBackStack() },
                onOpenDownloads = { navController.navigate(Routes.LIBRARY) }
            )
        }
        composable(Routes.SYSTEM_ERROR) {
            ErrorScreen(onRetry = { navController.popBackStack() })
        }
        composable(Routes.NOTIFICATIONS) {
            NotificationsScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Routes.NOTIFICATION_PERMISSION) {
            NotificationPermissionScreen(
                onAllow = { navController.popBackStack() },
                onDismiss = { navController.popBackStack() }
            )
        }

        // ── Auth Error Dialog (KHQ 12) ──
        composable(Routes.AUTH_ERROR) {
            AuthErrorDialog(
                errorMessage = null,
                onRetry = { navController.popBackStack() },
                onDismiss = { navController.popBackStack() }
            )
        }
    }
}
