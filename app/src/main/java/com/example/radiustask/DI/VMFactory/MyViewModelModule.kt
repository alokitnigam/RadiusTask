package com.example.radiustask.DI.VMFactory

import androidx.lifecycle.ViewModel
import com.example.divumtask.DI.VMFactory.ViewModelKey
import com.example.radiustask.Views.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MyViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewModel(myViewModel: MainActivityViewModel): ViewModel


}