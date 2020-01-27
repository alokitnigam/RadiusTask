package com.example.divumtask.DI.VMFactory

import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkerFactory
import com.example.radiustask.DI.VMFactory.DaggerViewModelFactory
import com.example.radiustask.WorkManager.MyWorkerFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory


}