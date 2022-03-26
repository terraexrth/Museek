package com.example.musikkapp.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musikkapp.R
import com.example.musikkapp.fragments.player.PlayerFragment
import com.example.musikkapp.fragments.playlist.LibaryFragment
import com.example.musikkapp.fragments.upload.UploadFragment


class MyAdapter(private val musicList : ArrayList<Music>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>(){
    private  lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(listener: onItemClickListener){

        mListener = listener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.music_item,parent,false)
        return MyViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = musicList[position]
        val playerFragment = PlayerFragment()

    Glide.with(holder.itemView.context).load(currentitem.coverUrl).into(holder.mcover)

        holder.mname.text = currentitem.name
        holder.martist.text = currentitem.artist

       /* holder.mcover.setOnClickListener{


            (holder.itemView.getContext() as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PlayerFragment())
                .commit()

        }*/


    }



    override fun getItemCount(): Int {

        return musicList.size
    }

    class  MyViewHolder(itemView: View, listener: onItemClickListener):RecyclerView.ViewHolder(itemView){
        val mcover : ImageView = itemView.findViewById(R.id.cover_img)
        val mname : TextView = itemView.findViewById(R.id.tvMusicName)
        val martist : TextView = itemView.findViewById(R.id.tvArtist)

        init{
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)

            }

        }
    }

    fun getArraylist(): ArrayList<Music> {
        return musicList
    }


}

