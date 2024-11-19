package com.estaciogroup.learninglist.adapters

import TopicDBHelper
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.estaciogroup.learninglist.R
import com.estaciogroup.learninglist.activities.Topic

class TopicAdapter(
    private var topics: List<Topic>, context: Context

): RecyclerView.Adapter<TopicAdapter.TopicViewHolder>() {

    // ViewHolder que irá conter as referências dos componentes do layout
    class TopicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBoxItem)
    }

    // Cria novos itens de visualização, são replicados conforme o tamanho da lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_trilha_kotlin, parent, false)
        return TopicViewHolder(view)
    }

    override fun getItemCount() = topics.size // Conta itens da lista

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val topicItem = topics[position]
        holder.checkBox.text = topicItem.name  // Define o nome do tópico como texto do CheckBox
        holder.checkBox.isChecked = topicItem.isChecked

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            val db = TopicDBHelper(holder.itemView.context)
            db.updateTaskCompletion(topicItem.id, isChecked)

            topics = topics.toMutableList().apply { this[position] = topicItem.copy(isChecked = isChecked) }

            if (holder.checkBox.isChecked != isChecked) {
                Handler(Looper.getMainLooper()).post {
                    notifyItemChanged(position)
                }
            }
        }
    }

    fun refresh(newTasks: List<Topic>) {
        topics = newTasks
        notifyDataSetChanged()
    }

}




