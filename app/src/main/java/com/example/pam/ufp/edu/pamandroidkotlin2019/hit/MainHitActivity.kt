package com.example.pam.ufp.edu.pamandroidkotlin2019.hit

import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.pam.ufp.edu.pamandroidkotlin2019.R

import kotlinx.android.synthetic.main.activity_main_hit.*
import kotlinx.android.synthetic.main.content_main_hit.*

class MainHitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_hit)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        buttonHit.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                var n : Int = textViewHit.text.toString().toInt()
                n++
                textViewHit.text = "$n"
            }

        })
    }

}
