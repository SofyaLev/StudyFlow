package com.example.studyflow.ui.tasks

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studyflow.R

class TasksFragment : Fragment(R.layout.fragment_tasks) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val subjectName = arguments?.getString("subjectName")

        val titleTasks: TextView = view.findViewById(R.id.tvSubjectTitle)
        titleTasks.text = subjectName ?: "Tasks"

        val tasks = listOf(
            TaskItem("Lab №1", "29.01", false),
            TaskItem("Test", "02.02", false),
            TaskItem("Report", "15.01", true)
        )

        val fab: View = view.findViewById(R.id.fabAddTask)
        fab.setOnClickListener {
            //findNavController().navigate(R.id.action_tasksFragment_to_addTaskFragment)
        }

        val rvTasks: RecyclerView = view.findViewById(R.id.rvTasks)

        rvTasks.layoutManager = LinearLayoutManager(requireContext())
        rvTasks.adapter = TasksAdapter(tasks)
    }
}