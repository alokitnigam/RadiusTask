package com.example.radiustask.Base


import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.radiustask.AppConstants
import com.example.radiustask.DI.ApplicationComponent
import com.example.radiustask.DI.DaggerApplicationComponent
import com.example.radiustask.DI.database.DbHelper
import com.example.radiustask.WorkManager.MyWorkerFactory
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.realm.BuildConfig
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject


class  MyApplication : DaggerApplication() {

//    @Inject
//    lateinit var myWorkerFactory: MyWorkerFactory
//    @Inject
//    lateinit var dbHelper: DbHelper

    lateinit var component: ApplicationComponent


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        component = DaggerApplicationComponent.builder().application(this).build()
        component.inject( this)
        return component
    }

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        var builder: RealmConfiguration.Builder = RealmConfiguration.Builder()
        builder.name(AppConstants.DB_NAME)
        if (BuildConfig.DEBUG) {
            builder = builder.deleteRealmIfMigrationNeeded()
        }
//
//        WorkManager.initialize(
//            this,
//            Configuration.Builder()
//                .setWorkerFactory(myWorkerFactory)
//                .build()
//        )

        Realm.setDefaultConfiguration(builder.build())

        Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build())
        }


}
