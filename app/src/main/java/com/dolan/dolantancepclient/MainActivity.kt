package com.dolan.dolantancepclient

import android.content.Context
import android.content.Intent
import android.database.ContentObserver
import android.database.Cursor
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.dolan.dolantancepclient.DatabaseContract.Companion.CONTENT_URI
import com.dolan.dolantancepclient.detail.DetailActivity
import com.dolan.dolantancepclient.detail.DetailActivity.Companion.EXTRA_ID
import com.dolan.dolantancepclient.detail.DetailActivity.Companion.EXTRA_TYPE
import com.dolan.dolantancepclient.movie.MovieAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity(), FavoriteCallback {


    private lateinit var favAdapter: MovieAdapter

    private lateinit var threadHandler: HandlerThread
    private lateinit var myDataObserver: DataObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        favAdapter = MovieAdapter {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(EXTRA_TYPE, it?.type)
            intent.putExtra(EXTRA_ID, it?.id)
            startActivity(intent)
        }
        rv_main.layoutManager = GridLayoutManager(this, 2)
        rv_main.adapter = favAdapter

        threadHandler = HandlerThread("DataObserver")
        threadHandler.start()
        myDataObserver = DataObserver(this, Handler(threadHandler.looper))
        contentResolver?.registerContentObserver(CONTENT_URI, true, myDataObserver)
        LoadAsyn(baseContext, this).execute()
    }

    override fun postExecute(cursor: Cursor?) {
        if (cursor != null) {
            val favList = FavoriteMapper.cursorToArray(cursor)
            if (favList.isNotEmpty()) {
                favAdapter.setFavList(favList)
            } else {
                favAdapter.setFavList(mutableListOf())
                Toast.makeText(baseContext, getString(R.string.data_kosong), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    class LoadAsyn(
        context: Context? = null,
        callback: FavoriteCallback
    ) : AsyncTask<Void, Void, Cursor?>() {

        private val ctx = WeakReference(context)
        private val cb = WeakReference(callback)

        override fun doInBackground(vararg params: Void?): Cursor? {
            return ctx.get()?.contentResolver?.query(CONTENT_URI, null, null, null, null)
        }

        override fun onPostExecute(result: Cursor?) {
            super.onPostExecute(result)
            cb.get()?.postExecute(result)
        }
    }

    class DataObserver(private val context: Context?, handler: Handler) : ContentObserver(handler) {
        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            LoadAsyn(context, context as MainActivity).execute()
        }
    }
}
