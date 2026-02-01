package com.example.studyflow.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.studyflow.data.entities.TaskEntity
import com.example.studyflow.data.repositories.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    val allTasks: LiveData<List<TaskEntity>> = repository.allTasks.asLiveData()

    fun insert(task: TaskEntity) = viewModelScope.launch {
        repository.insert(task)
    }

    fun update(task: TaskEntity) = viewModelScope.launch {
        repository.update(task)
    }

    fun delete(taskId: Int) = viewModelScope.launch {
        repository.delete(taskId)
    }
}