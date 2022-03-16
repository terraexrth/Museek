package com.example.musikkapp.fragments.player

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.musikkapp.R
import com.example.musikkapp.fragments.home.Music
import com.example.musikkapp.fragments.home.MyAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import jp.wasabeef.glide.transformations.BlurTransformation
import java.lang.Exception

class PlayerFragment : Fragment() {
    companion object{
        var mediaPlayer : MediaPlayer? = null
        private lateinit var musicArrayList: ArrayList<Music>
    }

    private lateinit var mAuth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference
    private lateinit var postRef: DatabaseReference
    private lateinit var viewModel: PlayerViewModel
    var cover: ImageView? = null
    var bg: ImageView? = null
    var pos: Int? = null
    var title_name: TextView? = null
    var artist_name: TextView? = null
    var playB: ImageView? = null
    var isPlaying : Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this).get(PlayerViewModel::class.java)
        val root = inflater.inflate(R.layout.player_fragment, container, false)
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        cover = root.findViewById(R.id.playerCover) as ImageView
        title_name = root.findViewById(R.id.title_player) as TextView
        artist_name = root.findViewById(R.id.artist_player) as TextView
        playB = root.findViewById(R.id.playB) as ImageView

        bg = root.findViewById(R.id.background) as ImageView
        musicArrayList = arrayListOf<Music>()


        dbRef = FirebaseDatabase.getInstance().getReference("Music").child("New")

        dbRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {

                        val music = userSnapshot.getValue(Music::class.java)

                        musicArrayList.add(music!!)

                    }

                    var adapter = MyAdapter(musicArrayList)
                    pos = arguments?.getInt("pos")

                    activity?.let {
                        Glide.with(it).load(adapter.getArraylist()[pos!!].coverUrl).into(cover!!)
                        Glide.with(it).load(adapter.getArraylist()[pos!!].coverUrl).apply(
                            RequestOptions.bitmapTransform(BlurTransformation(25, 3))).into(bg!!)
                    }

                    title_name!!.text = adapter.getArraylist()[pos!!].name
                    artist_name!!.text = adapter.getArraylist()[pos!!].artist
                    createMediaPlayer(adapter, pos!!)
                    playB!!.setOnClickListener{
                        if(isPlaying){
                            pauseMusic()
                        }else{
                            playMusic()
                        }
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })




        return root
    }
    private fun createMediaPlayer(adapter: MyAdapter,pos : Int){
        try{
            if(mediaPlayer == null) {
                mediaPlayer = MediaPlayer()
                mediaPlayer!!.reset()
                mediaPlayer!!.setDataSource(adapter.getArraylist()[pos!!].songUrl)
                mediaPlayer!!.prepare()
                mediaPlayer!!.start()
                isPlaying = true
                playB!!.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24)
            }
        }catch(e : Exception){return}
    }
    private fun playMusic(){
        playB!!.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24)
        isPlaying = true
        mediaPlayer!!.start()
    }
    private fun pauseMusic(){
        playB!!.setImageResource(R.drawable.ic_baseline_play_circle_filled_24)
        isPlaying = false
        mediaPlayer!!.pause()
    }




}