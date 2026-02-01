package com.example.studyflow.ui.tasks

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.studyflow.R
import com.example.studyflow.data.database.AppDatabase
import com.example.studyflow.data.entities.TaskEntity
import com.example.studyflow.data.repositories.TaskRepository

class AddTaskFragment : Fragment(R.layout.fragment_add_task) {

    private val viewModel: TaskViewModel by viewModels {
        val database = AppDatabase.getDatabase(requireContext())
        val repository = TaskRepository(database.taskDao())
        TaskViewModelFactory(repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etTitle = view.findViewById<EditText>(R.id.etTaskName)
        val etDeadline = view.findViewById<EditText>(R.id.etDeadline)
        val btnSave = view.findViewById<Button>(R.id.btnSaveTask)

        btnSave.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val deadline = etDeadline.text.toString().trim()

            val subjectId = arguments?.getInt("subjectId")

            if (title.isNotEmpty()) {
                val newTask = TaskEntity(
                    title = title,
                    deadline = deadline,
                    isCompleted = false,
                    subjectId = subjectId
                )

                viewModel.insert(newTask)

                Toast.makeText(requireContext(), "Task saved", Toast.LENGTH_SHORT).show()

                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Enter the name of the task", Toast.LENGTH_SHORT).show()
            }
        }
    }
}