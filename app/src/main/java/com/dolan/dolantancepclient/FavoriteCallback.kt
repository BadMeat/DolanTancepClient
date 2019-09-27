package com.dolan.dolantancepclient

import android.database.Cursor

interface FavoriteCallback {
    fun postExecute(cursor: Cursor?)
}