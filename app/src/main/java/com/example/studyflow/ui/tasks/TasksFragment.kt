package com.example.studyflow.ui.tasks

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studyflow.R

class TasksFragment : Fragment(R.layout.fragment_tasks) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tasks = listOf(
            TaskItem("Lab №1", "29.01", false),
            TaskItem("Test", "02.02", false),
            TaskItem("Report", "15.01", true)
        )

        val rvTasks: RecyclerView = view.findViewById(R.id.rvTasks)

        rvTasks.layoutManager = LinearLayoutManager(requireContext())
        rvTasks.adapter = TasksAdapter(tasks)
    }
}