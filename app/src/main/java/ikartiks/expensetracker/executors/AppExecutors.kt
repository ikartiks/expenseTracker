package ikartiks.expensetracker.executors

import java.util.concurrent.Executor
import javax.inject.Inject

/**
 * Global executor pools for the whole application.
 *
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind
 * webservice requests).
 */
//private val networkIOExecutor: Executor
class AppExecutors @Inject constructor(
    private val diskIOExecutor: DiskIOThreadExecutor,
    private val mainThreadExecutor: MainThreadExecutor
) {

    fun diskIO(): Executor {
        return diskIOExecutor
    }

//    fun networkIO(): Executor {
//        return networkIOExecutor
//    }

    fun mainThread(): Executor {
        return mainThreadExecutor
    }
}
