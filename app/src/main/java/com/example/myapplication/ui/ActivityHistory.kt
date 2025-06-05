package com.example.myapplication.ui.history

import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivityHistoryBinding
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.HistoryRepository
import com.example.myapplication.data.db.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ActivityHistory : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userLogin = intent.getStringExtra("login") ?: return
        val repository = HistoryRepository(AppDatabase.getDatabase(this).historyDao())
        val viewModel: HistoryViewModel by viewModels { HistoryViewModelFactory(repository) }

        lifecycleScope.launchWhenStarted {
            viewModel.history.collect { historyList ->
                binding.layoutHistory.removeAllViews()
                for (item in historyList) {
                    val textView = TextView(this@ActivityHistory)
                    textView.text = item
                    textView.textSize = 16f
                    textView.setTextColor(getColor(android.R.color.black))
                    textView.layoutParams = ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    ).apply {
                        bottomMargin = 12
                    }
                    binding.layoutHistory.addView(textView)
                }
            }
        }
        viewModel.loadHistory(userLogin)
    }
}