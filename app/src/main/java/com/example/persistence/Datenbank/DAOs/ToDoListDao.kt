package com.example.persistence.Datenbank.DAOs

import androidx.lifecycle.LiveData
import androidx.room.Dao
import com.example.persistence.Datenbank.Entities.ListEntry

@Dao
interface ToDoListDao {

    //TODO:
    fun removeEntry(entry : ListEntry)

    //TODO:
    fun addEntry (entry : ListEntry)


    //TODO: Funktion die
    fun changeEntry (entry : ListEntry)


    //TODO: Funktion, die alle Einträge zurückgibt, die noch nicht erledigt wurden
    fun getPendingEntries () : LiveData<List<ListEntry>>

    //TODO:  Funktion, die alle Einträge zurückgibt, die bereits erledigt wurden
    fun getCompletedEntries () : LiveData<List<ListEntry>>



}