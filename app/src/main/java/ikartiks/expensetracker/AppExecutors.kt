package ikartiks.expensetracker

import android.os.Handler
import android.os.Looper
import androidx.annotation.NonNull
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Global executor pools for the whole application.
 *
 *
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind
 * webservice requests).
 */
class AppExecutors

    internal constructor(private val diskIO: Executor, private val networkIO: Executor,
                     private val mainThread: Executor) {

    constructor() : this(
        DiskIOThreadExecutor(), Executors.newFixedThreadPool(THREAD_COUNT),
        MainThreadExecutor()
    ) {
    }

    fun diskIO(): Executor {
        return diskIO
    }

    fun networkIO(): Executor {
        return networkIO
    }

    fun mainThread(): Executor {
        return mainThread
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(@NonNull command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

    companion object {

        private val THREAD_COUNT = 3
    }

    class DiskIOThreadExecutor : Executor {

        private val mDiskIO: Executor

        init {
            mDiskIO = Executors.newSingleThreadExecutor()
        }

        override fun execute(@NonNull runnable: Runnable) {
            mDiskIO.execute(runnable)
        }
    }
}
