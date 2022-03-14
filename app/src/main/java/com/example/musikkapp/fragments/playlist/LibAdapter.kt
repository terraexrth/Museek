package com.example.musikkapp.fragments.playlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musikkapp.R
import com.example.musikkapp.fragments.home.Music

class LibAdapter(private val musicList : ArrayList<Music>) : RecyclerView.Adapter<LibAdapter.LibViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.libary_item,parent,false)
        return LibViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LibViewHolder, position: Int) {
        val currentitem = musicList[position]
        Glide.with(holder.itemView.context).load(currentitem.coverUrl).into(holder.mcover)
        holder.mname.text = currentitem.name
        holder.martist.text = currentitem.artist
    }

    override fun getItemCount(): Int {

        return musicList.size

    }
    class  LibViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val mcover : ImageView = itemView.findViewById(R.id.cover_img)
        val mname : TextView = itemView.findViewById(R.id.titleSong)
        val martist : TextView = itemView.findViewById(R.id.artistSong)


    }

}