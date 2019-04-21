package ikartiks.expensetracker.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ikartiks.expensetracker.dao.AppDatabase
import ikartiks.expensetracker.dao.TasksRepository
import ikartiks.expensetracker.executors.AppExecutors

@Module
class AppDatabaseModule {

    //since context is already being provided by another module, dagger automatically gets it from there
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideesTaskRepo(appDatabase: AppDatabase, appExecutors: AppExecutors):TasksRepository {
        return TasksRepository(appDatabase.appDao(), appExecutors)
    }
}