package ikartiks.expensetracker.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import ikartiks.expensetracker.dao.TasksRepository
import ikartiks.expensetracker.entities.ViewTransactionDetails

class AddViewModel (application: Application) : AndroidViewModel(application) {

    var tasksRepository : TasksRepository? = null



    fun getViews(){

        tasksRepository?.getTransactionViewDetails(1,object :TasksRepository.LoadTasksCallback{

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