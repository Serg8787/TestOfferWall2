package com.tsybulnik.testofferwall2.network

import com.tsybulnik.testofferwall2.model.DataList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServise {
    @GET("entities/getAllIds")
    fun getDataList(): Call<DataList>

    @GET("object/{id}")
    fun getView(@Path("id") id:Int): Call<Any>
}