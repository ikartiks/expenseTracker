package ikartiks.expensetracker.di

import dagger.Component
import ikartiks.expensetracker.AddTransactionActivity
import ikartiks.expensetracker.MainActivity
import ikartiks.expensetracker.executors.AppExecutors

@Component(modules = arrayOf(ContextModule::class, AppDatabaseModule::class))
interface AppComponent {

    fun getAppExecutor(): AppExecutors


    fun inject(mainActivity: MainActivity)
    fun inject(addTransactionActivity: AddTransactionActivity)
}