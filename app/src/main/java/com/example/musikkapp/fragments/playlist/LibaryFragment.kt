package com.example.musikkapp.fragments.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musikkapp.R
import com.example.musikkapp.fragments.home.Music
import com.example.musikkapp.fragments.home.MyAdapter
import com.example.musikkapp.fragments.player.PlayerFragment
import com.example.musikkapp.fragments.upload.UploadFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class LibaryFragment : Fragment() {
    var btn_upload : ImageView? = null
    private lateinit var libaryViewModel: LibaryViewModel
    private lateinit var mAuth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference
    private lateinit var libaryRecyclerView: RecyclerView
    private lateinit var libaryArrayList: ArrayList<Music>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        libaryViewModel =
            ViewModelProvider(this).get(LibaryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_libary, container, false)
        mAuth = FirebaseAuth.getInstance()
        var currentUser = mAuth.currentUser
        btn_upload = root.findViewById(R.id.btn_upload) as ImageView

        libaryRecyclerView = root.findViewById(R.id.libarylist)
        libaryRecyclerView.layoutManager =
            LinearLayoutManager(activity)

        libaryRecyclerView.setHasFixedSize(true)
        libaryArrayList = arrayListOf<Music>()
        getUserData()

        btn_upload!!.setOnClickListener{
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            val uploadFragment = UploadFragment()

            transaction?.replace(R.id.fragment_container,uploadFragment)?.addToBackStack(null)?.commit()
        }
        

        return root
    }
        private fun getUserData(){
            dbRef = FirebaseDatabase.getInstance().getReference("Music").child("New")

            dbRef.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        var currentUser = mAuth.currentUser
                        for(userSnapshot in snapshot.children){
                            val music = userSnapshot.getValue(Music::class.java)
                            if(music?.artist == currentUser?.displayName )
                            libaryArrayList.add(music!!)

                        }
                        libaryRecyclerView.adapter = LibAdapter(libaryArrayList)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }


}

