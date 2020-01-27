package com.example.radiustask.WorkManager

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import androidx.work.impl.utils.futures.SettableFuture
import com.example.radiustask.DI.Models.FacilitiyInfo
import com.example.radiustask.DI.Models.Facility
import com.example.radiustask.DI.Network.ApiService
import com.example.radiustask.DI.database.DbHelper
import com.google.common.util.concurrent.ListenableFuture
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.realm.RealmList
import retrofit2.Response
import javax.inject.Inject

class DataSyncListenableWorker constructor( private val context: Context,
     private val workerParams: WorkerParameters, private val db:DbHelper, private val service: ApiService)
    : ListenableWorker(context, workerParams) {

    var compositeDisposable = CompositeDisposable()
    private lateinit var mFuture: SettableFuture<Result>
    @SuppressLint("RestrictedApi")
    override fun startWork(): ListenableFuture<Result> {
        mFuture = SettableFuture.create()
        if (!isStopped) {
            getAlldata()
            Log.i("worker started", "startWork: ")
        }
        return mFuture
    }
    private fun getAlldata(){
        service.getFacilities()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(io.reactivex.schedulers.Schedulers.io())
            .subscribe(object : SingleObserver<Response<FacilitiyInfo>> {
                @SuppressLint("RestrictedApi")
                override fun onSuccess(t: Response<FacilitiyInfo>) {
                    Log.i("sucsess","sucsess saflfnaksfn asj aafvuk as ")
                    if (t.body()!=null){

                        db.addAllFacilities(prepareDataToSave(t.body()!!))
                        mFuture.set(Result.Success())



                    }
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.addAll(d)
                }

                @SuppressLint("RestrictedApi")
                override fun onError(e: Throwable) {
                    mFuture.set(Result.failure())
                }

            })
    }

    private fun prepareDataToSave(info: FacilitiyInfo): List<Facility> {
        for (item in info.exclusions){
            val id1 = item[0].options_id
            val id2 = item[1].options_id
            addExcludedItemsInFacilityOptions(id1,id2,info.facilities)
        }
        return info.facilities
    }

    private fun addExcludedItemsInFacilityOptions(
        id1: String?,
        id2: String?,
        facilities: List<Facility>
    ) {
        var savedIDs=0
        facilityLoop@ for (facility in facilities)
        {
            for(option in facility.options!!){

                val list = if (option.excludedOptionsIds.isNullOrEmpty())
                    RealmList<String>()
                else
                    option.excludedOptionsIds

                if (option.id==id1)
                {
                    list?.add(id2)
                    option.excludedOptionsIds=list
                    savedIDs++
                    continue@facilityLoop
                }
                else if (option.id==id2)
                {
                    list?.add(id1)
                    option.excludedOptionsIds=list
                    savedIDs++
                    continue@facilityLoop
                }

            }
            if (savedIDs==2)
                break
        }
    }

    override fun onStopped() {
        compositeDisposable.clear()
        super.onStopped()
    }



    class Factory @Inject constructor(
        val db: DbHelper,
        val networkService: ApiService
    ): ChildWorkerFactory {

        override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
            return DataSyncListenableWorker(appContext, params,db,networkService)
        }
    }

}

