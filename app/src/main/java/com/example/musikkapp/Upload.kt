package com.example.musikkapp


import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*


class Upload : AppCompatActivity() {
    private  lateinit var mAuth: FirebaseAuth
    var selectBtn : Button? = null
    var uploadBtn : Button? = null
    var selectAudio : Button? = null
    var imgStore : ImageView? = null
    var fileLoc : TextView? = null
    var title : EditText? = null
    lateinit var ImgUri : Uri
    lateinit var AudioUri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()



        setContentView(R.layout.activity_upload)
        init()

        selectBtn!!.setOnClickListener {
            selectImage()
        }
        selectAudio!!.setOnClickListener {
            selectAudio()
        }
        uploadBtn!!.setOnClickListener {
            upload()
        }

    }
    private fun init(){
        selectBtn=findViewById(R.id.selectBtn) as Button
        uploadBtn=findViewById(R.id.uploadBtn) as Button
        selectAudio = findViewById(R.id.selectAudio) as Button
        imgStore=findViewById(R.id.imgUpload) as ImageView
        fileLoc = findViewById(R.id.musicTitle) as TextView
        title = findViewById(R.id.musicName) as EditText
    }
    private fun upload(){
        val progressDialog = ProgressDialog(this)
        val currentUser = mAuth.currentUser
        val userName: String? = currentUser?.uid
        progressDialog.setMessage("กำลังอัพโหลด")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_MM_dd", Locale.getDefault())
        val now = Date()
        val audName = title!!.text
        val fileName = "$audName"+"_cover"
        val storageReference = FirebaseStorage.getInstance().getReference("$userName/$audName/$fileName")
        val audioRef = FirebaseStorage.getInstance().getReference("$userName/$audName/$audName")

        storageReference.putFile(ImgUri).addOnSuccessListener {
            imgStore!!.setImageURI(null)
            Toast.makeText(this@Upload,"อัพโหลดเสร็จสิ้น",Toast.LENGTH_SHORT).show()
            if(progressDialog.isShowing) progressDialog.dismiss()

        }.addOnFailureListener{
            if(progressDialog.isShowing) progressDialog.dismiss()
            Toast.makeText(this@Upload,"อัพโหลดไม่เสร็จสิ้น",Toast.LENGTH_SHORT).show()

        }
        audioRef.putFile(AudioUri).addOnSuccessListener {
            if(progressDialog.isShowing) progressDialog.dismiss()
        }

    }


    private fun selectImage(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent,0)
    }
    private fun selectAudio(){
        val intentAudio = Intent()
        intentAudio.type = "audio/*"
        intentAudio.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intentAudio,1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==0 && resultCode== RESULT_OK) {
            ImgUri = data?.data!!
            imgStore!!.setImageURI(ImgUri)
        }
        if(requestCode==1 && resultCode== RESULT_OK){
            AudioUri = data?.data!!
            fileLoc!!.text = AudioUri.toString()

        }
    }

}
