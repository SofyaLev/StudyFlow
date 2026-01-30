package com.example.studyflow.ui.tasks

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.studyflow.R
import com.example.studyflow.data.database.AppDatabase
import com.example.studyflow.data.entities.TaskEntity
import com.example.studyflow.databinding.FragmentAddTaskBinding
import kotlinx.coroutines.launch

class AddTaskFragment : Fragment(R.layout.fragment_add_task) {

    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentAddTaskBinding.bind(view)

        super.onViewCreated(view, savedInstanceState)

        binding.btnSaveTask.setOnClickListener {
            saveTask()
        }
    }

    private fun saveTask() {
        val title = binding.etTaskName.text.toString()
        val deadline = binding.etDeadline.text.toString()

        if (title.isBlank()) {
            binding.etTaskName.error = "Please enter a task name"
            return
        }

        val newTask = TaskEntity(
            id = 0,
            title = title,
            deadline = deadline,
            isCompleted = false
        )

        val database = AppDatabase.getDatabase(requireContext())
        viewLifecycleOwner.lifecycleScope.launch {
            database.taskDao().insertTask(newTask)

            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}