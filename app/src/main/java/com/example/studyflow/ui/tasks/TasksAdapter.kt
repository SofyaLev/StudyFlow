package com.example.studyflow.ui.tasks

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studyflow.R
import com.example.studyflow.data.entities.TaskEntity


class TasksAdapter (
    private val onTaskClick: (TaskEntity) -> Unit,
    private val onStatusChanged: (TaskEntity) -> Unit
) : ListAdapter<TaskEntity, TasksAdapter.TaskViewHolder>(TaskDiffCallback()) {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvTaskTitle)
        val deadline: TextView = view.findViewById(R.id.tvTaskDeadline)
        val cbCompleted: CheckBox = view.findViewById(R.id.cbCompleted)

        fun bind(
            task: TaskEntity,
            onClick: (TaskEntity) -> Unit,
            onStatusChanged: (TaskEntity) -> Unit
        ) {
            title.text = task.title
            deadline.text = task.deadline

            cbCompleted.isChecked = task.isCompleted
            updateTextStyle(task.isCompleted)

            itemView.setOnClickListener { onClick(task) }

            cbCompleted.setOnCheckedChangeListener { _, isChecked ->
                updateTextStyle(isChecked)
                onStatusChanged(task.copy(isCompleted = isChecked))
            }
        }

        private fun updateTextStyle(isCompleted: Boolean) {
            if (isCompleted) {
                title.paintFlags = title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                title.alpha = 0.5f
            } else {
                title.paintFlags = title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                title.alpha = 1.0f
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position), onTaskClick, onStatusChanged)
    }

    class TaskDiffCallback: DiffUtil.ItemCallback<TaskEntity>() {
        override fun areItemsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
            return oldItem == newItem
        }
    }
}
