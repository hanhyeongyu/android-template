package com.example.template.core.datastore

data class UserData(
    val show_completed: Boolean,
    val themeBrand: ThemeBrand,
    val darkThemeConfig: DarkTheme
)

enum class ThemeBrand {
    DEFAULT,
    ANDROID,
}

enum class DarkTheme {
    FOLLOW_SYSTEM,
    LIGHT,
    DARK,
}
