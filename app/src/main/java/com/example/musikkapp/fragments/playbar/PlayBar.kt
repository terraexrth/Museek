package com.example.musikkapp.fragments.playbar

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.renderscript.ScriptGroup
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.musikkapp.R

class PlayBar : Fragment() {

    companion object {
        fun newInstance() = PlayBar()

    }
    var layout:ConstraintLayout? = null
    private lateinit var viewModel: PlayBarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this).get(PlayBarViewModel::class.java)

       val root = inflater.inflate(R.layout.play_bar_fragment, container, false)
        layout = root.findViewById(R.id.playbar)
        layout!!.visibility = View.INVISIBLE


        return root
    }

    override fun onResume() {
        super.onResume()
    }

}