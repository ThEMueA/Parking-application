package com.example.parkingapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SelectFormat
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    // recycler view, exit text, button and view model.

     private lateinit var spinnerCars: Spinner
     private lateinit var spinnerSms: Spinner
     private lateinit var spinnerTime: Spinner
    private var carList =  listOf<Car>()
    private var smsList =  listOf<Sms>()

    private val csViewModel: CSViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

         spinnerCars = findViewById(R.id.spinnerCars)
         spinnerSms =  findViewById(R.id.spinnerSms)
        spinnerTime = findViewById(R.id.spinnerHours)


       //var a = CSViewModel(application)


        // Load Cars
        csViewModel.allCars.observe(this, Observer { cars ->
            carList = cars
            val carTitles = cars.map { it.carTitle }
            spinnerCars.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, carTitles)
        })

        // Load SMS
        csViewModel.allSms.observe(this, Observer { sms ->
            smsList = sms
            val smsTitles = sms.map { it.smsTitle }
            spinnerSms.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, smsTitles)
        })



        val HOUR = 60 * 60 * 1000L
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())

        val hours = (1..10).map { h ->
            val futureTime = System.currentTimeMillis() + h * HOUR
            val timeStr = sdf.format(Date(futureTime))
            if(h==1){"$h hour/   valid until $timeStr" }
             else{"$h hours/   valid until $timeStr"}
        }


      /*  val hourss = (1..10).map { h -> "f"+h }
        hourss.size*/


        spinnerTime.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, hours)









        var switch =  false
        var startB = findViewById<Button>(R.id.btnStart)
        var carB = findViewById<Button>(R.id.carB)
        var smsB = findViewById<Button>(R.id.smsB)


            startB.setOnClickListener{

                val selectedCarIndex = spinnerCars.selectedItemPosition
                val selectedSmsIndex = spinnerSms.selectedItemPosition
                val selectedHours = spinnerTime.selectedItemPosition

                if (selectedCarIndex < 0 || selectedSmsIndex < 0) {
                    return@setOnClickListener
                }

                val car = carList[selectedCarIndex]
                val sms = smsList[selectedSmsIndex]
                val hours = selectedHours+1;



                if(switch==false) {
                    if (checkSmsPermission() == true) {
                        startSMSService(sms.smsNumber, hours, car.carNumber)
                    }
                    startB.text="STOP SERVICE"; switch=true;
                }
                else{
                    stopSMSService(); startB.text="START SERVICE"; switch=false;
                }
            }


        carB.setOnClickListener {
            var i = Intent(this , CarsActivity::class.java)
            startActivity(i)
            startActivity(i)
        }

        smsB.setOnClickListener {
            var i = Intent(this , SMSActivity::class.java)
            startActivity(i)
            startActivity(i)
        }


    }


    //SMS SERVICE


















////checks permission
    private fun checkSmsPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.SEND_SMS), 100
            )
            false
        } else true
    }

///Start the service
    private fun startSMSService(smsNumber: String, stopAfterHours: Int, message: String) {
        val intent = Intent(this, ServiceSMS::class.java)
        intent.putExtra("smsNumber", smsNumber)
        intent.putExtra("message", message) // your message
        intent.putExtra("stopTime", System.currentTimeMillis() + stopAfterHours * 3600000)

        ContextCompat.startForegroundService(this, intent)
    }
///Stops the service
    private fun stopSMSService(){
        val intent = Intent(this, ServiceSMS::class.java)
        stopService(intent);
    }
}