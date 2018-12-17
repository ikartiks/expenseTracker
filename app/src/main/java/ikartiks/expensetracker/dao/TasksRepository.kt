package ikartiks.expensetracker.dao

import ikartiks.expensetracker.AppExecutors
import ikartiks.expensetracker.entities.ViewTransactionDetails

class TasksRepository(val appDao: AppDao, val appExecutors: AppExecutors) {

    interface LoadTasksCallback {

        fun onTasksLoaded(tasks: List<ViewTransactionDetails>)

        fun onDataNotAvailable()
    }


    fun getTransactionViewDetails(accounId:Int, loadTasksCallback:LoadTasksCallback){

        var list :List<ViewTransactionDetails>
        val runnable = Runnable {
            list = appDao.findTransactionDetails(accounId)
            appExecutors.mainThread().execute{
                if(list.size>0)
                    loadTasksCallback.onTasksLoaded(list)
                else
                    loadTasksCallback.onDataNotAvailable()
            }
        }
        appExecutors.diskIO().execute(runnable)
    }
}