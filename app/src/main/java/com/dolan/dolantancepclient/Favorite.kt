package com.dolan.dolantancepclient

import android.database.Cursor
import android.provider.BaseColumns._ID
import com.dolan.dolantancepclient.DatabaseContract.Companion.FAV_DATE
import com.dolan.dolantancepclient.DatabaseContract.Companion.FAV_POSTER
import com.dolan.dolantancepclient.DatabaseContract.Companion.FAV_RATE
import com.dolan.dolantancepclient.DatabaseContract.Companion.FAV_TITLE
import com.dolan.dolantancepclient.DatabaseContract.Companion.FAV_TYPE

data class Favorite(
    var id: Int? = 0,
    var title: String? = "TITLE",
    var date: String? = "DATE",
    var rate: Double? = 2.0,
    var poster: String? = "POSTER",
    var type: Int? = 1
) {
    constructor(cursor: Cursor?) : this() {
        id = DatabaseContract.getColumntInt(cursor, _ID)
        title = DatabaseContract.getColumntString(cursor, FAV_TITLE)
        date = DatabaseContract.getColumntString(cursor, FAV_DATE)
        rate = DatabaseContract.getColumntDouble(cursor, FAV_RATE)
        poster = DatabaseContract.getColumntString(cursor, FAV_POSTER)
        type = DatabaseContract.getColumntInt(cursor, FAV_TYPE)
    }
}