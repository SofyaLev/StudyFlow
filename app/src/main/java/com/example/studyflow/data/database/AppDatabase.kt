package com.example.studyflow.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.studyflow.data.dao.TaskDao
import com.example.studyflow.data.entities.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}