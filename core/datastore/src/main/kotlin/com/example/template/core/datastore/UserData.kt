package com.example.template.core.datastore

data class UserData(
    val show_completed: Boolean,
    val themeBrand: ThemeBrand,
    val darkTheme: DarkTheme,
    val useDynamicColor: Boolean
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
