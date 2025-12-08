package com.example.parkingapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CarDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(car : Car)

    @Delete
    suspend fun delete(car :Car)

    @Query("SELECT*FROM carsTable ORDER BY id ASC")
    fun getAllCars(): LiveData<List<Car>>

    @Update
    suspend fun update(car: Car)

}


@Dao
interface SmsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(sms : Sms)

    @Delete
    suspend fun delete(sms :Sms)

    @Query("SELECT*FROM smsTable ORDER BY id ASC")
    fun getAllSms(): LiveData<List<Sms>>

    @Update
    suspend fun update(sms: Sms)

}