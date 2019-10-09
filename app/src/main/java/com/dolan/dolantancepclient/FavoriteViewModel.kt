package com.dolan.dolantancepclient

import android.content.Context
import android.database.ContentObserver
import android.database.Cursor
import android.os.AsyncTask
import android.os.Handler
import android.os.HandlerThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

/**
 * Created by Bencoleng on 05/10/2019.
 */
class FavoriteViewModel : ViewModel(), FavoriteCallback {


    private val favList = MutableLiveData<List<Favorite>>()

    private lateinit var threadHandler: HandlerThread
    private lateinit var myDataObserver: DataObserver

    fun getData(context : Context){
        threadHandler = HandlerThread("DataObserver")
        threadHandler.start()
        myDataObserver = DataObserver(context, Handler(threadHandler.looper))
        context.contentResolver?.registerContentObserver(DatabaseContract.CONTENT_URI, true, myDataObserver)
        LoadAsyn(context, this).execute()
    }

    override fun postExecute(cursor: Cursor?) {
        if (cursor != null) {
            val fav = FavoriteMapper.cursorToArray(cursor)
            if (fav.isNotEmpty()) {
                favList.postValue(fav)
            }
        }
    }

    fun getFavList() = favList

    class LoadAsyn(
        context: Context? = null,
        callback: FavoriteCallback
    ) : AsyncTask<Void, Void, Cursor?>() {

        private val ctx = WeakReference(context)
        private val cb = WeakReference(callback)

        override fun doInBackground(vararg params: Void?): Cursor? {
            return ctx.get()?.contentResolver?.query(DatabaseContract.CONTENT_URI, null, null, null, null)
        }

        override fun onPostExecute(result: Cursor?) {
            super.onPostExecute(result)
            cb.get()?.postExecute(result)
        }
    }

    class DataObserver(private val context: Context?, handler: Handler) : ContentObserver(handler) {
        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            LoadAsyn(context,context as MainActivity).execute()
        }
    }
}