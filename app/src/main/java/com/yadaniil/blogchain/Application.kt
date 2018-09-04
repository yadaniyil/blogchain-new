package com.yadaniil.blogchain

import com.yadaniil.blogchain.di.components.ApplicationComponent
import com.yadaniil.blogchain.di.modules.ApplicationModule
import com.yadaniil.blogchain.util.CrashReportTree
import timber.log.Timber
import android.app.Application
import com.facebook.stetho.Stetho
import com.yadaniil.blogchain.di.components.DaggerApplicationComponent

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)

        component = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportTree())
        }
    }

    companion object {

        var component: ApplicationComponent? = null
            private set
    }

}