package com.example.studyflow.ui.subjects

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.studyflow.R

class AddSubjectFragment : Fragment(R.layout.fragment_add_subject) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnSaveSubject).setOnClickListener {
            findNavController().popBackStack()
        }
    }
}

