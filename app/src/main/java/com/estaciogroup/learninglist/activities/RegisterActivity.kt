package com.estaciogroup.learninglist.activities

import TopicDBHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.estaciogroup.learninglist.databinding.ActivityRegisterBinding

class RegisterActivity : ComponentActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var db: TopicDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Recuperando os componentes do layout e instanciandoocontrolador:
        db = TopicDBHelper(this)

        binding.btnRegister.setOnClickListener {
            val myTopicString = binding.edtTopic.text.toString()

            val task = Topic(0, myTopicString, false, "task")
            db.insertTask(task)
            finish()
            Toast.makeText(this, "Tarefa adicionada com sucesso", Toast.LENGTH_SHORT).show()
        }


        binding.btnListAll.setOnClickListener(){
            startNewActivityList(ListDataActivity::class.java)
        }
        binding.btnUpdate.setOnClickListener(){
            startNewActivityList(ListDataActivity::class.java)
        }


    }
    private fun startNewActivityList(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun clearViews() {
        binding.edtTopic.text.clear()
    }
}