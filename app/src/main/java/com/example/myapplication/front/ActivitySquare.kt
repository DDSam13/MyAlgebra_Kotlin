package com.example.myapplication.front

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivitySquareBinding
import com.example.myapplication.logic.Math

class ActivitySquare : AppCompatActivity() {
    private lateinit var binding: ActivitySquareBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySquareBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSolveSquare.setOnClickListener {
            val aText = binding.editTextA.text.toString()
            val bText = binding.editTextB.text.toString()
            val cText = binding.editTextC.text.toString()

            val result = try {
                val a = aText.toDouble()
                val b = bText.toDouble()
                val c = cText.toDouble()
                Math.solveQuadraticEquation(a, b, c)
            } catch (e: Exception) {
                "Ошибка ввода!"
            }
            binding.textResult.text = result
        }
    }
}