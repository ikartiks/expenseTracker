package ikartiks.expensetracker.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import ikartiks.expensetracker.dao.TasksRepository
import ikartiks.expensetracker.entities.ViewTransactionDetails

class AddViewModel (val applicationX: Application,val tasksRepository : TasksRepository) : AndroidViewModel(applicationX) {


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
}