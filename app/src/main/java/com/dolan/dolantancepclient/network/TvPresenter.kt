package com.dolan.dolantancepclient.network

import android.util.Log
import com.dolan.dolantancepclient.tv.ResponseTv
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Bencoleng on 28/09/2019.
 */
class TvPresenter(
    private val cb: TvCallback
) {

    private var disposable: Disposable? = null

    fun getData(id: Int?, language: String?) {

        var tvItem: ResponseTv? = null

        disposable = ApiClient.getInstance().getTv(id, language)
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
            ?.map {
                it.body()
            }
            ?.doFinally {
                cb.postExecute(tvItem)
            }
            ?.subscribe(
                { result -> tvItem = result },
                { error -> Log.e("Error Reponse TV", "$error") }
            )
    }

    fun close() {
        disposable?.dispose()
    }

}