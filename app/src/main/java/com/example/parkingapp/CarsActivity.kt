package com.example.parkingapp


import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class CarsActivity :AppCompatActivity(),CarClickInterface, CarDeleteInterface {

    private lateinit var carViewModel: CSViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CarAdapter
    private lateinit var btnNew: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cars)
        btnNew = findViewById(R.id.btnNewCar)
        btnNew.setOnClickListener {
            val intent = Intent(this, AddEditCarActivity::class.java)
            startActivity(intent)
        }


        recyclerView = findViewById(R.id.recyclerCars)
        adapter = CarAdapter(this, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        carViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[CSViewModel::class.java]

        carViewModel.allCars.observe(this) { list ->
            adapter.updateList(list)
        }
    }

    override fun onCarClick(car: Car) {
        val intent = Intent(this, AddEditCarActivity::class.java)
        intent.putExtra("carId", car.id)
        intent.putExtra("carTitle", car.carTitle)
        intent.putExtra("carNumber", car.carNumber)
        intent.putExtra("carOwner", car.carOwner)
        startActivity(intent)
    }




    override fun onDeleteClick(car: Car) {
        carViewModel.deleteCar(car)
    }
}














