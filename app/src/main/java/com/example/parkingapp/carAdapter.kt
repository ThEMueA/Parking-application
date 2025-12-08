package com.example.parkingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CarAdapter(
    private val carClickInterface: CarClickInterface,
    private val carDeleteInterface: CarDeleteInterface
) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    private val allCars = ArrayList<Car>()

    inner class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTV: TextView = itemView.findViewById(R.id.tvCarTitle)
        val numberTV: TextView = itemView.findViewById(R.id.tvCarNumber)
        val ownerTV: TextView = itemView.findViewById(R.id.tvCarOwner)
        val deleteIV: ImageView = itemView.findViewById(R.id.ivDeleteCar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.car_item, parent, false)
        return CarViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = allCars[position]
        holder.titleTV.text = car.carTitle
        holder.numberTV.text = car.carNumber
        holder.ownerTV.text = car.carOwner

        holder.deleteIV.setOnClickListener {
            carDeleteInterface.onDeleteClick(car)
        }

        holder.itemView.setOnClickListener {
            carClickInterface.onCarClick(car)
        }
    }

    override fun getItemCount(): Int = allCars.size

    fun updateList(newList: List<Car>) {

        allCars.clear()
        allCars.addAll(newList)
        notifyDataSetChanged()

    }
}

interface CarClickInterface {
    fun onCarClick(car: Car)
}

interface CarDeleteInterface {
    fun onDeleteClick(car: Car)
}
