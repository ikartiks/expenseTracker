package ikartiks.expensetracker.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import ikartiks.expensetracker.dao.TasksRepository
import ikartiks.expensetracker.entities.TransactionDetails
import ikartiks.expensetracker.entities.ViewTransactionDetails
import io.reactivex.Completable
import io.reactivex.Flowable

class AddTransactionViewModel (val applicationX: Application, val tasksRepository : TasksRepository) : AndroidViewModel(applicationX) {



    fun addTransaction(transactionDetails: TransactionDetails): Completable {
        return Completable.fromAction {

            tasksRepository.appDao.insertTransactionDetails(transactionDetails)
        }
    }
}