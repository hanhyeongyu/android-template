import com.example.template.TemplateBuildType


plugins {
    alias(libs.plugins.template.android.application)
    alias(libs.plugins.template.android.application.compose)
    alias(libs.plugins.template.android.application.firebase)
    alias(libs.plugins.template.android.application.flavors)

    alias(libs.plugins.template.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.template"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.template"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug{
            applicationIdSuffix = TemplateBuildType.DEBUG.applicationIdSuffix
        }

        release {
            isMinifyEnabled = true
            applicationIdSuffix = TemplateBuildType.RELEASE.applicationIdSuffix


            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.log)
    implementation(projects.core.network)
    implementation(projects.core.composeutils)
    implementation(projects.core.database)
    implementation(projects.core.datastore)
    implementation(projects.core.datastoreProto)
    implementation(projects.core.notification)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    ksp(libs.hilt.compiler)
    kspTest(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    testImplementation(libs.hilt.android.testing)


    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}