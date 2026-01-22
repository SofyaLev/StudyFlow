package com.example.studyflow.ui.tasks

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.studyflow.R

class AddTaskFragment : Fragment(R.layout.fragment_add_task) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnSaveTask).setOnClickListener {
            findNavController().popBackStack()
        }
    }
}