package com.example.parkingapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlin.getValue

class AddEditSmsActivity: AppCompatActivity() {

    private lateinit var etTitle: EditText
    private lateinit var etNumber: EditText
    private lateinit var etCity: EditText
    private lateinit var btnSave: Button

    private val smsViewModel: CSViewModel by viewModels() // ViewModel tied to Activity

    private var smsId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_sms)

        etTitle = findViewById(R.id.etSmsTitle)
        etNumber = findViewById(R.id.etSmsNumber)
        etCity = findViewById(R.id.etSmsCity)
        btnSave = findViewById(R.id.btnSaveSms)

        // Check if editing
        smsId = intent.getIntExtra("smsId", -1).takeIf { it != -1 }

        if (smsId != null) {
            // populate fields
            etTitle.setText(intent.getStringExtra("smsTitle"))
            etNumber.setText(intent.getStringExtra("smsNumber"))
            etCity.setText(intent.getStringExtra("City"))
        }

        btnSave.setOnClickListener {
            val title = etTitle.text.toString()
            val number = etNumber.text.toString()
            val city = etCity.text.toString()

            if (title.isBlank() || number.isBlank() || city.isBlank()) {
                return@setOnClickListener
            }

            if (smsId != null) {
                // edit existing car
                val updatedSms = Sms(title, number, city).apply { id = smsId!! }
                smsViewModel.updateSms(updatedSms)
            } else {
                // create new car
                val newSms = Sms(title, number, city)
                smsViewModel.insertSms(newSms)
            }

            finish() // close activity
        }
    }
}

