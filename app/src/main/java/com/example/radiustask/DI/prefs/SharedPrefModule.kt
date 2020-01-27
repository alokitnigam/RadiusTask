package com.example.radiustask.DI.prefs

import com.example.radiustask.AppConstants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPrefModule {


    @Provides
    @Singleton
    fun providePreferencesHelper(appPreferencesHelper: AppPreferencesHelper): PreferencesHelper {
        return appPreferencesHelper
    }

    @Provides
    @PreferenceInfo
    fun providePreferenceName(): String {
        return AppConstants.PREF_NAME
    }

}