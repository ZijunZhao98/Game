package com.example.game

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_level_two.*

class levelTwo : AppCompatActivity() , SensorEventListener {

    private lateinit var mSensorManager: SensorManager
    private var motionSensor: Sensor? = null
    private var lastx : Float = -1.0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_two)
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.add(R.id.btn_holder3, ButtonFrag(this))
        ft.commit()
        lastx = -1.0f

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        motionSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        Handler().postDelayed({
            levelTwoText.text = ""
        }, 2000)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {
        if(event.sensor.type == Sensor.TYPE_ACCELEROMETER){
            //detect shake event
            var timeNow = System.currentTimeMillis()
            var timeLast : Long = 0
            val difference = timeNow - timeLast

            if(difference > 1000){
                timeLast = timeNow

                var x = event.values[0]

                var speed = Math.abs(x - lastx ) * 100
                Log.d("SPEED", speed.toString())
                if(speed > 200){
                    levelPassed()
                }
                lastx = x

            }
        }
    }

    override fun onResume() {
        super.onResume()
        mSensorManager.registerListener(this, motionSensor, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        mSensorManager.unregisterListener(this)
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

    private fun levelPassed(){
        levelTwoImg.setImageResource(R.drawable.open)
        Handler().postDelayed({
            levelTwoText.text = "Even if it is something small"
            val intent = Intent(this,levelThree::class.java)
            startActivity(intent)
        }, 2000)
    }


}
