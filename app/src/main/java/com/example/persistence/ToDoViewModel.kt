package com.example.persistence

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.persistence.database.entities.ListEntry
import com.example.persistence.database.ToDoListDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ToDoViewModel(
    app: Application
) : AndroidViewModel(app) {

    private val db = ToDoListDatabase.getInstance(app.applicationContext)
    private val dao = db.dao

    val incompleteEntries = dao.getPendingEntries()
    val completedEntries = dao.getCompletedEntries()
    val _state = MutableStateFlow(ToDoListState())


    //TODO: to simple functions
    fun onEvent(event: ToDoListEvent, state: ToDoListState = ToDoListState()) {
        when (event) {
            // Hint: Implement Methods in the ToDoListDao that will make the corresponding change to the database

            ToDoListEvent.saveToDo -> {
                val name = _state.value.name

                val newEntry = ListEntry(name = name)
                viewModelScope.launch(Dispatchers.IO) {
                    dao.insertToDo(newEntry)
                }

                _state.update {
                    it.copy(
                        name = "",
                    )
                }
            }

            is ToDoListEvent.DeleteEntry -> {
                viewModelScope.launch(Dispatchers.IO) {
                    dao.deleteEntry(event.entry)
                }

            }

            is ToDoListEvent.completeEntry -> {
                viewModelScope.launch(Dispatchers.IO) {
                    dao.completeEntry(event.entry.copy(completed = true))
                }
            }

            is ToDoListEvent.uncompleteEntry -> {
                viewModelScope.launch(Dispatchers.IO) {
                    dao.uncompleteEntry(event.entry.copy(completed = false))
                }
            }


            is ToDoListEvent.SetName -> {
                _state.update { it.copy(name = event.name) }
            }
        }
    }
}