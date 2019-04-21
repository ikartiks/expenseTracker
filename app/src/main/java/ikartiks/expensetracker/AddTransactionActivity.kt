package ikartiks.expensetracker

import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.lifecycle.ViewModelProviders
import ikartiks.expensetracker.dao.AppDatabase
import ikartiks.expensetracker.dao.TasksRepository
import ikartiks.expensetracker.di.DaggerAppComponent
import ikartiks.expensetracker.entities.TransactionDetails
import ikartiks.expensetracker.executors.AppExecutors
import ikartiks.expensetracker.viewmodel.AddTransactionViewModel
import ikartiks.expensetracker.viewmodel.ViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_add_transaction.*
import kotlinx.android.synthetic.main.content_add_transaction.*
import javax.inject.Inject

class AddTransactionActivity : ActivityBase() {

    val disposable = CompositeDisposable()

    @Inject
    lateinit var tasksRepository: TasksRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)
        setSupportActionBar(toolbar)

        var app = application as CustomApplication
        app.appComponent.inject(this)

//        DaggerAppComponent.create().inject(this)
        //appExecutors = appComponent.getAppExecutor()

        //val db = AppDatabase.getInstance(this)
        //val tasksRepository = TasksRepository(appDatabase.appDao(), appExecutors)
        val factory = ViewModelFactory(application, tasksRepository)
        //val addViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // note we are calling get method on factory and not onCreate, so it will decide if
        // it wants to reuse old instance or create new using create method in our factory
        val viewModel = ViewModelProviders.of(this, factory).get(AddTransactionViewModel::class.java)


        fab.setOnClickListener { view ->
            run {

                view.visibility = View.GONE

                val details = TransactionDetails()
                details.amount = amount.text.toString().toInt()
                details.transactionTypeId = 1
                details.accountId = 1
                details.note = note.text.toString()

                disposable.add(viewModel.addTransaction(details)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        view.visibility = View.VISIBLE
                        Snackbar.make(view, "Expense added", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show()
                    })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}
