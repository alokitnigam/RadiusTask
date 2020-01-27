package com.example.radiustask.WorkManager

import androidx.work.WorkerFactory
import dagger.Binds
import dagger.Module

@Module
abstract class FactoryProvider {
    @Binds
    abstract fun bindWorkManagerFactory(viewModelFactory: MyWorkerFactory): WorkerFactory
}