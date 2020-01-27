package com.example.radiustask.WorkManager

import androidx.work.ListenableWorker
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass
@Module
abstract class MyWorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(DataSyncListenableWorker::class)
    internal abstract fun bindMyWorkerFactory(worker: DataSyncListenableWorker.Factory): ChildWorkerFactory
}

@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class WorkerKey(val value: KClass<out ListenableWorker>)

