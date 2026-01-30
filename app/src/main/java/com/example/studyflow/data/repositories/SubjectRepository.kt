package com.example.studyflow.data.repositories

import com.example.studyflow.data.dao.SubjectDao
import com.example.studyflow.data.entities.SubjectEntity
import kotlinx.coroutines.flow.Flow

class SubjectRepository(private val subjectDao: SubjectDao) {

    val allSubjects: Flow<List<SubjectEntity>> = subjectDao.getAllSubjects()

    suspend fun insert(subject: SubjectEntity) {
        subjectDao.insertSubject(subject)
    }

    suspend fun delete(subjectId: Int) {
        subjectDao.deleteSubject(subjectId)
    }
}