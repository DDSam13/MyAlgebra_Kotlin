package com.example.myapplication.ui.nod

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivityNodBinding
import com.example.myapplication.data.repository.HistoryRepository
import com.example.myapplication.data.db.AppDatabase
import kotlinx.coroutines.launch

class ActivityNod : AppCompatActivity() {
    private lateinit var binding: ActivityNodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userLogin = intent.getStringExtra("login") ?: ""
        val historyRepository = HistoryRepository(AppDatabase.getDatabase(this).historyDao())
        val viewModel: NodViewModel by viewModels { NodViewModelFactory(historyRepository, userLogin) }

        lifecycleScope.launchWhenStarted {
            viewModel.result.collect { result ->
                binding.textResult.text = result
            }
        }

        binding.btnCalculateNod.setOnClickListener {
            val num1Text = binding.editTextNum1.text.toString()
            val num2Text = binding.editTextNum2.text.toString()
            viewModel.calculate(num1Text, num2Text)
        }
    }
}


