package com.example.myapplication.front

import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivityHistoryBinding
import com.example.myapplication.logic.Methods
import kotlinx.coroutines.launch

class ActivityHistory : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            val historyList = Methods.getHistory(this@ActivityHistory)
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
}