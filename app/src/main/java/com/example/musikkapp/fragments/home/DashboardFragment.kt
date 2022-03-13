package com.example.musikkapp.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musikkapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class DashboardFragment : Fragment() {
    private lateinit var dashboardViewmodel: DashboardViewModel
    private lateinit var mAuth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference
    private lateinit var musicRecyclerView: RecyclerView
    private lateinit var musicArrayList: ArrayList<Music>
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

        musicRecyclerView = root.findViewById(R.id.musicList)
        musicRecyclerView.layoutManager = LinearLayoutManager(activity)
        musicRecyclerView.setHasFixedSize(true)

        musicArrayList = arrayListOf<Music>()
        getUserData()

        return root
    }
    private fun getUserData(){
        dbRef = FirebaseDatabase.getInstance().getReference("Music")

        dbRef.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){

                        val music = userSnapshot.getValue(Music::class.java)
                        musicArrayList.add(music!!)

                    }
                    musicRecyclerView.adapter = MyAdapter(musicArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


}



