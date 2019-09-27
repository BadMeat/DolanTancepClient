package com.dolan.dolantancepclient.network

import com.dolan.dolantancepclient.BuildConfig
import com.dolan.dolantancepclient.movie.ResponseMovie
import com.dolan.dolantancepclient.tv.ResponseTv
import com.jakewharton.retrofit2.adapter.rxjava2.Result
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    private var dataService: GetDataService? = null

    companion object {
        var api: ApiClient? = null
        fun getInstance(): ApiClient {
            if (api == null) {
                api = ApiClient()
            }
            return api as ApiClient
        }
    }

    init {
        val retro = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        dataService = retro.create(GetDataService::class.java)
    }

    fun getMovie(id: Int?, language: String?): Observable<Response<ResponseMovie>?>? {
        return dataService?.getMovie(id, language)
    }

    fun getTv(id: Int?, language: String?): Observable<Response<ResponseTv>?>? {
        return dataService?.getTv(id, language)
    }
}