package com.example.myapplication.front

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivitySystemBinding
import com.example.myapplication.logic.Math

class ActivitySystem : AppCompatActivity() {
    private lateinit var binding: ActivitySystemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySystemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSolveSystem.setOnClickListener {
            val a1Text = binding.editTextA1.text.toString()
            val b1Text = binding.editTextB1.text.toString()
            val c1Text = binding.editTextC1.text.toString()
            val a2Text = binding.editTextA2.text.toString()
            val b2Text = binding.editTextB2.text.toString()
            val c2Text = binding.editTextC2.text.toString()

            val result = try {
                val a1 = a1Text.toDouble()
                val b1 = b1Text.toDouble()
                val c1 = c1Text.toDouble()
                val a2 = a2Text.toDouble()
                val b2 = b2Text.toDouble()
                val c2 = c2Text.toDouble()
                Math.solveSystem(a1, b1, c1, a2, b2, c2)
            } catch (e: Exception) {
                "Ошибка ввода!"
            }
            binding.textResult.text = result
        }
    }
}