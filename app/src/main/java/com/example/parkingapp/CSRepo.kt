package com.example.parkingapp

import androidx.lifecycle.LiveData

class CSRepo(private val carDao: CarDao, private val smsDao: SmsDao) {
    val allCars: LiveData<List<Car>> = carDao.getAllCars();
    val allSms: LiveData<List<Sms>> = smsDao.getAllSms();

    suspend fun insert(car : Car){
        carDao.insert(car)
    }

    suspend fun delete(car : Car){
        carDao.delete(car)
    }
    suspend fun update(car: Car){
        carDao.update(car);
        }


    suspend fun insert(sms : Sms){
        smsDao.insert(sms)
    }

    suspend fun delete(sms : Sms){
        smsDao.delete(sms)
    }
    suspend fun update(sms: Sms){
        smsDao.update(sms);
    }



}