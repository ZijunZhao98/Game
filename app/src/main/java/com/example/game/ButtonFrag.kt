package com.example.game

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.game.ui.dialog.LevelFragment

@SuppressLint("ValidFragment")
class ButtonFrag(context: Context) : Fragment() {

    var fragmentContext: Context = context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentContext = activity?.applicationContext!!

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(
            R.layout.activity_button_frag,
            container,
            false
        )

        val fm = activity!!.supportFragmentManager
        val levelFrag = LevelFragment(context!!)

        view.findViewById<Button>(R.id.menu_btn).setOnClickListener {
            levelFrag.show(fm, "list_fragment")
        }

        return view
    }


    override fun onDestroy() {
        Log.e("FRAGMENT", "Destroyed!")
        super.onDestroy()
    }
}
