package com.example.parkingapp

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Car::class, Sms::class], version = 2,exportSchema = false)
abstract class CSDatabase : RoomDatabase() {
    abstract fun getCarsDao(): CarDao
    abstract fun getSmsDao(): SmsDao

    companion object{
        @Volatile
        private var INSTANCE: CSDatabase?=null

        fun getDatabase(app: Application): CSDatabase{

             /*   INSTANCE?.close();
                  app.deleteDatabase("note_database1")
            app.deleteDatabase("park_database")*/

            return INSTANCE ?: synchronized(this){


                val instance = Room.databaseBuilder(
                    app.applicationContext,
                    CSDatabase::class.java,
                    "park_database"
                ).build()

                INSTANCE = instance

               instance

            }



        }

    }
}