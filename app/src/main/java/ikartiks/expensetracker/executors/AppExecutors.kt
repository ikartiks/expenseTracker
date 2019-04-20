package ikartiks.expensetracker.executors

import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Global executor pools for the whole application.
 *
 *
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind
 * webservice requests).
 */
class AppExecutors constructor(
    private val diskIOExecutor: Executor, private val networkIOExecutor: Executor,
    private val mainThreadExecutor: Executor
) {
    constructor() : this(
        DiskIOThreadExecutor(), Executors.newFixedThreadPool(THREAD_COUNT),
        MainThreadExecutor()
    )

    fun diskIO(): Executor {
        return diskIOExecutor
    }

    fun networkIO(): Executor {
        return networkIOExecutor
    }

    fun mainThread(): Executor {
        return mainThreadExecutor
    }

    companion object {
        private val THREAD_COUNT = 3
    }
}
