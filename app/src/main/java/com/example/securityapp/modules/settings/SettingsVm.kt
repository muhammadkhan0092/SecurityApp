package com.example.securityapp.modules.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.modules.logout.Logout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsVm @Inject constructor(
    private val logout: Logout
) : ViewModel(){
    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<SettingsEvent>(replay = 0, extraBufferCapacity = 1)
    val events = _events.asSharedFlow()

    fun onAction(action: SettingsAction){
        when(action){
            SettingsAction.OnLogoutClicked ->{
                _state.update {
                    it.copy(isLoading = true)
                }
                viewModelScope.launch {
                    logout()
                    _state.update {
                        it.copy(isLoading = false)
                    }
                    _events.emit(SettingsEvent.NavigateToIntro)
                }
            }
        }
    }
}