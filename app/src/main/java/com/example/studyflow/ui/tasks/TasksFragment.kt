package com.example.studyflow.ui.tasks

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studyflow.R
import com.example.studyflow.data.database.AppDatabase
import com.example.studyflow.data.repositories.TaskRepository
import com.example.studyflow.databinding.FragmentTasksBinding

class TasksFragment : Fragment(R.layout.fragment_tasks) {

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!
    private val taskAdapter = TasksAdapter { task ->
        val bundle = Bundle().apply {
            putInt("taskId", task.id)
            putString("taskTitle", task.title)
            putString("taskDeadline", task.deadline)
        }
        findNavController().navigate(R.id.action_tasksFragment_to_addTaskFragment, bundle)
    }

    private val viewModel: TaskViewModel by viewModels {
        val database = AppDatabase.getDatabase(requireContext())
        val repository = TaskRepository(database.taskDao())
        TaskViewModelFactory(repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTasksBinding.bind(view)

        setupUI()
        setupRecyclerView()
        setupSwipeToDelete()
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

    private fun setupSwipeToDelete() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val task = taskAdapter.currentList[position]

                viewModel.delete(task.id)
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.rvTasks)
    }

    private fun observeTasks() {
        viewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
            taskAdapter.submitList(tasks)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}