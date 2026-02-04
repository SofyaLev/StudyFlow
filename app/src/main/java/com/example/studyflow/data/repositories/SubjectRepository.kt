package com.example.studyflow.data.repositories

import com.example.studyflow.data.dao.SubjectDao
import com.example.studyflow.data.entities.SubjectEntity
import com.example.studyflow.data.entities.SubjectWithTasks
import kotlinx.coroutines.flow.Flow

class SubjectRepository(private val subjectDao: SubjectDao) {

    val allSubjects: Flow<List<SubjectEntity>> = subjectDao.getAllSubjects()

    val allSubjectsWithTasks: Flow<List<SubjectWithTasks>> = subjectDao.getSubjectsWithTasks()

    suspend fun insert(subject: SubjectEntity) = subjectDao.insertSubject(subject)

    suspend fun update(subject: SubjectEntity) = subjectDao.updateSubject(subject)

    suspend fun delete(subjectId: Int) = subjectDao.deleteSubject(subjectId)
}