package com.komikhq.app.navigation

/**
 * Exhaustive type-safe and string route contract definitions for all 62 KomikHQ screens.
 */
object Routes {
    // ----------------------------------------------------
    // Root & Bottom Nav Flow
    // ----------------------------------------------------
    const val MAIN = "main"
    const val HOME = "main/home"
    const val EXPLORE = "main/explore"
    const val LIBRARY = "main/library"

    // ----------------------------------------------------
    // Onboarding Flows (KHQ 01 – KHQ 04)
    // ----------------------------------------------------
    const val ONBOARDING_SPLASH = "onboarding/splash"         // KHQ 01
    const val ONBOARDING_WELCOME = "onboarding/welcome"       // KHQ 02
    const val ONBOARDING_INTRO = "onboarding/intro"           // KHQ 03
    const val ONBOARDING_GUEST = "onboarding/guest"           // KHQ 04

    // ----------------------------------------------------
    // Authentication & Password Management (KHQ 05 – 14, 58)
    // ----------------------------------------------------
    const val AUTH_SIGN_IN = "auth/sign-in"                   // KHQ 05, KHQ 58
    const val AUTH_SIGN_UP = "auth/sign-up"                   // KHQ 06
    const val AUTH_FORGOT_PASSWORD = "auth/forgot-password"   // KHQ 07
    const val AUTH_EMAIL_SENT = "auth/email-sent"             // KHQ 08
    const val AUTH_RESET_PASSWORD = "auth/reset-password"     // KHQ 09
    const val AUTH_VERIFY_OTP = "auth/verify-otp"             // KHQ 10
    const val AUTH_SUCCESS = "auth/success"                   // KHQ 11
    const val AUTH_ERROR = "auth/error"                       // KHQ 12
    const val AUTH_PASSWORD_SUCCESS = "auth/password-success" // KHQ 13
    const val AUTH_EXPIRED = "auth/expired"                   // KHQ 14

    // ----------------------------------------------------
    // Home & Discovery (Figma 2:23, KHQ 15, 47, 51, 52, 59)
    // ----------------------------------------------------
    const val HOME_EDITORIAL = "home/editorial"               // KHQ 15
    const val NOTIFICATIONS = "home/notifications"            // KHQ 51

    // ----------------------------------------------------
    // Search, Catalog & Filtering (Figma 2:28, KHQ 16 – 20, 53, 60 – 62)
    // ----------------------------------------------------
    const val CATALOG_TRENDING = "explore/trending"           // KHQ 60
    const val CATALOG_POPULAR = "explore/popular"             // KHQ 61
    const val CATALOG_COMPLETED = "explore/completed"         // KHQ 62

    // ----------------------------------------------------
    // Comic Detail & Reading Engine (Figma 2:33, 2:43, KHQ 55 – 57)
    // ----------------------------------------------------
    const val COMIC_DETAIL = "comic/{slug}"                   // Figma 2:33, KHQ 55
    const val COMIC_READER = "comic/{slug}/chapter/{chapterNumber}" // Figma 2:43, KHQ 56, 57

    fun comicDetail(slug: String): String = "comic/$slug"
    fun comicReader(slug: String, chapterNumber: String): String = "comic/$slug/chapter/$chapterNumber"

    // ----------------------------------------------------
    // Account, Profile & Settings (KHQ 21 – KHQ 36)
    // ----------------------------------------------------
    const val PROFILE = "profile"                             // KHQ 21, KHQ 22
    const val PROFILE_EDIT = "profile/edit"                   // KHQ 23
    const val PROFILE_AVATAR = "profile/avatar"               // KHQ 24
    const val PROFILE_INFO = "profile/info"                   // KHQ 25
    const val PROFILE_STATS = "profile/stats"                 // KHQ 26
    const val SETTINGS_READING = "settings/reading"           // KHQ 27
    const val SETTINGS_PASSWORD = "settings/password"         // KHQ 28
    const val SETTINGS_PRIVACY = "settings/privacy"           // KHQ 29
    const val SETTINGS_SECURITY = "settings/security"         // KHQ 30
    const val SETTINGS_NOTIFICATIONS = "settings/notifications" // KHQ 31
    const val SETTINGS_THEME = "settings/theme"               // KHQ 32
    const val SETTINGS_LANGUAGE = "settings/language"         // KHQ 33
    const val SETTINGS_DELETE_ACCOUNT = "settings/delete-account" // KHQ 35
    const val SETTINGS_ACCOUNT_DELETED = "settings/account-deleted" // KHQ 36

    // ----------------------------------------------------
    // Legal, Support & Feedback (KHQ 37 – KHQ 45, 49, 50)
    // ----------------------------------------------------
    const val LEGAL_ABOUT = "legal/about"                     // KHQ 37
    const val LEGAL_TERMS = "legal/terms"                     // KHQ 38
    const val LEGAL_PRIVACY = "legal/privacy"                 // KHQ 39
    const val LEGAL_GUIDELINES = "legal/guidelines"           // KHQ 40
    const val LEGAL_LICENSES = "legal/licenses"               // KHQ 41
    const val LEGAL_CREDITS = "legal/credits"                 // KHQ 42
    const val SUPPORT_CONTACT = "support/contact"             // KHQ 43
    const val SUPPORT_FORM = "support/form"                   // KHQ 44
    const val SUPPORT_SENT = "support/sent"                   // KHQ 45
    const val SYSTEM_OFFLINE = "system/offline"               // KHQ 49
    const val SYSTEM_ERROR = "system/error"                   // KHQ 50
    const val NOTIFICATION_PERMISSION = "system/notification-permission" // KHQ 52
}

