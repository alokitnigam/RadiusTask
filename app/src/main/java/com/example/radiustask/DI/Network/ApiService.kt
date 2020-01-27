package com.example.radiustask.DI.Network

import com.example.radiustask.DI.Models.FacilitiyInfo
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @GET("db")
    fun getFacilities(): Single<Response<FacilitiyInfo>>



}

