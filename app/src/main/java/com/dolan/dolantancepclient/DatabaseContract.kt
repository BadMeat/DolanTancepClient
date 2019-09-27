package com.dolan.dolantancepclient

import android.database.Cursor
import android.net.Uri

class DatabaseContract {

    companion object {
        const val FAV_TABLE = "TABLE_FAV"
        const val ID = "_ID"
        const val FAV_TITLE = "FAV_TITLE"
        const val FAV_DATE = "FAV_DATE"
        const val FAV_RATE = "FAV_RATE"
        const val FAV_POSTER = "FAV_POSTER"
        const val FAV_TYPE = "FAV_TYPE"

        private const val AUTH = "com.dolan.dolantancepapp"
        private const val SCHEME = "content"

        var CONTENT_URI: Uri = Uri.Builder()
            .authority(AUTH)
            .scheme(SCHEME)
            .appendPath(FAV_TABLE)
            .build()

        fun getColumntInt(cursor: Cursor?, columnName: String?): Int? {
            return cursor?.getInt(cursor.getColumnIndexOrThrow(columnName))
        }

        fun getColumntString(cursor: Cursor?, columnName: String?): String? {
            return cursor?.getString(cursor.getColumnIndexOrThrow(columnName))
        }

        fun getColumntDouble(cursor: Cursor?, columnName: String?): Double? {
            return cursor?.getDouble(cursor.getColumnIndexOrThrow(columnName))
        }
    }

}