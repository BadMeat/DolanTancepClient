package com.dolan.dolantancepclient.detail

import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dolan.dolantancepclient.BuildConfig
import com.dolan.dolantancepclient.DatabaseContract.Companion.CONTENT_URI
import com.dolan.dolantancepclient.DatabaseContract.Companion.getContentUriDelete
import com.dolan.dolantancepclient.MainActivity
import com.dolan.dolantancepclient.R
import com.dolan.dolantancepclient.getConvertDate
import com.dolan.dolantancepclient.movie.MovieViewModel
import com.dolan.dolantancepclient.movie.ResponseMovie
import com.dolan.dolantancepclient.network.TvViewModel
import com.dolan.dolantancepclient.tv.ResponseTv
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.*

class DetailActivity : AppCompatActivity() {

    /**
     * 1 Tv
     * 2 Movie
     */

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var tvViewModel: TvViewModel
    private var type = 1

    private var id: Int? = 0

    companion object {
        const val EXTRA_TYPE = "extra_type"
        const val EXTRA_ID = "extra_id"
    }

    private val tvItem = Observer<ResponseTv> {
        if (it != null) {
            setUI(1, it)
        }
    }

    private val movieItem = Observer<ResponseMovie> {
        if (it != null) {
            setUI(2, it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        id = intent.getIntExtra(EXTRA_ID, 0)

        val defaultLang = Locale.getDefault().displayLanguage
        var lang = "en-US"
        if (defaultLang.equals("Indonesia", true)) {
            lang = "id"
        }

        type = intent.getIntExtra(EXTRA_TYPE, 0)

        when (type) {
            1 -> {
                /**
                 * TV
                 */
                tvViewModel = ViewModelProviders.of(this).get(TvViewModel::class.java)
                tvViewModel.getTvList().observe(this, tvItem)
                tvViewModel.getData(id, lang)
            }
            2 -> {
                /**
                 * Movie
                 */
                movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
                movieViewModel.getDetailData().observe(this, movieItem)
                movieViewModel.getData(id, lang)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_add -> {
                deleteData()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun <T> setUI(type: Int, model: T) {
        when (type) {
            1 -> {
                val e = model as ResponseTv
                txt_title.text = e.name
                txt_descs.text = e.overview
                txt_rate.text = e.voteAverage.toString()
                txt_date.text = getConvertDate(e.firstAirDate)
                Picasso.get().load("${BuildConfig.BASE_IMAGE}${e.posterPath}").into(img_poster)
            }
            2 -> {
                val e = model as ResponseMovie
                txt_title.text = e.title
                txt_descs.text = e.overview
                txt_rate.text = e.voteAverage.toString()
                txt_date.text = getConvertDate(e.releaseDate)
                Picasso.get().load("${BuildConfig.BASE_IMAGE}${e.posterPath}").into(img_poster)
            }
        }
        progress_bar.visibility = View.INVISIBLE

    }

    private fun deleteData() {
        val alert = AlertDialog.Builder(this, R.style.AlertDialogCustom)
            .setTitle("Delete Data")
            .setCancelable(false)
            .setMessage(getString(R.string.ask_delete))
            .setPositiveButton("Ya") { _, _ ->
                contentResolver?.delete(getContentUriDelete("$id"), null, null)
                contentResolver?.notifyChange(
                    CONTENT_URI,
                    MainActivity.DataObserver(this, Handler())
                )
                Toast.makeText(
                    baseContext,
                    resources.getString(R.string.delete_success),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
            }
        val data = alert.create()
        data.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        when (type) {
            1 -> {
                tvViewModel.onDestroy()
            }
            2 -> {
                movieViewModel.onDestroy()
            }
        }
    }
}
