package ikartiks.expensetracker

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ikartiks.expensetracker.entities.Repo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_retrofit_with_rx_kotlin.*
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class ActivityRetrofitWithRxKotlin : ActivityBase() {

    val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit_with_rx_kotlin)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val certificatePinner = CertificatePinner.Builder()
            .add("*.github.com", "sha256/y2HhTRXXLdmAF1esYBb/muQUl3BIBdmEB8jUvMrGc28=")
            .build()
        val okHttpClient = OkHttpClient.Builder()
            .certificatePinner(certificatePinner)
            .build()
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.github.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(RetrofitInterface::class.java)

        disposable.add(
            service.listRepos("ikartiks")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map( object : io.reactivex.functions.Function<ArrayList<Repo>,  ArrayList<Repo>> {

                    override fun apply(t: ArrayList<Repo>): ArrayList<Repo> {
                        Collections.sort(t, object : Comparator<Repo> {
                            override fun compare(n1: Repo, n2: Repo): Int {
                                return   n1.name!!.length - n2.name!!.length
                            }
                        })
                        // optionally filter here, since filter wont work
                        t.removeAt(0)
                        return t
                    }

                })
//                .filter(object: Predicate<List<Repo>>{
//                    override fun test(t: List<Repo>): Boolean {
//
//                    }
//                })
                .subscribeWith(object : DisposableSingleObserver<ArrayList<Repo>>() {
                    override fun onSuccess(notes: ArrayList<Repo>) {

//                        val b = StringBuilder()
//                        val i =0
//                        while (i<notes.size){
//                            b.append(notes.get(i).name!!+" \n")
//                        }
                        log(notes.toString())
                        showCustomMessage(notes.get(0).name!!)
                    }

                    override fun onError(e: Throwable) {
                        log("onError: " + e.message)
                    }
                })
        )
    }

    private fun fetchAllNotes() {

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}


