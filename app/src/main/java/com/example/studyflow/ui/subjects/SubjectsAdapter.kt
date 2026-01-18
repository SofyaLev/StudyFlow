package com.example.studyflow.ui.subjects

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studyflow.R

class SubjectsAdapter (
    private val items: List<SubjectItem>
) : RecyclerView.Adapter<SubjectsAdapter.SubjectViewHolder>() {

    class SubjectViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvTitle)
        val progress: ProgressBar = view.findViewById(R.id.progress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_subject, parent, false)
        return SubjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.title
        holder.progress.progress = (item.completedTasks * 100) / item.totalTasks
    }

    override fun getItemCount() = items.size
}