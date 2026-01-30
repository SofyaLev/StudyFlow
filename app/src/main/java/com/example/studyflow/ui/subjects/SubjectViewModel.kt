package com.example.studyflow.ui.subjects

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.studyflow.data.entities.SubjectEntity
import com.example.studyflow.data.repositories.SubjectRepository
import kotlinx.coroutines.launch

class SubjectViewModel(private val repository: SubjectRepository) : ViewModel() {

    val allSubjects: LiveData<List<SubjectEntity>> = repository.allSubjects.asLiveData()

    fun insert(subject: SubjectEntity) = viewModelScope.launch {
        repository.insert(subject)
    }

    fun delete(subjectId: Int) = viewModelScope.launch {
        repository.delete(subjectId)
    }
}