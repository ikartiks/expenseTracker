package ikartiks.expensetracker.dao

import ikartiks.expensetracker.AppExecutors
import ikartiks.expensetracker.entities.ViewTransactionDetails
import io.reactivex.Flowable
import android.content.ClipData.Item



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
                if(list.isNotEmpty())
                    loadTasksCallback.onTasksLoaded(list)
                else
                    loadTasksCallback.onDataNotAvailable()
            }
        }
        appExecutors.diskIO().execute(runnable)
    }

    fun getTransactionViewDetails(accounId:Int): Flowable<List<ViewTransactionDetails>>{



        return appDao.findTransactionDetailsFlowable(accounId)
        //val abc = appDao.findTransactionDetailsFlowable(accounId)
        //return abc.blockingFirst()
    }
}