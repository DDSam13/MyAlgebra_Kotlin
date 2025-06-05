package com.example.myapplication.ui.square

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivitySquareBinding
import com.example.myapplication.data.repository.HistoryRepository
import com.example.myapplication.data.db.AppDatabase
import kotlinx.coroutines.launch

class ActivitySquare : AppCompatActivity() {
    private lateinit var binding: ActivitySquareBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySquareBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userLogin = intent.getStringExtra("login") ?: ""
        val historyRepository = HistoryRepository(AppDatabase.getDatabase(this).historyDao())
        val viewModel: SquareViewModel by viewModels { SquareViewModelFactory(historyRepository, userLogin) }

        lifecycleScope.launchWhenStarted {
            viewModel.result.collect { result ->
                binding.textResult.text = result
            }
        }

        binding.btnSolveSquare.setOnClickListener {
            val aText = binding.editTextA.text.toString()
            val bText = binding.editTextB.text.toString()
            val cText = binding.editTextC.text.toString()
            viewModel.solve(aText, bText, cText)
        }
    }
}