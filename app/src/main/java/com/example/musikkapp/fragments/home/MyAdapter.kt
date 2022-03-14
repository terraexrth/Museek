package com.example.musikkapp.fragments.home

import android.content.ContentValues.TAG
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musikkapp.MainActivity
import com.example.musikkapp.R
import com.example.musikkapp.fragments.upload.UploadFragment
import java.io.IOException
import java.util.ArrayList

class MyAdapter(private val musicList : ArrayList<Music>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.music_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = musicList[position]
        val mediaPlayer = MediaPlayer()


        Glide.with(holder.itemView.context).load(currentitem.coverUrl).into(holder.mcover)

        holder.mname.text = currentitem.name
        holder.martist.text = currentitem.artist

       /* holder.itemView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
               // val activity = p0!!.context as AppCompatActivity
               // var uploadFragment = UploadFragment()
               // activity.supportFragmentManager.beginTransaction().replace(R.id.fragment_container,uploadFragment).addToBackStack(null).commit()
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
                try {
                    mediaPlayer.setDataSource(currentitem.songUrl)
                    mediaPlayer.prepare()
                    mediaPlayer.start()


                } catch (e: IOException) {
                    e.printStackTrace()
                }
                Log.v(TAG,"Music is streaming")
            }

        })*/

    }



    override fun getItemCount(): Int {

        return musicList.size
    }

    class  MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val mcover : ImageView = itemView.findViewById(R.id.cover_img)
        val mname : TextView = itemView.findViewById(R.id.tvMusicName)
        val martist : TextView = itemView.findViewById(R.id.tvArtist)


    }



}
