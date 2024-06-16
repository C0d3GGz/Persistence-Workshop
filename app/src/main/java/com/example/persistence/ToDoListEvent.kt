package com.example.persistence

import com.example.persistence.Datenbank.Entities.ListEntry

sealed interface ToDoListEvent {
    object saveNewEntry : ToDoListEvent
    data class DeleteEntry(val entry : ListEntry) : ToDoListEvent
    data class CompleteEntry(val entry: ListEntry ): ToDoListEvent
    data class UncompleteEntry(val entry : ListEntry ): ToDoListEvent
    data class SetName(val name :String) : ToDoListEvent


}