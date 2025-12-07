package com.example.parkingapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
class Note(
    @ColumnInfo(name="title") val noteTitle: String,
    @ColumnInfo(name = "description") val noteDescribtion: String
) {
    @PrimaryKey(autoGenerate = true)
    var id=0;
}


@Dao
interface NotesDao {
    @Insert(onConflict=OnConflictStategy.IGNORE)
    suspend fun isert(note: Note)

    @Delete
    suspend fun delete(note: Note)

   @Query("Select * from notesTable order by id ASC")
   fun getAllNotes(): LiveDAta<List<Note>>

@Update
suspend fun update(note: Note)

}