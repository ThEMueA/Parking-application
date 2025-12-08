package com.example.parkingapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CSViewModel(application: Application): AndroidViewModel(application) {

    val allCars: LiveData<List<Car>>
    val allSms: LiveData<List<Sms>>
    private val repo: CSRepo

    init{
        val cardao = CSDatabase.getDatabase(application).getCarsDao()
        val smsdao = CSDatabase.getDatabase(application).getSmsDao()
        repo= CSRepo(cardao,smsdao)
        allCars = repo.allCars
        allSms = repo.allSms
    }

    fun deleteCar(car: Car) = viewModelScope.launch( Dispatchers.IO )
    {
        repo.delete(car)
    }
    fun updateCar(car: Car) = viewModelScope.launch(Dispatchers.IO){
        repo.update(car)
    }
    fun insertCar(car: Car) = viewModelScope.launch(Dispatchers.IO){
        repo.insert(car)
    }



    fun deleteSms(sms: Sms) = viewModelScope.launch( Dispatchers.IO )
    {
        repo.delete(sms)
    }
    fun updateSms(sms: Sms) = viewModelScope.launch(Dispatchers.IO){
        repo.update(sms)
    }
    fun insertSms(sms: Sms) = viewModelScope.launch(Dispatchers.IO){
        repo.insert(sms)
    }



}