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

    val incompleteEntries : LiveData<List<ListEntry>> = dao.getPendingEntries()
    val completedEntries : LiveData<List<ListEntry>> = dao.getCompletedEntries()



    val _state = MutableStateFlow(ToDoListState())
    fun onEvent(event: ToDoListEvent) {
        when (event) {

            ToDoListEvent.saveNewEntry -> {
                val name = _state.value.name
                // !!!!!! Neuer ListEntry !!!!!!!!!!
                val newEntry = ListEntry(taskID = 0, name = name)
                viewModelScope.launch(Dispatchers.IO) {
                    // TODO: Rufe hier die Methode auf, die ein bestimmter ListEntry hinzufügt
                    //  Hinweis: Innerhalb vom ToDoViewModel kann man mit 'dao.METHODE()' auf die ToDoListDao Methoden zugreifen
                }

                _state.update { it.copy(
                    name = "",
                ) }
            }

            is ToDoListEvent.DeleteEntry -> {
                viewModelScope.launch(Dispatchers.IO) {
                    //TODO: Rufe hier die Methode auf, die ein bestimmtes ListEntry löscht.
                    // Hinweis: Mit 'event.entry' kann man auf den ListEntry, bei dem der Delete-Knopf gedrückt wurde, zugreifen
                }

            }

            is ToDoListEvent.CompleteEntry -> {
                viewModelScope.launch(Dispatchers.IO) {
                    //TODO: Rufe hier die Methode, die ein bestimmtes Eintrag aktualisiert, so auf, dass der Eintrag als
                    // erfüllt gekennzeichnet wird.
                    // Hinweis:
                    // - In Kotlin kann man mit der Methode .copy(), Kopien von Objekte erstellen, wo nur einzelne Eigenschaften
                    //   verändert werden
                    //      Z.B:
                    //          event.entry.copy(name = "Müll Rausbringen")
                }
            }
            is ToDoListEvent.UncompleteEntry -> {
                viewModelScope.launch(Dispatchers.IO) {
                    //TODO: Rufe hier die Methode, die ein bestimmtes Eintrag aktualisiert, so auf, dass der Eintrag als
                    // nicht erfüllt gekennzeichnet wird.
                }
            }


            is ToDoListEvent.SetName -> {
                _state.update { it.copy(name = event.name) }
            }
        }
    }
}