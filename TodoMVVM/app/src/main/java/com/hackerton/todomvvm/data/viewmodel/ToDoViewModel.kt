package com.hackerton.todomvvm.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hackerton.todomvvm.data.ToDoDatabase
import com.hackerton.todomvvm.data.model.ToDoData
import com.hackerton.todomvvm.data.repo.ToDoRepository
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application): AndroidViewModel(application) {
    private val toDoDao = ToDoDatabase.getDatabase(application).toDoDao()
    private val repository: ToDoRepository

    val getAllData: LiveData<List<ToDoData>>
    val sortByHighPriority: LiveData<List<ToDoData>>
    val sortByLowPriority: LiveData<List<ToDoData>>

    init{
        repository = ToDoRepository(toDoDao)
        getAllData = repository.getAllData
        sortByHighPriority = repository.sortByHighPriority
        sortByLowPriority = repository.sortByLowPriority
    }

    fun insertData(toDoData: ToDoData){
        viewModelScope.launch {
            repository.insertData(toDoData)
        }
    }

    fun updateData(toDoData: ToDoData) = viewModelScope.launch{
        repository.updateData(toDoData)
    }

    fun deleteItem(toDoData: ToDoData) = viewModelScope.launch {
        repository.deleteItem(toDoData)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<ToDoData>>{
        return repository.searchDatabase(searchQuery)
    }
}