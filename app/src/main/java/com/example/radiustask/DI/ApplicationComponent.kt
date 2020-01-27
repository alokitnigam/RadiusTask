package com.example.radiustask.DI

import android.app.Application
import com.example.radiustask.DI.Modules.ContextModule
import com.example.radiustask.DI.Modules.ActivityBindingModule
import com.example.radiustask.DI.Network.NetworkModule
import com.example.radiustask.DI.VMFactory.MyViewModelModule
import com.example.divumtask.DI.VMFactory.ViewModelFactoryModule
import com.example.radiustask.DI.database.RealmDBModule
import com.example.radiustask.DI.prefs.SharedPrefModule
import com.example.radiustask.WorkManager.FactoryProvider
import com.example.radiustask.WorkManager.MyWorkerModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class,AndroidSupportInjectionModule::class,
    ActivityBindingModule::class, NetworkModule::class,
    ViewModelFactoryModule::class, FactoryProvider::class, MyViewModelModule::class, RealmDBModule::class,SharedPrefModule::class,MyWorkerModule::class])
interface ApplicationComponent : AndroidInjector<DaggerApplication> {


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }

}