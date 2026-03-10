package com.example.securityapp.modules.intro.presentation.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.modules.controlled.domain.models.UninstallDomain
import com.example.securityapp.modules.intro.domain.PackageRepository
import com.example.securityapp.modules.intro.domain.PackagesComplete
import com.example.securityapp.modules.intro.presentation.models.PackagesAction
import com.example.securityapp.modules.intro.presentation.models.PackagesEvent
import com.example.securityapp.modules.intro.presentation.models.PackagesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PackagesVm @Inject constructor(
    private val packagesComplete: PackagesComplete,
    private val packagesRepository: PackageRepository
) : ViewModel() {
    private val _state = MutableStateFlow(PackagesState())
    val state = _state.asStateFlow()
    private val _events = MutableSharedFlow<PackagesEvent>(replay = 0, extraBufferCapacity = 1)
    val events = _events.asSharedFlow()
    fun onAction(action: PackagesAction){
        when(action){
            PackagesAction.OnNextClicked -> {
                viewModelScope.launch(Dispatchers.IO){
                    val result = packagesComplete(state.value.selectedPackages.map {
                        UninstallDomain(it)
                    })
                    when(result){
                        is Result.Error<*> -> {
                            _events.emit(PackagesEvent.Toast(result.error))
                        }
                        is Result.Success -> _events.emit(PackagesEvent.NavigateToControlledMain)
                    }
                }
            }
            is PackagesAction.OnPackageClicked -> {
                Log.d("KHAN","PACKAGE CLICKED IS ${action.name}")
                val isPackageSelected = state.value.selectedPackages.contains(action.name)
                when(isPackageSelected){
                    true-> {
                        Log.d("KHAN","IN TRUE")
                        _state.update {
                            it.copy(
                                selectedPackages = state.value.selectedPackages.filter {
                                     it!=action.name
                                }
                            )
                        }
                    }
                    false -> {
                        Log.d("KHAN","IN False")
                        val newList = state.value.selectedPackages.toMutableList()
                        newList.add(action.name)
                        Log.d("KHAN","NEW LIST IS $newList")
                        _state.update {
                            it.copy(
                                selectedPackages = newList.toList()
                            )
                        }
                    }
                }
            }
        }
    }

    init {
        val list = packagesRepository.getInstalledApps()
        _state.update {
            it.copy(allPackages = list)
        }
    }
}