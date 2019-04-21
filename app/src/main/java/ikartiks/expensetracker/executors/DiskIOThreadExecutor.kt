package ikartiks.expensetracker.executors

import androidx.annotation.NonNull
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

class DiskIOThreadExecutor @Inject constructor() : Executor {

    private val mDiskIO: Executor

    init {
        mDiskIO = Executors.newSingleThreadExecutor()
    }

    override fun execute(@NonNull runnable: Runnable) {
        mDiskIO.execute(runnable)
    }
}