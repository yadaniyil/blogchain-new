package com.yadaniil.blogchain.di.components

import android.app.Application
import com.yadaniil.blogchain.BlogchainApp
import com.yadaniil.blogchain.di.modules.ApplicationModule
import com.yadaniil.blogchain.di.modules.DatabaseModule
import com.yadaniil.blogchain.di.modules.MainActivityModule
import com.yadaniil.blogchain.di.modules.NetModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidInjectionModule::class,
            ApplicationModule::class,
            NetModule::class,
            DatabaseModule::class,
            MainActivityModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(blogchainApp: BlogchainApp)
}
