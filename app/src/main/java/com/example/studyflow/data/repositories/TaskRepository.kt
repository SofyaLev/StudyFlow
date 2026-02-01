package com.example.studyflow.data.repositories

import com.example.studyflow.data.dao.TaskDao
import com.example.studyflow.data.entities.TaskEntity
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    val allTasks: Flow<List<TaskEntity>> = taskDao.getAllTasks()

    fun getTasksBySubject(subjectId: Int): Flow<List<TaskEntity>> {
        return taskDao.getTasksBySubject(subjectId)
    }

    suspend fun insert(task: TaskEntity) = taskDao.insertTask(task)

    suspend fun update(task: TaskEntity) = taskDao.updateTask(task)

    suspend fun delete(taskId: Int) = taskDao.deleteTask(taskId)
}