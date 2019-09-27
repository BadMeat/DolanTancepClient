package com.dolan.dolantancepclient.detail

import com.dolan.dolantancepclient.movie.ResponseMovie
import com.dolan.dolantancepclient.network.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DetailPresenter(
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
            ?.subscribe {
                if (it != null) {
                    item = it
                }
            }
    }

    fun onDestroy() {
        disposable?.dispose()
    }
}