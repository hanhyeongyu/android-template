package com.example.template

import android.app.Application
import com.example.template.core.log.timber.TimberHelper
import dagger.hilt.android.HiltAndroidApp



@HiltAndroidApp
class TemplateApp: Application() {
    override fun onCreate() {
        super.onCreate()

        TimberHelper.setupLogger()
    }
}