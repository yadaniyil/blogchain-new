package com.yadaniil.blogchain.di.modules

import com.yadaniil.blogchain.MainActivity
import com.yadaniil.blogchain.di.FragmentBuildersModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
}