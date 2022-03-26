package com.example.musikkapp.fragments.upload

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musikkapp.R
import com.example.musikkapp.fragments.home.DashboardFragment
import com.example.musikkapp.fragments.playlist.LibaryFragment
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.net.MediaType
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class UploadFragment : Fragment() {
    private lateinit var uploadViewModel: UploadViewModel
    private lateinit var mAuth: FirebaseAuth

    var selectBtn: ImageView? = null
    var uploadBtn: Button? = null
    var selectAudio: ImageView? = null
    var imgStore: ImageView? = null
    var fileLoc: TextView? = null
    var back: TextView? = null
    var title: EditText? = null
    var textUpload: ImageView? = null
    lateinit var ImgUri: Uri
    lateinit var AudioUri: Uri


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val dashboardFragment = DashboardFragment()
        /*
             fragmentManager?.beginTransaction()?.apply {
                 replace(
                     R.id.fragment_container,
                     dashboardFragment,
                     DashboardFragment::class.java.simpleName
                 )
                     .addToBackStack(null)
                     .commit()
             }
         */

        uploadViewModel =
            ViewModelProvider(this).get(UploadViewModel::class.java)
        val root = inflater.inflate(R.layout.upload_fragment, container, false)
        mAuth = FirebaseAuth.getInstance()
        selectBtn = root.findViewById(R.id.selectBtn) as ImageView
        uploadBtn = root.findViewById(R.id.uploadBtn) as Button
        selectAudio = root.findViewById(R.id.selectAudio) as ImageView
        imgStore = root.findViewById(R.id.imgUpload) as ImageView
        fileLoc = root.findViewById(R.id.musicTitle) as TextView
        title = root.findViewById(R.id.musicName) as EditText
        textUpload = root.findViewById(R.id.textUpload) as ImageView
        back = root.findViewById(R.id.upload_back) as TextView

        back!!.setOnClickListener {
            replaceFragment()
        }

        selectBtn!!.setOnClickListener {
            selectImage()

        }
        selectAudio!!.setOnClickListener {
            selectAudio()

        }
        uploadBtn!!.setOnClickListener {
            upload()

        }


        return root
    }

    fun replaceFragment() {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction!!.replace(R.id.fragment_container, LibaryFragment())
        transaction.disallowAddToBackStack()
        transaction.commit()

    }

    private fun upload() {
        val progressDialog = ProgressDialog(requireContext())
        val currentUser = mAuth.currentUser
        val userName: String? = currentUser?.uid
        progressDialog.setMessage("กำลังอัพโหลด")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_MM_dd", Locale.getDefault())
        val now = Date()
        val audName = title!!.text
        val fileName = "$audName" + "_cover"
        val hashMap1: HashMap<String, String> = HashMap()
        val storageReference =
            FirebaseStorage.getInstance().getReference("$userName/$audName/$fileName")
        val audioRef = FirebaseStorage.getInstance().getReference("$userName/$audName/$audName")

        storageReference.putFile(ImgUri).addOnSuccessListener {
            storageReference.downloadUrl.addOnSuccessListener(OnSuccessListener { uri ->
                val dbRef: DatabaseReference = FirebaseDatabase.getInstance()
                    .getReferenceFromUrl("https://museek-a09de-default-rtdb.asia-southeast1.firebasedatabase.app")
                    .child("Music").child("New").child("$audName")
                val usRef: DatabaseReference = FirebaseDatabase.getInstance()
                    .getReferenceFromUrl("https://museek-a09de-default-rtdb.asia-southeast1.firebasedatabase.app")
                    .child("User").child("${currentUser?.uid.toString()}").child("$audName")

                hashMap1.put("name", audName.toString())
                hashMap1.put("artist", currentUser?.displayName.toString())
                hashMap1.put("coverUrl", uri.toString())
                hashMap1.put("timestamp", now.toString())
                hashMap1.put("views","0")


                dbRef.setValue(hashMap1)
                usRef.setValue(hashMap1)

            })
            imgStore!!.setImageURI(null)
            selectBtn!!.visibility = View.VISIBLE
            textUpload!!.visibility = View.VISIBLE


        }.addOnFailureListener {
            if (progressDialog.isShowing) progressDialog.dismiss()
            Toast.makeText(activity, "อัพโหลดไม่เสร็จสิ้น", Toast.LENGTH_SHORT).show()

        }
        audioRef.putFile(AudioUri).addOnSuccessListener {
            audioRef.downloadUrl.addOnSuccessListener(OnSuccessListener { uri ->
                val dbRef2: DatabaseReference = FirebaseDatabase.getInstance()
                    .getReferenceFromUrl("https://museek-a09de-default-rtdb.asia-southeast1.firebasedatabase.app")
                    .child("Music").child("New").child("$audName")
                val usRef: DatabaseReference = FirebaseDatabase.getInstance()
                    .getReferenceFromUrl("https://museek-a09de-default-rtdb.asia-southeast1.firebasedatabase.app")
                    .child("User").child(currentUser?.uid.toString()).child("$audName")
                // val hashMap2: HashMap<String, String> = HashMap()
                var mp: MediaPlayer = MediaPlayer.create(activity, AudioUri)
                var duration = mp.duration.toLong()
                mp.release()


                /*   */

                hashMap1.put("songUrl", uri.toString())


                dbRef2.setValue(hashMap1)
                usRef.setValue(hashMap1)
            })
            title!!.setText("")
            if (progressDialog.isShowing) progressDialog.dismiss()
            Toast.makeText(activity, "อัพโหลดเสร็จสิ้น", Toast.LENGTH_SHORT).show()
            replaceFragment()

        }


    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 0)
    }

    private fun selectAudio() {
        val intentAudio = Intent()
        intentAudio.type = "audio/*"
        intentAudio.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intentAudio, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == AppCompatActivity.RESULT_OK) {
            ImgUri = data?.data!!
            imgStore!!.setImageURI(ImgUri)


            selectBtn!!.visibility = View.GONE
            textUpload!!.visibility = View.GONE
        }
        if (requestCode == 1 && resultCode == AppCompatActivity.RESULT_OK) {
            AudioUri = data?.data!!
            selectAudio!!.visibility = View.GONE
            fileLoc!!.text = ("File Location :  $AudioUri")

        }
    }


}