package com.example.color_palette.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.color_palette.databinding.ActivityMainBinding

class PalettePickerActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
