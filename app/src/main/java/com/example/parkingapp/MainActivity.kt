package com.example.parkingapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer

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

        // Hours spinner (1-24)
        val hours = (1..10).map { it.toString() }
        spinnerTime.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, hours)









        var switch =  false
        var startB = findViewById<Button>(R.id.btnStart)
        var carB = findViewById<Button>(R.id.carB)
        var smsB = findViewById<Button>(R.id.smsB)


            startB.setOnClickListener{

                val selectedCarIndex = spinnerCars.selectedItemPosition
                val selectedSmsIndex = spinnerSms.selectedItemPosition
                val selectedHours = spinnerTime.selectedItem.toString().toIntOrNull() ?: 1

                if (selectedCarIndex < 0 || selectedSmsIndex < 0) {
                    return@setOnClickListener
                }

                val car = carList[selectedCarIndex]
                val sms = smsList[selectedSmsIndex]




                if(switch==false) {
                    if (checkSmsPermission() == true) {
                        startSMSService(sms.smsNumber, selectedHours, car.carNumber)
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