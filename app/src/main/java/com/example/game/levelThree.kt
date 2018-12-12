package com.example.game

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_level_four.*

import kotlinx.android.synthetic.main.activity_level_three.*

class levelThree : AppCompatActivity() {

    private var rectCloud1 = Rect(0, 0, 0, 0)
    private var rectCloud2 = Rect(0, 0, 0, 0)
    private var rectSun = Rect(0, 0, 0, 0)
    private var intersect1 = 1
    private var intersect2 = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_three)
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.add(R.id.btn_holder4, ButtonFrag(this))
        ft.commit()

        intersect1 = 1
        intersect2 = 1

        sun.getDrawingRect(rectSun)
        var xs = rectSun.centerX()
        var ys = rectSun.centerY()
        var hs = rectSun.height()
        var ws = rectSun.width()

        var cloudOnelistener = View.OnTouchListener(function = {view, motionEvent ->
            if(motionEvent.action == MotionEvent.ACTION_MOVE){
                view.y = motionEvent.rawY - view.height/2
                view.x = motionEvent.rawX - view.width/2

                //check if the clouds are overlapping the sun
                cloudOne.getDrawingRect(rectCloud1)

                var x1 = motionEvent.rawX
                var y1 = motionEvent.rawY
                var w1 = view.width
                var h1 = view.height

                if(!(x1 > xs+ws || x1+w1 < xs || y1 > ys+hs || y1+h1 < ys)){
                    intersect1 = 1
                }else{
                    intersect1 = 0
                }

                checkGameEnd()
            }
            true
        })

        var cloudTwolistener = View.OnTouchListener(function = {view, motionEvent ->
            if(motionEvent.action == MotionEvent.ACTION_MOVE){
                view.y = motionEvent.rawY - view.height/2
                view.x = motionEvent.rawX - view.width/2

                //check if the clouds are overlapping the sun
                cloudTwo.getDrawingRect(rectCloud2)

                var x2 = motionEvent.rawX
                var y2 = motionEvent.rawY
                var w2 = view.width
                var h2 = view.height

                if(!(x2 > xs+ws || x2+w2 < xs || y2 > ys+hs || y2+h2 < ys)){
                    intersect2 = 1
                }else{
                    intersect2 = 0
                }

                checkGameEnd()
            }
            true
        })


        cloudOne.setOnTouchListener(cloudOnelistener)
        cloudTwo.setOnTouchListener(cloudTwolistener)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    private fun checkGameEnd(){
        if((intersect1 == 0) && (intersect2 == 0)){
            Handler().postDelayed({
                levelThreeImg.setImageResource(R.drawable.clear)
                levelThreeText.text = "But eventually the sun will always come out"
            }, 500)

            Handler().postDelayed({
                val intent = Intent(this,levelFour::class.java)
                startActivity(intent)
            }, 1500)
        }
    }

}
