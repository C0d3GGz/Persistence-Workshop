package com.example.persistence.Datenbank

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.persistence.Datenbank.DAOs.ToDoListDao
import com.example.persistence.Datenbank.Entities.ListEntry

//TODO:
// - Verwende die passende Annotation um die Datenbankklasse mit alle dazugehörige Entitäten zu spezifizieren
// - ToDoListDatabase muss von eine bestimte Klasse Erben
abstract class ToDoListDatabase {
    abstract val dao : ToDoListDao
}