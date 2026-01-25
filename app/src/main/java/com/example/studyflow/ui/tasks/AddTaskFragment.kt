package com.example.studyflow.ui.tasks

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.studyflow.MainActivity
import com.example.studyflow.R
import com.example.studyflow.data.entities.TaskEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddTaskFragment : Fragment(R.layout.fragment_add_task) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etTaskName = view.findViewById<EditText>(R.id.etTaskName)
        val etDeadline = view.findViewById<EditText>(R.id.etDeadline)
        val btnSaveTask = view.findViewById<Button>(R.id.btnSaveTask)

        btnSaveTask.setOnClickListener {
            val title = etTaskName.text.toString()
            val deadline = etDeadline.text.toString()

            if (title.isNotBlank()) {
                saveTask(title, deadline)
            } else {
                etTaskName.error = "Please enter a task name"
            }
        }
    }

    private fun saveTask(title: String, deadline: String) {
        val newTask = TaskEntity(
            title = title,
            deadline = deadline,
            isCompleted = false
        )

        viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                (activity as? MainActivity)?.database?.taskDao()?.insertTask(newTask)
            }

            findNavController().popBackStack()
        }
    }
}