package com.example.studyflow.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class SubjectWithTasks(
    @Embedded
    val subject: SubjectEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "subjectId"
    )
    val tasks: List<TaskEntity>
)