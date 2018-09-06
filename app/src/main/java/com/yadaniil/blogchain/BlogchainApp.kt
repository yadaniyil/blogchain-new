package com.yadaniil.blogchain

import android.app.Activity
import android.app.Application
import com.facebook.stetho.Stetho
import com.yadaniil.blogchain.di.AppInjector
import com.yadaniil.blogchain.util.CrashReportTree
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

open class BlogchainApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
        else
            Timber.plant(CrashReportTree())

        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector

}