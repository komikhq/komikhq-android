package com.komikhq.app.ui.auth

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun OAuthWebViewDialog(
    provider: String = "google",
    onDismiss: () -> Unit,
    onSuccess: (token: String) -> Unit
) {
    var isLoading by remember { mutableStateOf(true) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Header bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Tutup",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                Text(
                    text = "Masuk dengan Google",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f)
                )
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 2.dp
                    )
                }
            }

            // WebView Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                AndroidView(
                    factory = { ctx ->
                        WebView(ctx).apply {
                            settings.javaScriptEnabled = true
                            settings.domStorageEnabled = true
                            settings.userAgentString = settings.userAgentString.replace("; wv", "") // avoid restricted webview UA
                            settings.setSupportMultipleWindows(false)

                            val cookieManager = CookieManager.getInstance()
                            cookieManager.setAcceptCookie(true)
                            cookieManager.setAcceptThirdPartyCookies(this, true)

                            webViewClient = object : WebViewClient() {
                                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                                    isLoading = true
                                    checkCookiesAndIntercept(url, onSuccess)
                                }

                                override fun onPageFinished(view: WebView?, url: String?) {
                                    isLoading = false
                                    checkCookiesAndIntercept(url, onSuccess)
                                }

                                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                                    val url = request?.url?.toString()
                                    return if (checkCookiesAndIntercept(url, onSuccess)) {
                                        true
                                    } else {
                                        super.shouldOverrideUrlLoading(view, request)
                                    }
                                }

                                private fun checkCookiesAndIntercept(url: String?, onSuccess: (String) -> Unit): Boolean {
                                    if (url == null) return false

                                    // If redirected back to komikhq domain
                                    if (url.startsWith("https://komikhq.com") || url.startsWith("https://api.komikhq.com/v1/auth/callback")) {
                                        val cookies = cookieManager.getCookie("https://komikhq.com")
                                            ?: cookieManager.getCookie("https://api.komikhq.com")
                                            ?: ""

                                        val token = extractTokenFromCookies(cookies)
                                        if (token != null) {
                                            onSuccess(token)
                                            return true
                                        }
                                    }
                                    return false
                                }

                                private fun extractTokenFromCookies(cookies: String): String? {
                                    val parts = cookies.split(";")
                                    for (part in parts) {
                                        val trimmed = part.trim()
                                        if (trimmed.startsWith("komikhq.session_token=") ||
                                            trimmed.startsWith("better-auth.session_token=")
                                        ) {
                                            val value = trimmed.substringAfter("=")
                                            if (value.isNotBlank()) return value
                                        }
                                    }
                                    return null
                                }
                            }

                            // Load the bootstrap page directly into WebView so cookies are stored in WebView
                            val html = """
                                <!DOCTYPE html>
                                <html>
                                <head>
                                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                    <style>
                                        body { background: #0C0A09; color: #FBFAF9; display: flex; align-items: center; justify-content: center; height: 100vh; margin: 0; font-family: sans-serif; }
                                    </style>
                                </head>
                                <body>
                                    <p>Menghubungkan ke Google...</p>
                                    <script>
                                        fetch('https://api.komikhq.com/v1/auth/sign-in/social', {
                                            method: 'POST',
                                            headers: { 'Content-Type': 'application/json' },
                                            credentials: 'include',
                                            body: JSON.stringify({
                                                provider: '$provider',
                                                callbackURL: 'https://komikhq.com'
                                            })
                                        })
                                        .then(res => res.json())
                                        .then(data => {
                                            if (data.url) {
                                                window.location.href = data.url;
                                            } else {
                                                document.body.innerHTML = '<p>Gagal memulai autentikasi.</p>';
                                            }
                                        })
                                        .catch(err => {
                                            document.body.innerHTML = '<p>Gagal: ' + err.message + '</p>';
                                        });
                                    </script>
                                </body>
                                </html>
                            """.trimIndent()

                            loadDataWithBaseURL("https://api.komikhq.com", html, "text/html", "UTF-8", null)
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
