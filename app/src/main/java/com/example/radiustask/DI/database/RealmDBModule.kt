package com.example.radiustask.DI.database

import android.app.Application
import android.content.Context
import com.example.radiustask.AppConstants
import dagger.Module
import dagger.Provides
import io.realm.BuildConfig
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject
import javax.inject.Singleton

@Module
class RealmDBModule {

    @Inject
    lateinit var context : Context

    @Provides
    @DatabaseInfo
    fun provideDatabaseName(): String? {
        return AppConstants.DB_NAME
    }
    @Provides
    @Singleton
    fun provideRealm(): Realm {

        return Realm.getDefaultInstance()
    }

    @Provides
    @Singleton
    fun provideDbHelper(appDbHelper: AppDbHelper): DbHelper {
        return appDbHelper
    }

    @Provides
    @Singleton
    @Inject
    fun realmConfiguration( @DatabaseInfo databaseName: String): RealmConfiguration {
        Realm.init(context.applicationContext)

        var builder: RealmConfiguration.Builder = RealmConfiguration.Builder()
        builder.name(databaseName)
        builder = builder.deleteRealmIfMigrationNeeded()
        return builder.build()
    }

}