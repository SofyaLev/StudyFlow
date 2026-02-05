package com.example.studyflow.ui.tasks

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.studyflow.R
import com.example.studyflow.data.database.AppDatabase
import com.example.studyflow.data.entities.TaskEntity
import com.example.studyflow.data.repositories.TaskRepository
import java.text.SimpleDateFormat
import java.util.Locale

class AddTaskFragment : Fragment(R.layout.fragment_add_task) {

    private val viewModel: TaskViewModel by viewModels {
        val database = AppDatabase.getDatabase(requireContext())
        val repository = TaskRepository(database.taskDao())
        TaskViewModelFactory(repository)
    }

    private var currentTaskId: Int? = null
    private val calendar = Calendar.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etTitle = view.findViewById<EditText>(R.id.etTaskName)
        val etDeadline = view.findViewById<EditText>(R.id.etDeadline)
        val btnSave = view.findViewById<Button>(R.id.btnSaveTask)
        val tvHeader = view.findViewById<TextView>(R.id.tvHeader)

        etDeadline.setOnClickListener {
            showDatePicker(etDeadline)
        }

        arguments?.let {
            if (it.containsKey("taskId")) {
                currentTaskId = it.getInt("taskId")
                etTitle.setText(it.getString("taskTitle"))
                etDeadline.setText(it.getString("taskDeadline"))

                btnSave.text = "Update task"
                tvHeader?.text = "Edit task"
            }
        }
        btnSave.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val deadline = etDeadline.text.toString().trim()

            val subjectId = arguments?.getInt("subjectId")

            if (title.isNotEmpty()) {
                val newTask = TaskEntity(
                    id = currentTaskId ?: 0,
                    title = title,
                    deadline = deadline,
                    isCompleted = false,
                    subjectId = subjectId
                )

                if (currentTaskId == null) {
                    viewModel.insert(newTask)
                    Toast.makeText(requireContext(), "Task saved", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.update(newTask)
                    Toast.makeText(requireContext(), "Task updated", Toast.LENGTH_SHORT).show()
                }

                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Enter the name of the task", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDatePicker(editText: EditText) {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
            editText.setText(sdf.format(calendar.time))
        }

        DatePickerDialog(
            requireContext(),
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}