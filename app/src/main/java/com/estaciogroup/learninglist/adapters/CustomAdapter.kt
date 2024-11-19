package com.estaciogroup.learninglist.adapters

import TopicDBHelper
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.estaciogroup.learninglist.R
import com.estaciogroup.learninglist.activities.Topic
import com.estaciogroup.learninglist.activities.UpdateDeleteActivity

class CustomAdapter(
    private var tasks: List<Topic>, context: Context

): RecyclerView.Adapter<CustomAdapter.TaskViewHolder>() {
    class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val txtTitle: TextView = itemView.findViewById(R.id.itemTxtTitle)
        val taskCheckbox: CheckBox = itemView.findViewById(R.id.taskCheckbox)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_my_list, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.txtTitle.text = task.name
        holder.taskCheckbox.isChecked = task.isChecked
        holder.taskCheckbox.setOnCheckedChangeListener(null)

        holder.taskCheckbox.setOnCheckedChangeListener { _, isChecked ->
            val db = TopicDBHelper(holder.itemView.context)
            db.updateTaskCompletion(task.id, isChecked)

            tasks = tasks.toMutableList().apply { this[position] = task.copy(isChecked = isChecked) }

            if (holder.taskCheckbox.isChecked != isChecked) {
                Handler(Looper.getMainLooper()).post {
                    notifyItemChanged(position)
                }
            }
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateDeleteActivity::class.java)
            intent.putExtra("TAG_CODE", task.id.toString())
            holder.itemView.context.startActivity(intent)
        }
    }
    fun refresh(newTasks: List<Topic>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}
