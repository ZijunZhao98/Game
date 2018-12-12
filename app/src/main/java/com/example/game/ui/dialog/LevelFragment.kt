package com.example.game.ui.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.game.*

@SuppressLint("ValidFragment")
class LevelFragment(context: Context) : DialogFragment() {

    private val levelMap = mapOf(0 to Intent(context, MainActivity::class.java), 1 to Intent(context, levelOne::class.java), 2 to Intent(context, levelTwo::class.java), 3 to Intent(context, levelThree::class.java), 4 to Intent(context, levelFour::class.java))

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragview = inflater.inflate(R.layout.dialog_fragment, container, false)
        var levels = arrayOf("home", "level one", "level two", "level three", "level four")

        val list = fragview.findViewById(R.id.dialog_frag) as ListView

        list!!.adapter = ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, levels)

        this.dialog.setTitle("Choose your level!")

        list.setOnItemClickListener {
                adapterView,
                view,
                position,
                l
            -> changeScreen(position)
        }

        return fragview
    }

    private fun changeScreen(level: Int){
        var intent = levelMap[level]
        startActivity(intent)
    }


}
