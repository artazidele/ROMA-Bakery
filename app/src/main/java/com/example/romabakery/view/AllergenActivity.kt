package com.example.romabakery.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.romabakery.R
import com.example.romabakery.databinding.ActivityAllergenBinding

class AllergenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllergenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllergenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}