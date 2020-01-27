package com.example.radiustask.Views

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.example.divumtask.Adapters.FacilitiesAdapter
import com.example.radiustask.Base.BaseActivity
import com.example.radiustask.DI.Models.Option
import com.example.radiustask.DI.Network.NetworkState
import com.example.radiustask.R
import com.example.radiustask.WorkManager.DataSyncListenableWorker
import com.example.radiustask.WorkManager.MyWorkerFactory
import io.realm.RealmList
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : BaseActivity<MainActivityViewModel>() {
    @Inject
    lateinit var mainActivityViewModel: MainActivityViewModel
    @Inject
    lateinit var facilitiesAdapter: FacilitiesAdapter
    @Inject
    lateinit var myWorkerFactory: MyWorkerFactory
    var currentExcludedOptions:RealmList<String> = RealmList<String>()

    var options:RealmList<Option> = RealmList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //initWorkMagager()
        setContentView(R.layout.activity_main)
        facilitiesrv.layoutManager= LinearLayoutManager(this)
        facilitiesrv.adapter = facilitiesAdapter
        facilitiesAdapter.setOnItemSelectedListener(object:FacilitiesAdapter.OnSpinnerItemSelectedListener{
            override fun onItemSelected(option: Option) {

                if(currentExcludedOptions.contains(option.id)){
                    Toast.makeText(this@MainActivity,"This Option cannot be selected",Toast.LENGTH_SHORT).show()
                }else{
                    for (id in option.excludedOptionsIds!!)
                        currentExcludedOptions.add(id)

                }

            }

        })

        // getContactList();

        setUpObservers()
    }

    private fun initWorkMagager() {
        WorkManager.initialize(
            this.applicationContext,
            Configuration.Builder()
                .setWorkerFactory(myWorkerFactory)
                .build()
        )
    }

    private fun setUpObservers() {
        getViewModel().getAllFaciitiesFromDB().observe(this, Observer {
           facilitiesAdapter.setList(it)
            it.map { facility ->  options.addAll(facility.options!!)}
            facilitiesAdapter.setOptions(options)

        })

        getViewModel().netWorkResponse.observe(this, Observer {
            if (it.status== NetworkState.SUCESS)
            {
                initWorkMagager()
                setWorkManager()
            }
        })

    }

    private fun setWorkManager() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicWorkRequest = PeriodicWorkRequest.Builder(
            DataSyncListenableWorker::class.java, 24, TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .build()


        WorkManager.getInstance(this).enqueue(periodicWorkRequest)
    }

    override fun layoutRes(): Int {
        return R.layout.activity_main
    }

    override fun getViewModel(): MainActivityViewModel {
        return mainActivityViewModel

    }
}
