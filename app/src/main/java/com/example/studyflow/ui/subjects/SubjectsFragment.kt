package com.example.studyflow.ui.subjects

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studyflow.R

class SubjectsFragment : Fragment(R.layout.fragment_subjects) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val subjects = listOf(
            SubjectItem("Mobile Development", 10, 4),
            SubjectItem("Databases", 8, 6),
            SubjectItem("Computer Networks", 12, 3)
        )

        val fab: View = view.findViewById(R.id.fabAddSubject)
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_subjectsFragment_to_addSubjectFragment)
        }

        val rvSubjects: RecyclerView = view.findViewById(R.id.rvSubjects)

        rvSubjects.layoutManager = LinearLayoutManager(requireContext())
        rvSubjects.adapter = SubjectsAdapter(
            items = subjects,
            onItemClick = { subject ->
                val bundle = Bundle().apply {
                    putString("subjectName", subject.title)
                }

                findNavController().navigate(
                    R.id.action_subjectsFragment_to_tasksFragment,
                    bundle
                )
            }
        )
    }
}
