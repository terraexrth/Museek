package com.example.musikkapp.fragments.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musikkapp.R


class LibaryFragment : Fragment() {

    private lateinit var libaryViewModel: LibaryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        libaryViewModel =
            ViewModelProvider(this).get(LibaryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_libary, container, false)

        return root
    }
}
