package ikartiks.expensetracker

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import ikartiks.expensetracker.adapter.GroupsRecyclerAdapter
import ikartiks.expensetracker.dao.AppDatabase
import ikartiks.expensetracker.dao.TasksRepository
import ikartiks.expensetracker.entities.ViewTransactionDetails
import ikartiks.expensetracker.viewmodel.MainViewModel
import ikartiks.expensetracker.viewmodel.ViewModelFactory
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : ActivityBase(), NavigationView.OnNavigationItemSelectedListener {


    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            startActivity(Intent(this,AddTransactionActivity::class.java))
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val db= AppDatabase.getInstance(this)
        val tasksRepository = TasksRepository(db.appDao(),AppExecutors())
        val factory = ViewModelFactory(application,tasksRepository)
        //val addViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // note we are calling get method on factory and not onCreate, so it will decide if
        // it wants to reuse old instance or create new using create method in our factory
        val viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
        //below line prints and i also get my logs for fuel data.
        //addViewModel.applicationX.let { log(application.toString()+" not null") }
        //addViewModel.getViews()

        val list = ArrayList<ViewTransactionDetails>()
        val adapter = GroupsRecyclerAdapter(list)
        recyclerView.adapter = adapter


        //note subscribe function returns disposable
        disposable.add(viewModel.getViews(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

                // if you just want diff of 2 lists, use DiffUtils to get delta
                //https://developer.android.com/reference/android/support/v7/util/DiffUtil
                list.clear()
                list.addAll(it)
                adapter.notifyDataSetChanged()
            })




//        viewModel.getViewsObservable()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe (getAnimalsObserver())


    }

//    fun getAnimalsObserver(): Observer<ArrayList<ViewTransactionDetails>> {
//        return object: Observer<ArrayList<ViewTransactionDetails>> {
//
//            override fun onSubscribe(d: Disposable) {
//                disposable.add(d)
//            }
//
//            override fun onNext(t: ArrayList<ViewTransactionDetails>) {
//
//            }
//
//            override fun onError(e: Throwable) {
//
//            }
//
//            override fun onComplete() {
//
//            }
//        };
//    }


    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
