package com.komikhq.app.feature.legal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.komikhq.app.R
import com.komikhq.app.core.designsystem.HQTheme
import com.komikhq.app.core.designsystem.InstrumentSansFamily
import com.komikhq.app.core.designsystem.InterFamily
import com.komikhq.app.core.ui.HQLogo
import com.komikhq.app.core.ui.LogoSize
import com.komikhq.app.core.ui.LogoVariant
import com.komikhq.app.feature.settings.SettingsScaffold

/**
 * KHQ 37 · About KomikHQ Screen
 */
@Composable
fun AboutScreen(
    versionName: String,
    versionCode: Int,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    SettingsScaffold(
        title = stringResource(R.string.legal_about_title),
        onNavigateBack = onNavigateBack,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(vertical = 16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                HQLogo(variant = LogoVariant.MARK, size = LogoSize.LARGE)
                Spacer(modifier = Modifier.height(12.dp))
                HQLogo(variant = LogoVariant.WORDMARK, size = LogoSize.HEADER)
                Spacer(modifier = Modifier.height(8.dp))
                BasicText(
                    text = stringResource(R.string.legal_about_version, versionName, versionCode),
                    style = TextStyle(
                        fontFamily = InterFamily,
                        fontSize = 13.sp,
                        color = HQTheme.colors.secondaryText
                    )
                )
                Spacer(modifier = Modifier.height(6.dp))
                BasicText(
                    text = stringResource(R.string.brand_tagline),
                    style = TextStyle(
                        fontFamily = InterFamily,
                        fontSize = 14.sp,
                        color = HQTheme.colors.secondaryText
                    )
                )
            }
        }
    }
}

/**
 * KHQ 38 · Terms of Service Screen
 */
@Composable
fun TermsScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    LegalDocScreen(
        title = stringResource(R.string.legal_terms_title),
        onNavigateBack = onNavigateBack,
        modifier = modifier
    )
}

/**
 * KHQ 39 · Privacy Policy Screen
 */
@Composable
fun PrivacyPolicyScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    LegalDocScreen(
        title = stringResource(R.string.legal_privacy_title),
        onNavigateBack = onNavigateBack,
        modifier = modifier
    )
}

/**
 * KHQ 40 · Community Guidelines Screen
 */
@Composable
fun CommunityGuidelinesScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    LegalDocScreen(
        title = stringResource(R.string.legal_community_title),
        onNavigateBack = onNavigateBack,
        modifier = modifier
    )
}

/**
 * KHQ 41 · Open-Source Licenses Screen
 */
@Composable
fun LicensesScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    LegalDocScreen(
        title = stringResource(R.string.legal_licenses_title),
        onNavigateBack = onNavigateBack,
        modifier = modifier
    )
}

/**
 * KHQ 42 · Credits & Contributors Screen
 */
@Composable
fun CreditsScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    LegalDocScreen(
        title = stringResource(R.string.legal_credits_title),
        onNavigateBack = onNavigateBack,
        modifier = modifier
    )
}

/**
 * Reusable legal document scaffold (placeholder body for dynamic content loading)
 */
@Composable
private fun LegalDocScreen(
    title: String,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    SettingsScaffold(
        title = title,
        onNavigateBack = onNavigateBack,
        modifier = modifier
    ) {
        // Legal document content loaded from server or assets
        BasicText(
            text = title,
            style = TextStyle(
                fontFamily = InstrumentSansFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = HQTheme.colors.text
            )
        )
        Spacer(modifier = Modifier.height(12.dp))
        // Placeholder for dynamic legal document loading
        com.komikhq.app.core.ui.HQCircularProgressIndicator(
            color = com.komikhq.app.core.designsystem.HQOrange,
            size = 32.dp,
            strokeWidth = 3.dp
        )
    }
}

