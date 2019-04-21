package ikartiks.expensetracker.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ikartiks.expensetracker.dao.AppDatabase

@Module
class AppDatabaseModule {

    //since context is already being provided by another module, dagger automatically gets it from there
    @Provides
    fun provideAppDatabase(context:Context):AppDatabase{
        return AppDatabase.getInstance(context)
    }
}