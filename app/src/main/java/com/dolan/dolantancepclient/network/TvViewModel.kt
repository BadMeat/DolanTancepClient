package com.dolan.dolantancepclient.network

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dolan.dolantancepclient.tv.ResponseTv

/**
 * Created by Bencoleng on 28/09/2019.
 */
class TvViewModel : ViewModel(), TvCallback {

    private val tvPresenter = TvPresenter(this)

    private val tvList = MutableLiveData<ResponseTv>()

    fun getTvList() = tvList

    override fun postExecute(tv: ResponseTv?) {
        tvList.postValue(tv)
    }

    override fun onDestroy() {
        tvPresenter.close()
    }

    fun getData(id: Int?, language: String? = "en-US") {
        tvPresenter.getData(id, language)
    }
}