package com.estaciogroup.learninglist.activities

import TopicDBHelper
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.estaciogroup.learninglist.adapters.CustomAdapter
import com.estaciogroup.learninglist.databinding.ActivityListDataBinding

class ListDataActivity : ComponentActivity() {
    private lateinit var binding: ActivityListDataBinding
    private lateinit var db: TopicDBHelper
    private lateinit var customAdapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityListDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TopicDBHelper(this)
        customAdapter = CustomAdapter(db.getAllTaks(), this)

        binding.taskRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.taskRecyclerView.adapter = customAdapter

        // Configura o botão para abrir a RegisterActivity
        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

        }
    }
        override fun onResume() {
            super.onResume()
            customAdapter.refresh(db.getAllTaks())
        }
    }


