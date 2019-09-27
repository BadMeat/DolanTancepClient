package com.dolan.dolantancepclient

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class FavoriteAdapter(private val listener: (Favorite?) -> Unit) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        return FavoriteHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_favorite,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = favList.size

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        holder.bindItem(favList[position], listener)
    }

    private val favList = mutableListOf<Favorite?>()

    fun setFavList(e: List<Favorite?>) {
        favList.clear()
        if (e.isNotEmpty()) {
            favList.addAll(e)
            notifyDataSetChanged()
        }
    }


    class FavoriteHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val txtTitle: TextView = view.findViewById(R.id.txt_title)
        private val txtDate: TextView = view.findViewById(R.id.txt_date)
        private val txtRate: TextView = view.findViewById(R.id.txt_rate)
        private val poster: ImageView = view.findViewById(R.id.img_poster)

        fun bindItem(e: Favorite?, listener: (Favorite?) -> Unit) {
            if (e != null) {
                txtTitle.text = e.title
                txtDate.text = e.date
                txtRate.text = e.rate.toString()
                Picasso.get().load("${BuildConfig.BASE_IMAGE}${e.poster}").into(poster)
                itemView.setOnClickListener {
                    listener(e)
                }
            }
        }

    }
}