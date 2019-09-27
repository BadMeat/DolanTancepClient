package com.dolan.dolantancepclient.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dolan.dolantancepclient.BuildConfig
import com.dolan.dolantancepclient.R
import com.dolan.dolantancepclient.movie.ResponseMovie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var movie: ResponseMovie
    private lateinit var viewModel: DetailViewModel

    private val detailItem = Observer<ResponseMovie> {
        if (it != null) {
            setUI(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.getDetailData().observe(this, detailItem)
        viewModel.getData(2287)
    }

    private fun setUI(e: ResponseMovie) {
        txt_title.text = e.title
        txt_descs.text = e.overview
        txt_rate.text = e.voteAverage.toString()
        txt_date.text = e.releaseDate
        Picasso.get().load("${BuildConfig.BASE_IMAGE}${e.posterPath}").into(img_poster)
    }


}
