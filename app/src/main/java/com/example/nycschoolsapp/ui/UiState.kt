package com.example.nycschoolsapp.ui

import android.view.View
import com.example.nycschoolsapp.data.repository.localdatabase.SchoolModel

data class UiState(
    val list: List<SchoolModel> = emptyList(),
    val errorMessage: String = "",
    val showLoadingSpinner: Int = View.INVISIBLE,
    val state: ScreenState = ScreenState.IDLE,
    val satWritingScore: String = "" ,
    val satReadingScore: String = "",
    val satMathScore: String = "",
    val schoolDescription: String = "",
    val schoolName: String = ""
)

enum class ScreenState {
    IDLE,
    LOADING,
    SUCCESS,
    ERROR
}