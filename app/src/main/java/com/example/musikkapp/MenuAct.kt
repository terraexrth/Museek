package com.example.musikkapp

import android.content.Intent
import android.net.Uri
import com.example.musikkapp.MenuAct
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide


import com.example.musikkapp.fragments.home.DashboardFragment
import com.example.musikkapp.fragments.player.PlayerFragment
import com.example.musikkapp.fragments.playlist.LibaryFragment
import com.example.musikkapp.fragments.search.SearchFragment
import com.example.musikkapp.fragments.upload.UploadFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MenuAct : AppCompatActivity(),Communicator {

    private val dashboardFragment = DashboardFragment()
    private val uploadFragment = UploadFragment()
    private val searchFragment = SearchFragment()
    private val libaryFragment = LibaryFragment()
    var profileImg: ImageView? = null
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)
        replaceFragment(dashboardFragment)
        var bottom_navigation: BottomNavigationView? =
            findViewById<BottomNavigationView>(R.id.bottom_navigation)


        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser


        profileImg = findViewById<ImageView>(R.id.profile_float)


        profileImg?.let { Glide.with(this).load(currentUser?.photoUrl).into(it) }

        profileImg!!.setOnClickListener {
            val popupMenu = PopupMenu(this,it)

            popupMenu.setOnMenuItemClickListener { item ->
                when(item.itemId){
                    R.id.logout -> {
                        mAuth.signOut()
                        val intent = Intent(this,SignInActivity::class.java)
                        startActivity(intent)
                        finish()
                        true
                    }
                    else ->false

                }
            }

            popupMenu.inflate(R.menu.profilemenu)
            popupMenu.show()

        }


        if (bottom_navigation != null) {
            bottom_navigation.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.ic_home -> replaceFragment(dashboardFragment)
                    R.id.ic_search -> replaceFragment(searchFragment)
                    R.id.ic_lib -> replaceFragment(libaryFragment)
                }
                true
            }
        }


    }

    fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
    override fun passDataCom(position: String?) {
        val bundle = Bundle()
        bundle.putString("name",position)

        val transaction = this.supportFragmentManager.beginTransaction()
        val playerFragment = PlayerFragment()
        playerFragment.arguments = bundle

        transaction.replace(R.id.fragment_container,playerFragment).addToBackStack(null).commit()
    }


}