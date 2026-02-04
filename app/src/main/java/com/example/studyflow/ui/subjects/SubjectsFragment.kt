package com.example.studyflow.ui.subjects

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
import com.example.studyflow.data.repositories.SubjectRepository

class SubjectsFragment : Fragment(R.layout.fragment_subjects) {

    private val viewModel: SubjectViewModel by viewModels {
        val database = AppDatabase.getDatabase(requireContext())
        val repository = SubjectRepository(database.subjectDao())
        SubjectViewModelFactory(repository)
    }

    private val subjectsAdapter = SubjectsAdapter(
        onItemClick = { subject ->
            val bundle = Bundle().apply {
                putInt("subjectId", subject.id)
                putString("subjectName", subject.name)
            }
            findNavController().navigate(R.id.action_subjectsFragment_to_tasksFragment, bundle)
        },
        onLongClick = { subject ->
            val bundle = Bundle().apply {
                putInt("subjectId", subject.id)
                putString("subjectName", subject.name)
            }
            findNavController().navigate(R.id.action_subjectsFragment_to_addSubjectFragment, bundle)
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fab: View = view.findViewById(R.id.fabAddSubject)
        val rvSubjects: RecyclerView = view.findViewById(R.id.rvSubjects)

        rvSubjects.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = subjectsAdapter
        }

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_subjectsFragment_to_addSubjectFragment)
        }

        setupSwipeToDelete(rvSubjects)

        viewModel.allSubjectsWithTasks.observe(viewLifecycleOwner) { subjectsWithTasks ->
            subjectsAdapter.submitList(subjectsWithTasks)
        }
    }

    private fun setupSwipeToDelete(recyclerView: RecyclerView) {
        val swipeHandler = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                rv: RecyclerView,
                vh: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val subject = subjectsAdapter.currentList[position]

                viewModel.delete(subject.subject.id)
            }
        }
        ItemTouchHelper(swipeHandler).attachToRecyclerView(recyclerView)
    }
}
