package com.example.persistence

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.persistence.Datenbank.DAOs.ToDoListDao
import com.example.persistence.Datenbank.Entities.ListEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ToDoViewModel (
    private val dao : ToDoListDao
) : ViewModel() {

    val incompletedEntries : LiveData<List<ListEntry>> = dao.getPendingEntries()
    val completedEntries : LiveData<List<ListEntry>> = dao.getPendingEntries()



    val _state = MutableStateFlow(ToDoListState())
    fun onEvent(event: ToDoListEvent) {
        when (event) {
            ToDoListEvent.saveNewEntry -> {
                val name = _state.value.name

                // !! Neuer ListEntry
                val newTodo = ListEntry(name = name)
                viewModelScope.launch(Dispatchers.IO) {
                    // TODO: Rufe hier die Methode auf, die ein bestimmter ListEntry hinzufügt.
                }

                _state.update { it.copy(
                    name = "",
                ) }
            }

            is ToDoListEvent.DeleteEntry -> {
                viewModelScope.launch(Dispatchers.IO) {
                    //TODO: Rufe hier die Methode auf, die ein bestimmtes ListEntry löscht.
                    // .
                    // Hinweis:
                    // - Mit 'event.entry' kann man auf den ListEntry, bei dem der Delete-Knopf gedrückt wurde, zugreifen
                }

            }

            is ToDoListEvent.CompleteEntry -> {
                viewModelScope.launch(Dispatchers.IO) {
                    //TODO: Rufe hier die Methode, die ein bestimmtes ListEntry aktualisiert, so auf, dass der ListEntry als
                    // erfüllt gekennzeichnet wird.
                    // .
                    // Hinweis:
                    // - In Kotlin kann man mit der Methode .copy(), leicht abgeändert Kopien von Objekte erstellen (Nur für Data Class)
                    //      Z.B:
                    //          event.entry.copy(name = "Müll Rausbringen")
                }
            }
            is ToDoListEvent.UncompleteEntry -> {
                viewModelScope.launch(Dispatchers.IO) {
                    //TODO: Rufe hier die Methode, die ein bestimmtes ListEntry Verändert, so auf, dass es als
                    // nicht erfüllt gekennzeichnet wird.
                }
            }


            is ToDoListEvent.SetName -> {
                _state.update { it.copy(name = event.name) }
            }
        }
    }
}