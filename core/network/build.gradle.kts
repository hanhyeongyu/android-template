plugins {
    alias(libs.plugins.template.android.library)
    alias(libs.plugins.template.hilt)

    id("kotlinx-serialization")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}


android {
    buildFeatures {
        buildConfig = true
    }
    namespace = "com.example.template.core.network"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

secrets {
    defaultPropertiesFileName = "secrets.defaults.properties"
}

dependencies {
    api(projects.core.common)


    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)


    implementation(libs.coil.kt)
    implementation(libs.coil.kt.svg)
}
