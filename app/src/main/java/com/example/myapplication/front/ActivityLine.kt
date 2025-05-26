package com.example.myapplication.front

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityLineBinding
import com.example.myapplication.logic.Math

class ActivityLine : AppCompatActivity() {
    private lateinit var binding: ActivityLineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSolveLine.setOnClickListener {
            val aText = binding.editTextA.text.toString()
            val bText = binding.editTextB.text.toString()

            val result = try {
                val a = aText.toDouble()
                val b = bText.toDouble()
                Math.solveLinearEquation(a, b)
            } catch (e: Exception) {
                "Ошибка ввода!"
            }
            binding.textResult.text = result
        }

        lifecycleScope.launch {
            Methods.saveOperation(
                this@ActivityLine,
                OperationType.LINE,
                listOf(a, b),
                "x = $x"
            )
        }
    }
}