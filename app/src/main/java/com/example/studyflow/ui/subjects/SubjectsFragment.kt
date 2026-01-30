package com.example.studyflow.ui.subjects

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fab: View = view.findViewById(R.id.fabAddSubject)
        val rvSubjects: RecyclerView = view.findViewById(R.id.rvSubjects)

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_subjectsFragment_to_addSubjectFragment)
        }

        rvSubjects.layoutManager = LinearLayoutManager(requireContext())

        viewModel.allSubjects.observe(viewLifecycleOwner) { subjectEntities ->
            val subjects = subjectEntities.map { entity ->
                SubjectItem(
                    title = entity.name,
                    totalTasks = 0,
                    completedTasks = 0
                )
            }

            rvSubjects.adapter = SubjectsAdapter(
                items = subjects,
                onItemClick = { subjectItem ->
                    val bundle = Bundle().apply {
                        putString("subjectName", subjectItem.title)
                    }

                    findNavController().navigate(
                        R.id.action_subjectsFragment_to_tasksFragment,
                        bundle
                    )
                }
            )
        }
    }
}
