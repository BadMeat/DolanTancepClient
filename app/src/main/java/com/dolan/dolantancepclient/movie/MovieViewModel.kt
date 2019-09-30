package com.dolan.dolantancepclient.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieViewModel : ViewModel(), DetailCallback {

    private val presenter = MoviePresenter(this)
    private val detailData = MutableLiveData<ResponseMovie>()

    fun getData(id: Int?, language: String? = "en-US") {
        presenter.getData(id, language)
    }

    override fun postExecute(data: ResponseMovie?) {
        if (data != null) {
            detailData.postValue(data)
        }
    }

    override fun onDestroy() {
        presenter.onDestroy()
    }

    fun getDetailData() = detailData


}