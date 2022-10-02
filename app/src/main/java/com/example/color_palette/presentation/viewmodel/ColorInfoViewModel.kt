package com.example.color_palette.presentation.viewmodel

import com.example.color_palette.data.repository.IColorPaletteRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.color_palette.data.model.ColorInfoModel
import com.example.color_palette.utils.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ColorInfoViewModel @Inject constructor(private val repository: IColorPaletteRepository) : ViewModel() {
    val state = MutableStateFlow<ColorInfoModel?>(null)

    suspend fun fetchColorInfo(hex: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchColorInfo(hex).collect {
                when (it) {
                    is Success -> {
                        state.value = it.data
                    }
                }
            }
        }
    }
}