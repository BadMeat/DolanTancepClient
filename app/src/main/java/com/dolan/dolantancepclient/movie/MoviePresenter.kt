package com.dolan.dolantancepclient.movie

import android.util.Log
import com.dolan.dolantancepclient.network.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MoviePresenter(
    private val detailCb: DetailCallback
) {

    var disposable: Disposable? = null

    fun getData(id: Int?, language: String?) {
        var item: ResponseMovie? = null
        disposable = ApiClient.getInstance().getMovie(id, language)
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
            ?.map {
                it.body()
            }
            ?.doFinally {
                detailCb.postExecute(item)
            }
            ?.subscribe(
                { result ->
                    if (result != null) {
                        item = result
                    }
                },
                { error -> Log.e("Error Response Movie", "$error") }
            )
    }

    fun onDestroy() {
        disposable?.dispose()
    }
}