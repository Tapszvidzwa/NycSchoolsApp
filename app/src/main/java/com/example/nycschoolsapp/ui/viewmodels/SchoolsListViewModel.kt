package com.example.nycschoolsapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nycschoolsapp.data.repository.SchoolsRepository
import com.example.nycschoolsapp.data.api.Resource
import com.example.nycschoolsapp.ui.ScreenState
import com.example.nycschoolsapp.ui.UiState
import com.example.nycschoolsapp.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolsListViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val repository: SchoolsRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())

    val uiState: StateFlow<UiState>
        get() = _uiState

    fun retry() {
        fetchSchoolsInformation()
    }

    suspend fun getSchoolDetails(id: String) {
        viewModelScope.launch(ioDispatcher) {
            when (val response = repository.getSchoolDetails(id)) {
                is Resource.Success -> {
                    _uiState.emit(
                        UiState(
                            state = ScreenState.SUCCESS,
                            schoolName = response.data.name,
                            satMathScore = response.data.satMathScore,
                            satReadingScore = response.data.satReadingScore,
                            satWritingScore = response.data.satWritingScore,
                            schoolDescription = response.data.description
                        )
                    )
                }

                is Resource.Error -> {
                    _uiState.emit(
                        UiState(
                            state = ScreenState.ERROR,
                            errorMessage = response.message.toString()
                        )
                    )
                }
            }
        }
    }

    fun fetchSchoolsInformation() {

        _uiState.value = UiState(state = ScreenState.LOADING)

        viewModelScope.launch(ioDispatcher) {

            when (val response = repository.getSchoolsList()) {
                is Resource.Error -> {
                    _uiState.value = UiState(
                        state = ScreenState.ERROR,
                        errorMessage = response.message.toString()
                    )
                }

                is Resource.Success -> {
                    _uiState.emit(UiState(state = ScreenState.SUCCESS, list = response.data))
                }
            }
        }
    }
}