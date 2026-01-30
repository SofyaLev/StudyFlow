package com.example.studyflow.ui.subjects

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
import com.example.studyflow.data.entities.SubjectEntity
import com.example.studyflow.data.repositories.SubjectRepository
import kotlin.getValue

class AddSubjectFragment : Fragment(R.layout.fragment_add_subject) {

    private val viewModel: SubjectViewModel by viewModels {
        val database = AppDatabase.getDatabase(requireContext())
        val repository = SubjectRepository(database.subjectDao())
        SubjectViewModelFactory(repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etName = view.findViewById<EditText>(R.id.tvSubjectTitle)
        val btnSave = view.findViewById<Button>(R.id.btnSaveSubject)

        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()

            if (name.isNotEmpty()) {
                val newSubject = SubjectEntity(name = name, color = 0xFFE8DFFF.toInt())

                viewModel.insert(newSubject)

                findNavController().popBackStack()

                Toast.makeText(requireContext(), "Subject saved", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Enter the name of the subject", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

