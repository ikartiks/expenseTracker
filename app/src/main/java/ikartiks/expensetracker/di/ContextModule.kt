package ikartiks.expensetracker.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ContextModule(val context:Context) {

    @Provides
    fun providerContext():Context{
        return  context
    }
}