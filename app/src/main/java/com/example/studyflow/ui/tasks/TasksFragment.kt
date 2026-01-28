package com.example.studyflow.ui.tasks

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studyflow.R
import com.example.studyflow.data.database.AppDatabase
import com.example.studyflow.databinding.FragmentTasksBinding
import kotlinx.coroutines.launch

class TasksFragment : Fragment(R.layout.fragment_tasks) {

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    private val taskAdapter = TaskAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTasksBinding.bind(view)

        setupUI()
        setupRecyclerView()
        observeTasks()
    }

    private fun setupUI() {
        val subjectName = arguments?.getString("subjectName")
        binding.tvSubjectTitle.text = subjectName ?: "Tasks"

        binding.fabAddTask.setOnClickListener {
            findNavController().navigate(R.id.action_tasksFragment_to_addTaskFragment)
        }
    }

    private fun setupRecyclerView() {
        binding.rvTasks.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun observeTasks() {
        val database = AppDatabase.getDatabase(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            database.taskDao().getAllTasks().collect { tasksFromDb ->
                taskAdapter.submitList(tasksFromDb)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}