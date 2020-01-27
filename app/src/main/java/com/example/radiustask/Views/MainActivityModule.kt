package com.example.radiustask.Views

import com.example.divumtask.Adapters.FacilitiesAdapter
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    internal fun provideFacilitiesAdapter(): FacilitiesAdapter {
        return FacilitiesAdapter()
    }
}