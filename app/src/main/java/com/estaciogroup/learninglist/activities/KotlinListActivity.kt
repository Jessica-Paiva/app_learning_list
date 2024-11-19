package com.estaciogroup.learninglist.activities

import TopicDBHelper
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.LinearLayoutManager
import com.estaciogroup.learninglist.adapters.CustomAdapter
import com.estaciogroup.learninglist.adapters.TopicAdapter
import com.estaciogroup.learninglist.databinding.ActivityKotlinListBinding

//
class KotlinListActivity : ComponentActivity() {

    private lateinit var binding: ActivityKotlinListBinding
    private lateinit var db: TopicDBHelper
    private lateinit var topicAdapter: TopicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Inflate o layout usando View Binding
        binding = ActivityKotlinListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TopicDBHelper(this)
        topicAdapter = TopicAdapter(db.loadTopics(), this)

        binding.recyclerViewTopics.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewTopics.adapter = topicAdapter
    }

    override fun onResume() {
        super.onResume()
        topicAdapter.refresh(db.loadTopics())
    }

}
