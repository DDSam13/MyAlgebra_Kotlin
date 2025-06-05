package com.example.myapplication.ui.line

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivityLineBinding
import com.example.myapplication.data.repository.HistoryRepository
import com.example.myapplication.data.db.AppDatabase
import kotlinx.coroutines.launch

class ActivityLine : AppCompatActivity() {
    private lateinit var binding: ActivityLineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userLogin = intent.getStringExtra("login") ?: ""
        val historyRepository = HistoryRepository(AppDatabase.getDatabase(this).historyDao())
        val viewModel: LineViewModel by viewModels { LineViewModelFactory(historyRepository, userLogin) }

        lifecycleScope.launchWhenStarted {
            viewModel.result.collect { result ->
                binding.textResult.text = result
            }
        }

        binding.btnSolveLine.setOnClickListener {
            val aText = binding.editTextA.text.toString()
            val bText = binding.editTextB.text.toString()
            viewModel.solve(aText, bText)
        }
    }
}