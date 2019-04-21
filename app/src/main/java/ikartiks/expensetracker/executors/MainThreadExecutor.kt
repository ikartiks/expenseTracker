package ikartiks.expensetracker.executors

import android.os.Handler
import android.os.Looper
import androidx.annotation.NonNull
import java.util.concurrent.Executor
import javax.inject.Inject

class MainThreadExecutor @Inject constructor() : Executor {

    private val mainThreadHandler = Handler(Looper.getMainLooper())

    override fun execute(@NonNull command: Runnable) {
        mainThreadHandler.post(command)
    }
}