package com.example.color_palette

import com.example.color_palette.data.model.*
import com.example.color_palette.data.repository.IColorPaletteRepository
import com.example.color_palette.presentation.viewmodel.ColorInfoViewModel
import com.example.color_palette.utils.ApiResponse
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first

// Install plugin Kotest to run this test
@ExperimentalCoroutinesApi
class ColorInfoViewModelTest : StringSpec({

    val repository: IColorPaletteRepository = spyk()
    lateinit var viewModel: ColorInfoViewModel

    beforeTest {
        viewModel = ColorInfoViewModel(repository)
    }

    "When fetchColorInfo collects data from transaction API" {
        val request = "#000000"

        val response = ColorInfoModel(
            XYZ = XYZ(X = 1, Y = 2, Z = 3),
            cmyk = Cmyk(c = 1, m = 2, y = 3, k = 4),
            hex = Hex(value = "#000000", clean = "000000"),
            hsl = Hsl(h = 1, s = 2, l = 3),
            hsv = Hsv(h = 1, s = 2, v = 3),
            rgb = Rgb(r = 1, g = 2, b = 3),
            name = Name("#000000", distance = 0, exact_match_name = true, value = "Black"),
            image = Image(bare = "", named = "")

        )

        val channel = Channel<ApiResponse<ColorInfoModel>>(1)
        coEvery {
            repository.fetchColorInfo(request)
        } returns channel.consumeAsFlow()

        viewModel.fetchColorInfo(request)

        viewModel.state.first() shouldBe null

        channel.send(com.example.color_palette.utils.Success(response))
        viewModel.state.first() shouldBe MutableStateFlow(response)
    }


})