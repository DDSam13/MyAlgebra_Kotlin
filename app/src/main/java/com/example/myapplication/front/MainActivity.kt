package com.example.myapplication.front

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLine.setOnClickListener {
            startActivity(Intent(this, ActivityLine::class.java))
        }

        binding.btnSquare.setOnClickListener {
            startActivity(Intent(this, ActivitySquare::class.java))
        }

        binding.btnNok.setOnClickListener {
            startActivity(Intent(this, ActivityNok::class.java))
        }

        binding.btnNod.setOnClickListener {
            startActivity(Intent(this, ActivityNod::class.java))
        }

        binding.btnSystem.setOnClickListener {
            startActivity(Intent(this, ActivitySystem::class.java))
        }
    }
}