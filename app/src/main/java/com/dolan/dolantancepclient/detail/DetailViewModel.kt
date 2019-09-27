package com.dolan.dolantancepclient.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dolan.dolantancepclient.movie.ResponseMovie

class DetailViewModel : ViewModel(), DetailCallback {

    private val presenter = DetailPresenter(this)
    private val detailData = MutableLiveData<ResponseMovie>()

    fun getData(id: Int?, language: String? = "en-US") {
        presenter.getData(id, language)
    }

    override fun doFinaly() {

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