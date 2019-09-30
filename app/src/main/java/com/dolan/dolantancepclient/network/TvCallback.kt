package com.dolan.dolantancepclient.network

import com.dolan.dolantancepclient.tv.ResponseTv

/**
 * Created by Bencoleng on 28/09/2019.
 */
interface TvCallback {
    fun postExecute(tv: ResponseTv?)
    fun onDestroy()
}