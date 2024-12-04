plugins{
    alias(libs.plugins.template.android.library)
    alias(libs.plugins.template.hilt)
}

android{
    buildFeatures {
        buildConfig = true
    }

    namespace = "com.example.template.core.log"
}


dependencies{
    api(libs.timber)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
}
