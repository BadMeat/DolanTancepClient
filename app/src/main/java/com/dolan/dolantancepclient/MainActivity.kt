package com.dolan.dolantancepclient

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.ProviderInfo
import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.dolan.dolantancepclient.DatabaseContract.Companion.CONTENT_URI
import com.dolan.dolantancepclient.detail.DetailActivity
import com.dolan.dolantancepclient.detail.DetailActivity.Companion.EXTRA_ID
import com.dolan.dolantancepclient.detail.DetailActivity.Companion.EXTRA_TYPE
import com.dolan.dolantancepclient.movie.MovieAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FavoriteCallback {

    private lateinit var favAdapter: MovieAdapter
    private lateinit var favViewModel: FavoriteViewModel

    private val favObserver = Observer<List<Favorite>> {
        if (it != null) {
            favAdapter.setFavList(it)
        }
    }

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

        var isAvailableProvider = false

        for (pack: PackageInfo in packageManager.getInstalledPackages(PackageManager.GET_PROVIDERS)) {
            val providers = pack.providers
            if (providers != null) {
                for (provider: ProviderInfo in providers) {
                    if (provider.authority.equals(CONTENT_URI.authority, true)) {
                        isAvailableProvider = true
                        break
                    }
                }
            }
        }

        if (isAvailableProvider) {
            favViewModel = ViewModelProviders.of(this).get(FavoriteViewModel::class.java)
            favViewModel.getFavList().observe(this, favObserver)
            favViewModel.getData(this)

        } else {
            Toast.makeText(baseContext, getString(R.string.provider_info), Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun postExecute(cursor: Cursor?) {
        favViewModel.getData(this)
    }
}
