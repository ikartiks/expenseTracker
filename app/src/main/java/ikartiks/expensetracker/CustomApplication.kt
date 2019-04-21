package ikartiks.expensetracker

import android.app.Application
import ikartiks.expensetracker.di.AppComponent
import ikartiks.expensetracker.di.ContextModule
import ikartiks.expensetracker.di.DaggerAppComponent

class CustomApplication: Application() {

    lateinit var appComponent:AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().contextModule(ContextModule(this))
            .build()


    }
}