package com.estaciogroup.learninglist.activities

import TopicDBHelper
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.estaciogroup.learninglist.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate o layout usando View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura o botão para abrir a KotlinListActivity
        binding.button.setOnClickListener {
            // Criando o Intent para abrir a KotlinListActivity
            val intent = Intent(this, KotlinListActivity::class.java)
            startActivity(intent)
        }
      /* binding.btnAdd.setOnClickListener(){
            startNewActivity(RegisterActivity::class.java)
        }*/

        //Aqui ele vai pra lista
        binding.btnAdd.setOnClickListener(){
            startNewActivity(ListDataActivity::class.java)
        }

    }
    private fun startNewActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }

}
