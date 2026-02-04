package com.example.studyflow.ui.subjects

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studyflow.R
import com.example.studyflow.data.entities.SubjectEntity
import com.example.studyflow.data.entities.SubjectWithTasks

class SubjectsAdapter (
    private val onItemClick: (SubjectEntity) -> Unit,
    private val onLongClick: (SubjectEntity) -> Unit
) : ListAdapter<SubjectWithTasks, SubjectsAdapter.SubjectViewHolder>(SubjectDiffCallback()) {

    class SubjectViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvTitle)
        val progress: ProgressBar = view.findViewById(R.id.progress)

        fun bind(item: SubjectWithTasks, onClick: (SubjectEntity) -> Unit, onLong: (SubjectEntity) -> Unit) {
            title.text = item.subject.name

            val totalTasks = item.tasks.size
            val completedTasks = item.tasks.count { it.isCompleted }

            val progressPercent = if (totalTasks > 0) {
                (completedTasks * 100) / totalTasks
            } else {
                0
            }

            progress.progress = progressPercent

            itemView.setOnClickListener { onClick(item.subject) }
            itemView.setOnLongClickListener {
                onLong(item.subject)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_subject, parent, false)
        return SubjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick, onLongClick)
    }

    class SubjectDiffCallback : DiffUtil.ItemCallback<SubjectWithTasks>() {
        override fun areItemsTheSame(oldItem: SubjectWithTasks, newItem: SubjectWithTasks): Boolean {
            return oldItem.subject.id == newItem.subject.id
        }

        override fun areContentsTheSame(oldItem: SubjectWithTasks, newItem: SubjectWithTasks): Boolean {
            return oldItem == newItem
        }
    }
}