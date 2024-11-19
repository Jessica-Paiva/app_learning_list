package com.estaciogroup.learninglist.activities


import TopicDBHelper
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.estaciogroup.learninglist.databinding.ActivityUpdateDeleteBinding

class UpdateDeleteActivity : ComponentActivity() {
    private lateinit var binding: ActivityUpdateDeleteBinding
    private lateinit var db: TopicDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = TopicDBHelper(this)

        val code = intent.getStringExtra("TAG_CODE").toString()

        val cursor = db.getTopicById(code.toInt())

        cursor.apply {
            // Preenchendo o EditText com o valor do banco de dados
            binding.edtUpdActTitle.setText(cursor.name)
        }

        // Atualizando o tópico
        binding.btnUpdActUpdate.setOnClickListener {
            db.updateTask(
                binding.edtUpdActTitle.text.toString(),
                code.toInt()
                // Usando o texto do EditText para a atualização
            )
            finish()
        }

        // Deletando o item
        binding.btnUpdActDelete.setOnClickListener {
            db.deleteTask(code.toInt())
            finish()
        }
    }
}
