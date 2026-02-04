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

class SubjectsAdapter (
    private val onItemClick: (SubjectEntity) -> Unit,
    private val onLongClick: (SubjectEntity) -> Unit
) : ListAdapter<SubjectEntity, SubjectsAdapter.SubjectViewHolder>(SubjectDiffCallback()) {

    class SubjectViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvTitle)
        val progress: ProgressBar = view.findViewById(R.id.progress)

        fun bind(subject: SubjectEntity, onClick: (SubjectEntity) -> Unit, onLong: (SubjectEntity) -> Unit) {
            title.text = subject.name
            progress.progress = 0

            itemView.setOnClickListener { onClick(subject) }
            itemView.setOnLongClickListener {
                onLong(subject)
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

    class SubjectDiffCallback : DiffUtil.ItemCallback<SubjectEntity>() {
        override fun areItemsTheSame(oldItem: SubjectEntity, newItem: SubjectEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SubjectEntity, newItem: SubjectEntity): Boolean {
            return oldItem == newItem
        }
    }
}