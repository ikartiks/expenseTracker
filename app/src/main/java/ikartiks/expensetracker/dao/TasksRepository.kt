package ikartiks.expensetracker.dao

import ikartiks.expensetracker.executors.AppExecutors
import ikartiks.expensetracker.entities.ViewTransactionDetails
import io.reactivex.Flowable
import ikartiks.expensetracker.entities.Account
import ikartiks.expensetracker.entities.TransactionType


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

    fun insertDefaultAccount(){

        val runnable = Runnable {
            appDao.insertAccount(Account(1,"Default Account"))
        }
        appExecutors.diskIO().execute(runnable)
    }

    fun insertDefaultTransactionType(){

        val runnable = Runnable {
            appDao.insertTransactionType(TransactionType(1, "fuel", "expense"))
        }
        appExecutors.diskIO().execute(runnable)
    }
}