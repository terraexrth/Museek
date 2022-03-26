package com.example.musikkapp.fragments.player

import android.content.ComponentName
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.musikkapp.MusicService
import com.example.musikkapp.R
import com.example.musikkapp.fragments.home.Music
import com.example.musikkapp.fragments.home.MyAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import jp.wasabeef.glide.transformations.BlurTransformation
import java.lang.Exception

class PlayerFragment : Fragment(),ServiceConnection{
    companion object {
        var mediaPlayer: MediaPlayer? = null
        private lateinit var musicArrayList: ArrayList<Music>
        var musicService:MusicService? = null
    }

    private lateinit var mAuth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference
    private lateinit var postRef: DatabaseReference
    private lateinit var viewModel: PlayerViewModel
    private lateinit var runnable: Runnable
    private var handler = Handler()
    var seekbar:SeekBar? = null
    var cover: ImageView? = null
    var bg: ImageView? = null
    var name: String? = null
    var title_name: TextView? = null
    var artist_name: TextView? = null
    var playB: ImageView? = null
    var nextB: ImageView? = null
    var prevB: ImageView? = null
    var isPlaying: Boolean = false
    var songPos:Int = 0
    var pos:Int = 0

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
        nextB = root.findViewById(R.id.nextB) as ImageView
        prevB = root.findViewById(R.id.prevB) as ImageView
        musicArrayList = arrayListOf<Music>()
        seekbar = root.findViewById<SeekBar>(R.id.seekBar2)

        dbRef = FirebaseDatabase.getInstance().getReference("Music").child("New")

        dbRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {

                        val music = userSnapshot.getValue(Music::class.java)

                        musicArrayList.add(music!!)

                    }

                    var adapter = MyAdapter(musicArrayList)
                    name = arguments?.getString("name").toString()

                    var index = adapter.getArraylist().size -1
                    for(i in 0..index){
                        if(name.equals(adapter.getArraylist()[i].name)){
                            pos = i
                            //Toast.makeText(activity, "${adapter.getArraylist()[i].name} + $pos + $name", Toast.LENGTH_SHORT).show()

                        }
                    }
                    songPos+= pos

                    setLayout(adapter,songPos)

//                    activity?.let {
//                        Glide.with(it).load(adapter.getArraylist()[pos!!].coverUrl).into(cover!!)
//                        Glide.with(it).load(adapter.getArraylist()[pos!!].coverUrl).apply(
//                            RequestOptions.bitmapTransform(BlurTransformation(25, 3))).into(bg!!)
//                    }
//
//                    title_name!!.text = adapter.getArraylist()[pos!!].name
//                    artist_name!!.text = adapter.getArraylist()[pos!!].artist
                    createMediaPlayer(adapter, songPos)



                    playB!!.setOnClickListener {

                        if (isPlaying) {
                            pauseMusic()
                        } else {
                            playMusic()
                        }
                    }
                    nextB!!.setOnClickListener {
                        if(songPos>= musicArrayList.size-1){
                            songPos = 0
                            prevNextSong(adapter,increment = true, songPos)
                        }
                        else{
                            prevNextSong(adapter,increment = true, ++songPos)
                        }



                    }

                    prevB!!.setOnClickListener {
                        if(songPos!=0){
                        (prevNextSong(adapter,increment = false,--songPos))
                    }else{
                            songPos= (musicArrayList.size)-1
                            (prevNextSong(adapter,increment = false,songPos))
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

    private fun setLayout(adapter : MyAdapter,pos: Int) {
        activity?.let {
            Glide.with(it).load(adapter.getArraylist()[pos!!].coverUrl).into(cover!!)
            Glide.with(it).load(adapter.getArraylist()[pos!!].coverUrl).apply(
                RequestOptions.bitmapTransform(BlurTransformation(25, 3))
            ).into(bg!!)
        }

        title_name!!.text = adapter.getArraylist()[pos!!].name
        artist_name!!.text = adapter.getArraylist()[pos!!].artist

    }

    private fun createMediaPlayer(adapter: MyAdapter, pos: Int) {
        if(mediaPlayer!=null){
                mediaPlayer!!.stop();
                mediaPlayer!!.release();

        }
            mediaPlayer = MediaPlayer()
            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(adapter.getArraylist()[pos].songUrl)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()

            isPlaying = true
            playB!!.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24)
            seekbar!!.progress = 0
            seekbar!!.max = mediaPlayer!!.duration

            seekbar!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    if(p2){
                        mediaPlayer!!.seekTo(p1)
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }

            })

            runnable = Runnable {
                seekbar!!.progress = mediaPlayer!!.currentPosition
                handler.postDelayed(runnable,1000)

            }
            handler.postDelayed(runnable,1000)
            mediaPlayer!!.setOnCompletionListener {
                mediaPlayer!!.pause()

                playB!!.setImageResource(R.drawable.ic_baseline_play_circle_filled_24)
                if(songPos>= musicArrayList.size-1){
                    songPos = 0
                    prevNextSong(adapter,increment = true, songPos)
                }
                else{
                    prevNextSong(adapter,increment = true, ++songPos)
                }

            }



    }

    private fun playMusic() {
        playB!!.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24)
        isPlaying = true
        mediaPlayer!!.start()
    }

    private fun pauseMusic() {
        playB!!.setImageResource(R.drawable.ic_baseline_play_circle_filled_24)
        isPlaying = false

        mediaPlayer!!.pause()
    }
    private fun prevNextSong(adapter: MyAdapter,increment: Boolean, pos: Int){

        if(increment){
            setLayout(adapter,pos)
            createMediaPlayer(adapter,pos)
        }
        else{
            setLayout(adapter,pos)
            createMediaPlayer(adapter,pos)
        }
    }

    override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
        val binder = p1 as MusicService.MyBinder
        musicService = binder.currentService()
    }

    override fun onServiceDisconnected(p0: ComponentName?) {
        musicService = null
    }


}