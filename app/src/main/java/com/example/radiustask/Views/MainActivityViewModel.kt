package com.example.radiustask.Views

import android.util.Log
import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.radiustask.DI.Models.ExclusionCombinations
import com.example.radiustask.DI.Models.FacilitiyInfo
import com.example.radiustask.DI.Models.Facility
import com.example.radiustask.DI.Network.ApiService
import com.example.radiustask.DI.Network.NetworkState
import com.example.radiustask.DI.database.DbHelper
import com.example.radiustask.DI.database.LiveRealmData
import com.example.radiustask.DI.prefs.PreferencesHelper
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.realm.RealmList
import io.realm.RealmResults
import retrofit2.Response
import javax.inject.Inject


class MainActivityViewModel @Inject
constructor(private val service: ApiService, private val db: DbHelper,private val prefs:PreferencesHelper) : ViewModel() {
    var facilityList : LiveData<List<Facility>> = MutableLiveData()
    var exclusionsList : LiveData<List<ExclusionCombinations>> = MutableLiveData()
    var netWorkResponse: MutableLiveData<NetworkState> = MutableLiveData()
    var compositeDisposable = CompositeDisposable()

    init{
        if(!prefs.isSyncedForFirstTime){
            getData()
        }

    }

     fun getAllFaciitiesFromDB(): LiveData<List<Facility>> {
        val facilities:LiveRealmData<Facility> = db.allFacilities
        facilityList = Transformations.map(facilities,object: Function<RealmResults<Facility>, List<Facility>>{
            override fun apply(input: RealmResults<Facility>?): List<Facility> {
                val unmanagedList: List<Facility> = db.realm.copyFromRealm(input)
                return unmanagedList
            }


        })
        return facilityList
    }

    fun getAllExclusionsFromDB(): LiveData<List<ExclusionCombinations>> {
        val facilities:LiveRealmData<ExclusionCombinations> = db.allExclusionCombinations
        exclusionsList = Transformations.map(facilities,object: Function<RealmResults<ExclusionCombinations>, List<ExclusionCombinations>>{
            override fun apply(input: RealmResults<ExclusionCombinations>?): List<ExclusionCombinations> {
                val unmanagedList: List<ExclusionCombinations> = db.realm.copyFromRealm(input)
                return unmanagedList
            }


        })
        return exclusionsList
    }




    fun getData() {
        service.getFacilities()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(io.reactivex.schedulers.Schedulers.io())
            .subscribe(object : SingleObserver<Response<FacilitiyInfo>>{
                override fun onSuccess(t: Response<FacilitiyInfo>) {
                    Log.i("","")
                    if (t.body()!=null){

                        db.addAllFacilities(prepareDataToSave(t.body()!!))
                        prefs.syncedForFirstTime()
                        netWorkResponse.value = NetworkState(NetworkState.SUCESS,"Success")

                    }else{
                        netWorkResponse.value = NetworkState(NetworkState.FAILED,"Failed")

                    }
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.addAll(d)
                }

                override fun onError(e: Throwable) {
                    netWorkResponse.value = NetworkState(NetworkState.FAILED,e.localizedMessage)

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


    fun getNetworkState(): MutableLiveData<NetworkState> {
        return netWorkResponse

    }
    fun disposeAll(){
        compositeDisposable.dispose()
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}