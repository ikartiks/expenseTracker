package ikartiks.expensetracker.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import ikartiks.expensetracker.dao.TasksRepository
import ikartiks.expensetracker.entities.ViewTransactionDetails
import io.reactivex.Flowable


class MainViewModel (val applicationX: Application, val tasksRepository : TasksRepository) : AndroidViewModel(applicationX) {



    // for this example to work, ideally getviews should also take a callback
    //based on that callback data should be updated on the view
    //note you can directly pass the callback from getViews to getTransactionViewDetails rather than creating multiple callbacks
    fun getViews(){

        tasksRepository.getTransactionViewDetails(1,object :TasksRepository.LoadTasksCallback{
            override fun onDataNotAvailable() {
            }

            override fun onTasksLoaded(tasks: List<ViewTransactionDetails>) {
                for (transaction in tasks){
                    Log.e("data"," "+ transaction.transactionDetailsAmount + " "+transaction.transactionDetailsAccountId
                            +" "+ transaction.transactionTypeName+" " +transaction.transactionTypeType)
                }
            }
        })
    }

//    fun getViewsObservable():Observable<ArrayList<ViewTransactionDetails>>{
//
//        val list= ArrayList<ArrayList<ViewTransactionDetails>>()
//        val observable = list.toObservable()
//        tasksRepository.getTransactionViewDetails(1,object :TasksRepository.LoadTasksCallback{
//            override fun onDataNotAvailable() {
//            }
//
//            override fun onTasksLoaded(tasks: List<ViewTransactionDetails>) {
//
//                list.add(tasks as ArrayList<ViewTransactionDetails>)
//
//
//            }
//        })
//        return observable
//    }

    fun insertDefaults(){
        tasksRepository.insertDefaultAccount()
        tasksRepository.insertDefaultTransactionType()
    }

    fun getViews( accountId:Int): Flowable<List<ViewTransactionDetails>> {
        return tasksRepository.getTransactionViewDetails(accountId)
    }

//    fun getViewsX( accountId:Int): Observable<ViewTransactionDetails> {
//
//        var list = tasksRepository.getTransactionViewDetails(1).
//        val observable = list.toObservable()
//        return observable
//    }

}

