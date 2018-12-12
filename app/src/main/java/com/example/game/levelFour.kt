package com.example.game

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Point
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_level_four.*

class levelFour : AppCompatActivity(), SensorEventListener {

    private lateinit var mSensorManager: SensorManager
    private var motionSensor: Sensor? = null
    private lateinit var mCustomDrawableView:  CustomDrawableView
    var xmax : Int = 0
    var ymax : Int = 0
    lateinit var mBitmap: Bitmap
    lateinit var gBitmap: Bitmap
    var xl : Float = 0f
    var yl : Float = 0f
    var xp : Float = 100f
    var yp : Float = 100f
    var bh : Int = 0
    var bw : Int = 0
    var gh : Int = 0
    var gw : Int = 0
    var gpx : Float = 300f
    var gpy : Float = 900f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        motionSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        mCustomDrawableView = CustomDrawableView(this)
        setContentView(mCustomDrawableView)

        var display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        xmax = size.x
        ymax = size.y
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {
        if(event.sensor.type == Sensor.TYPE_ACCELEROMETER){
            mCustomDrawableView.onSensorEvent(event.values[0], event.values[1])
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
        Handler().postDelayed({
            val intent = Intent(this,end::class.java)
            startActivity(intent)
        }, 1500)
    }

    inner class CustomDrawableView(context: Context) : View(context){

        init{
            mBitmap = BitmapFactory.decodeResource(resources, R.drawable.ball)
            bh = mBitmap!!.height
            bw = mBitmap!!.width

            gBitmap = BitmapFactory.decodeResource(resources, R.drawable.goal)
            gh = gBitmap!!.height
            gw = gBitmap!!.width

            setWillNotDraw(false)
        }

        fun onSensorEvent(xa : Float, ya: Float){
            xl -= xa
            yl -= ya

            xp += xl
            yp += yl

            if(xp > xmax - bw){
                xp = (xmax - bw).toFloat()
                xl = 0f
            }else if(xp < 0){
                xp = 0f
                xl = 0f
            }

            if(yp > ymax -bh){
                yp = (ymax - bh).toFloat()
                yl = 0f
            }else if(yp < 0){
                yp = 0f
                yl = 0f
            }

           if(!(xp > gpx + gw || xp + bw < gpx || yp > gpy + gh || yp + bh < gpy)){
               levelPassed()
           }

        }

        override fun onDraw(canvas: Canvas?){
            canvas?.drawBitmap(mBitmap, xp, yp, null)
            canvas?.drawBitmap(gBitmap, gpx, gpy, null)
            invalidate()
        }
    }
}
