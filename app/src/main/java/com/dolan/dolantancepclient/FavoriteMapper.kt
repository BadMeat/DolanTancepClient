package com.dolan.dolantancepclient

import android.database.Cursor

class FavoriteMapper {
    companion object {
        fun cursorToArray(cursor: Cursor?): List<Favorite> {
            val favList = mutableListOf<Favorite>()
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val fav = Favorite(cursor)
                    favList.add(fav)
                }
            }
            return favList
        }
    }
}