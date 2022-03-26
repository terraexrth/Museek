package com.example.musikkapp.fragments.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musikkapp.R
import com.example.musikkapp.fragments.home.Music


class SearchAdapter(private val musicList: ArrayList<Music>) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    private lateinit var sListener: onSearchClickListener

    interface onSearchClickListener {
        fun onItemClick(position: Int)

    }

    fun setOnSearchClickListener(listener: onSearchClickListener){
        sListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.libary_item, parent, false)
        return SearchViewHolder(itemView,sListener)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val currentitem = musicList[position]
        Glide.with(holder.itemView.context).load(currentitem.coverUrl).into(holder.mcover)
        holder.mname.text = currentitem.name
        holder.martist.text = currentitem.artist
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    class SearchViewHolder(itemView: View,listener: onSearchClickListener) : RecyclerView.ViewHolder(itemView) {
        val mcover: ImageView = itemView.findViewById(R.id.cover_img)
        val mname: TextView = itemView.findViewById(R.id.titleSong)
        val martist: TextView = itemView.findViewById(R.id.artistSong)

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

    fun getArraylist(): ArrayList<Music> {
        return musicList
    }

}