package com.example.myapplication.ui.system

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivitySystemBinding
import com.example.myapplication.data.repository.HistoryRepository
import com.example.myapplication.data.db.AppDatabase
import kotlinx.coroutines.launch

class ActivitySystem : AppCompatActivity() {
    private lateinit var binding: ActivitySystemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySystemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userLogin = intent.getStringExtra("login") ?: ""
        val historyRepository = HistoryRepository(AppDatabase.getDatabase(this).historyDao())
        val viewModel: SystemViewModel by viewModels { SystemViewModelFactory(historyRepository, userLogin) }

        lifecycleScope.launchWhenStarted {
            viewModel.result.collect { result ->
                binding.textResult.text = result
            }
        }

        binding.btnSolveSystem.setOnClickListener {
            val a1Text = binding.editTextA1.text.toString()
            val b1Text = binding.editTextB1.text.toString()
            val c1Text = binding.editTextC1.text.toString()
            val a2Text = binding.editTextA2.text.toString()
            val b2Text = binding.editTextB2.text.toString()
            val c2Text = binding.editTextC2.text.toString()
            viewModel.solve(a1Text, b1Text, c1Text, a2Text, b2Text, c2Text)
        }
    }
}