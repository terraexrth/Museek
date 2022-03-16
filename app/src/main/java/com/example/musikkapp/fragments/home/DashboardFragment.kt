package com.example.musikkapp.fragments.home

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musikkapp.Communicator
import com.example.musikkapp.R
import com.example.musikkapp.fragments.player.PlayerFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*





class DashboardFragment : Fragment() {

    private lateinit var communicator: Communicator
    private lateinit var dashboardViewmodel: DashboardViewModel
    private lateinit var mAuth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference
    private lateinit var musicRecRecyclerView: RecyclerView
    private lateinit var musicRecArrayList: ArrayList<Music>
    private lateinit var musicNewRecyclerView: RecyclerView
    private lateinit var musicNewArrayList: ArrayList<Music>
    var pos : Int? = null
    var rec1 : ImageView? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewmodel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        val mediaPlayer = MediaPlayer()

        musicRecRecyclerView = root.findViewById(R.id.musicList)
        musicRecRecyclerView.layoutManager = LinearLayoutManager(activity,
            RecyclerView.HORIZONTAL,false)

        musicRecRecyclerView.setHasFixedSize(true)

        musicRecArrayList = arrayListOf<Music>()
        getUserData()

        musicNewRecyclerView = root.findViewById(R.id.musicList2)
        musicNewRecyclerView.layoutManager = LinearLayoutManager(activity,RecyclerView.HORIZONTAL,false)
        musicNewRecyclerView.setHasFixedSize(true)

        musicNewArrayList = arrayListOf<Music>()
        communicator = activity as Communicator
        getNewData()




        return root
    }
    private fun getUserData(){
        dbRef = FirebaseDatabase.getInstance().getReference("Music").child("Recommend")

        dbRef.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){

                        val music = userSnapshot.getValue(Music::class.java)
                        musicRecArrayList.add(music!!)

                    }

                    //musicRecRecyclerView.adapter = MyAdapter(musicRecArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    private fun getNewData(){
        dbRef = FirebaseDatabase.getInstance().getReference("Music").child("New")

        dbRef.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){

                        val music = userSnapshot.getValue(Music::class.java)
                        musicNewArrayList.add(music!!)

                    }
                    var adapter = MyAdapter(musicNewArrayList)
                    musicNewRecyclerView.adapter = adapter


                    adapter.setOnItemClickListener(object : MyAdapter.onItemClickListener{

                        override fun onItemClick(position: Int) {

                            communicator.passDataCom(position)
                           // Toast.makeText(activity, "${adapter.getArraylist()[position].name}", Toast.LENGTH_SHORT).show()
//                            (activity as FragmentActivity).supportFragmentManager.beginTransaction()
//                                .replace(R.id.fragment_container, PlayerFragment()).addToBackStack(null)
//                                .commit()
                        }

                    })

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


}






