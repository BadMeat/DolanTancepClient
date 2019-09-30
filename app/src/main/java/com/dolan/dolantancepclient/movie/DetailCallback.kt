package com.dolan.dolantancepclient.movie

interface DetailCallback {

    fun postExecute(data: ResponseMovie?)

    fun onDestroy()
}