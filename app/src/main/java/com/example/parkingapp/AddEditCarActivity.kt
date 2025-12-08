package com.example.parkingapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class AddEditCarActivity : AppCompatActivity() {

    private lateinit var etTitle: EditText
    private lateinit var etNumber: EditText
    private lateinit var etOwner: EditText
    private lateinit var btnSave: Button


    private val carViewModel: CSViewModel by viewModels() // ViewModel tied to Activity

    private var carId: Int? = null // null = new car, not null = edit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_car)

        etTitle = findViewById(R.id.etCarTitle)
        etNumber = findViewById(R.id.etCarNumber)
        etOwner = findViewById(R.id.etCarOwner)
        btnSave = findViewById(R.id.btnSaveCar)


        // Check if editing
        carId = intent.getIntExtra("carId", -1).takeIf { it != -1 }

        if (carId != null) {
            // populate fields
            etTitle.setText(intent.getStringExtra("carTitle"))
            etNumber.setText(intent.getStringExtra("carNumber"))
            etOwner.setText(intent.getStringExtra("carOwner"))
        }

        btnSave.setOnClickListener {
            val title = etTitle.text.toString()
            val number = etNumber.text.toString()
            val owner = etOwner.text.toString()

            if (title.isBlank() || number.isBlank() || owner.isBlank()) {
                return@setOnClickListener
            }

            if (carId != null) {
                // edit existing car
                val updatedCar = Car(title, number, owner).apply { id = carId!! }
                carViewModel.updateCar(updatedCar)
            } else {
                // create new car
                val newCar = Car(title, number, owner)
                carViewModel.insertCar(newCar)
            }

            finish() // close activity
        }
    }
}
