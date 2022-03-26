package com.example.musikkapp.fragments.Card

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musikkapp.R
import java.util.*
import kotlin.random.Random.Default.nextInt

class CardFragment : Fragment() {

    companion object {
        fun newInstance() = CardFragment()
    }

    private lateinit var viewModel: CardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this).get(CardViewModel::class.java)
        val root = inflater.inflate(R.layout.card_fragment, container, false)
        var card_img: ImageView? = root.findViewById<ImageView>(R.id.card_img)
        var card_name: TextView? = root.findViewById<TextView>(R.id.card_name)
        var card_info: TextView? = root.findViewById<TextView>(R.id.card_info)

        var cardName = arrayOf("Card ใบที่ 1","Card ใบที่ 2","Card ใบที่ 3")
        var cardImg = arrayOf<Int>(
            R.drawable.letter,
            R.drawable.message,
            R.drawable.thief

        )
        var cardInfo = arrayOf("นี่คือใบที่ 1 นะไอ้สัด","ใบที่ 2 แต่ไม่สองใจ","ใบที่ 3 ถามหาหวานใจ")

        val rnds = (0..cardName.size).random()
        card_img!!.setImageResource(cardImg[rnds])
        card_info!!.setText(cardInfo[rnds])
        card_name!!.setText(cardName[rnds])


        return root
    }




}