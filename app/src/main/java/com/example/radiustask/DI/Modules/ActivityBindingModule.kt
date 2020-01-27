package  com.example.radiustask.DI.Modules


import com.example.radiustask.Views.MainActivity
import com.example.radiustask.Views.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun contributesMainActivity(): MainActivity




}