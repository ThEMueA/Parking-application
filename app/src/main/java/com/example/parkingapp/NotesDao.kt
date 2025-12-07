package com.example.parkingapp

import androidx.lifecycle.LiveData
import androidx.room.*
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note :Note)

    @Delete()
    suspend fun delete(note :Note)

    @Query("SELECT*FROM notesTable ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Note>>

    @Update()
    suspend fun update(note: Note)

}