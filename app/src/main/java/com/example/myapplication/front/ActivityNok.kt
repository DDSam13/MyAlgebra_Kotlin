package com.example.myapplication.front

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityNokBinding
import com.example.myapplication.logic.Math

class ActivityNok : AppCompatActivity() {
    private lateinit var binding: ActivityNokBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNokBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalculateNok.setOnClickListener {
            val num1Text = binding.editTextNum1.text.toString()
            val num2Text = binding.editTextNum2.text.toString()

            val result = try {
                val a = num1Text.toInt()
                val b = num2Text.toInt()
                Math.nok(a, b)
            } catch (e: Exception) {
                "Ошибка ввода!"
            }
            binding.textResult.text = result
        }
    }
}