package com.dolan.dolantancepclient.detail

import com.dolan.dolantancepclient.movie.ResponseMovie

interface DetailCallback {

    fun doFinaly()

    fun postExecute(data: ResponseMovie?)

    fun onDestroy()
}