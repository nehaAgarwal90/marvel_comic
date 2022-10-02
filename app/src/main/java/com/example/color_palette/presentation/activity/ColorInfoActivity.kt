package com.example.color_palette.presentation.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.color_palette.data.model.ColorInfoModel
import com.example.color_palette.presentation.viewmodel.ColorInfoViewModel
import com.example.color_palette.databinding.ActivtiyColorInfoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import java.lang.String

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ColorInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivtiyColorInfoBinding

    private val viewModel by viewModels<ColorInfoViewModel>()

    private var currentColor: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivtiyColorInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch(Dispatchers.Main) {
            launch {
                viewModel.state.collect {
                    render(it)
                }
            }
            intent.extras?.getInt("hexcode")?.let {
                currentColor = it
                val hexColor = String.format("#%06X", 0xFFFFFF and it)
                viewModel.fetchColorInfo(hexColor)
            }
        }
        binding.ivBackIcon.setOnClickListener {
            finish()
        }
    }

    private fun render(model: ColorInfoModel?) {
        binding.apply {
            if (model != null) {
                image.setBackgroundColor(currentColor!!)
                colorValue.setBackgroundColor(getComplementaryColor(currentColor!!))
                //Glide.with(this@ColorInfoActivity).load("https://www.thecolorapi.com/id?format=svg&named=false&hex=000000").into(image)
                colorInfo = model
                complementaryColor = String.format("#%06X", 0xFFFFFF and getComplementaryColor(currentColor!!))
                executePendingBindings()

            }
        }
    }

    private fun getComplementaryColor(color: Int): Int {
        val R = 255 - (color and 255)
        val G = 255 - (color shr 8 and 255)
        val B = 255 - (color shr 16 and 255)
        val A = color shr 24 and 255

        return R + (G shl 8) + (B shl 16) + (A shl 24)
    }
}
