package com.example.template

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.template.MainActivityUiState.Loading
import com.example.template.MainActivityUiState.Success
import com.example.template.core.datastore.TemplatePreferencesDataSource
import com.example.template.core.datastore.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    preferences: TemplatePreferencesDataSource
) : ViewModel() {

    val uiState: StateFlow<MainActivityUiState> = preferences.userData.map {
        Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = Loading,
        started = SharingStarted.WhileSubscribed(5_000),
    )

}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val userData: UserData) : MainActivityUiState
}
