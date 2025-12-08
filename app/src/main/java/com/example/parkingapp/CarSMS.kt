package com.example.parkingapp
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "carsTable")
data class Car(
    @ColumnInfo(name="title") val carTitle: String,
    @ColumnInfo(name = "description") val carNumber: String,
    @ColumnInfo(name= "owner") val carOwner: String
) {
  @PrimaryKey(autoGenerate = true)
  var id =0
}

@Entity(tableName = "smsTable")
data class Sms(
    @ColumnInfo(name="title") val smsTitle: String,
    @ColumnInfo(name = "number") val smsNumber: String,
    @ColumnInfo(name= "city") val smsCity: String
) {
    @PrimaryKey(autoGenerate = true)
    var id =0
}