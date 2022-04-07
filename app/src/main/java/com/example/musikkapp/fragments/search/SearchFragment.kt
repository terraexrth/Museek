package com.example.musikkapp.fragments.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musikkapp.Communicator
import com.example.musikkapp.R
import com.example.musikkapp.fragments.home.Music
import com.example.musikkapp.fragments.home.MyAdapter
import com.example.musikkapp.fragments.playlist.DeleteModal
import com.example.musikkapp.fragments.playlist.LibAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SearchFragment : Fragment() {
    private lateinit var communicator: Communicator
    private lateinit var mAuth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference
    private lateinit var searchRecyclerView: RecyclerView
    private lateinit var searchArrayList: ArrayList<Music>
    private lateinit var searchViewModel: SearchViewModel
    var searchView : SearchView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        searchView = root.findViewById(R.id.searchView) as SearchView

        searchRecyclerView = root.findViewById(R.id.libarylist2)
        searchRecyclerView.layoutManager = LinearLayoutManager(activity)

        searchRecyclerView.setHasFixedSize(true)
        searchArrayList = arrayListOf<Music>()
        communicator = activity as Communicator
        searchView!!.setOnQueryTextListener(object  : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(newText: String?): Boolean {
              return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != null){
                    val userInput = newText.lowercase()

                    dbRef = FirebaseDatabase.getInstance().getReference("Music").child("New")

                    dbRef.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                searchArrayList.clear()
                                for (userSnapshot in snapshot.children) {
                                    val music = userSnapshot.getValue(Music::class.java)
                                    if (music != null) {
                                        if(music.name!!.lowercase().contains(userInput)){
                                            searchArrayList.add(music)
                                        }
                                        var adapter = SearchAdapter(searchArrayList)
                                        searchRecyclerView.adapter = adapter

                                        adapter.setOnSearchClickListener(object : SearchAdapter.onSearchClickListener{
                                            override fun onItemClick(position: Int) {

                                                communicator.passDataCom(adapter.getArraylist()[position].name)
                                            }

                                        })


                                    }



                                }

                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    })
                }


                return true
            }

        })


        return root
    }
    private fun getSearchData() {

            dbRef = FirebaseDatabase.getInstance().getReference("Music").child("New")

            dbRef.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        var currentUser = mAuth.currentUser
                        for (userSnapshot in snapshot.children) {
                            val music = userSnapshot.getValue(Music::class.java)

                            searchArrayList.add(music!!)

                        }
                        searchRecyclerView.adapter = LibAdapter(searchArrayList)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }

