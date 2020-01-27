package com.example.radiustask.DI.Modules

import android.app.Application
import android.content.Context
import com.example.radiustask.DI.prefs.AppPreferencesHelper
import com.example.radiustask.DI.prefs.PreferencesHelper

import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class ContextModule {

    @Binds
    internal abstract fun provideContext(application: Application): Context
}
