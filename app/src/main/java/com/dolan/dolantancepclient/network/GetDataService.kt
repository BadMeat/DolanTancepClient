package com.dolan.dolantancepclient.network

import com.dolan.dolantancepclient.BuildConfig
import com.dolan.dolantancepclient.movie.ResponseMovie
import com.dolan.dolantancepclient.tv.ResponseTv
import com.jakewharton.retrofit2.adapter.rxjava2.Result
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetDataService {

    @GET("${BuildConfig.BASE_URL}movie/{id}?api_key=${BuildConfig.API_KEY}")
    fun getMovie(
        @Path("id") id: Int?,
        @Query("language") language: String? = "en-US"
    ): Observable<Response<ResponseMovie>?>

    @GET("${BuildConfig.BASE_URL}tv/{id}?api_key=${BuildConfig.API_KEY}")
    fun getTv(
        @Path("id") id: Int?,
        @Query("language") language: String? = "en-US"
    ): Observable<Response<ResponseTv>?>

}