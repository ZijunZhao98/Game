package com.example.game

import android.content.Context
import android.content.Intent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import kotlinx.android.synthetic.main.activity_level_one.*

class levelOne : AppCompatActivity(), SensorEventListener {

    private lateinit var mSensorManager: SensorManager
    private var lightSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_one)

        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.add(R.id.btn_holder2, ButtonFrag(this))
        ft.commit()

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {
        if(event.sensor.type == Sensor.TYPE_LIGHT){
            if(event.values[0] <= 30){
                levelOneImg.setImageResource(R.drawable.night)
                levelOneText.text = "But most of the time, we will feel so much better in the morning"
                Handler().postDelayed({
                    val intent = Intent(this,levelTwo::class.java)
                    startActivity(intent)
                }, 2000)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mSensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
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
}
