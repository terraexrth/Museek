package com.example.musikkapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth

class Dashboard : AppCompatActivity() {
    private  lateinit var mAuth: FirebaseAuth
    var idTxt : TextView? = null
    var nameTxt : TextView? = null
    var emailTxt : TextView? = null
    var imageProfile : ImageView? = null
    var logOut : Button? = null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
       /* val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        navView.visibility = View.GONE*/
        init()
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser


        idTxt!!.text = currentUser?.uid
        nameTxt!!.text = currentUser?.displayName
        emailTxt!!.text = currentUser?.email

        imageProfile?.let { Glide.with(this).load(currentUser?.photoUrl).into(it) }

        logOut!!.setOnClickListener {

           mAuth.signOut()
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
    private fun init(){
        idTxt = findViewById(R.id.id_txt) as TextView
        nameTxt = findViewById(R.id.name_txt) as TextView
        emailTxt = findViewById(R.id.email_txt) as TextView
        imageProfile = findViewById(R.id.profile_image) as ImageView
        logOut = findViewById(R.id.sign_out) as Button

    }

    }
