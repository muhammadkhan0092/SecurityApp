package com.example.securityapp.modules.packages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.modules.uninstall.domain.UninstallDomainModel
import com.example.securityapp.modules.intro.domain.PackageRepository
import com.example.securityapp.modules.intro.domain.PackagesComplete
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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
    val packageData = packagesRepository.getInstalledApps()
    var list: List<String> = packageData.map { it.name }
    var job: Job? = null
    fun onAction(action: PackagesAction) {
        when (action) {
            PackagesAction.OnNextClicked -> {
                viewModelScope.launch(Dispatchers.IO) {
                    if (state.value.selectedPackages.isEmpty()) {
                        _events.emit(PackagesEvent.Toast("Select Atleast One App"))
                        return@launch
                    }
                    val selectedPackages = packageData.filter {pack->
                        state.value.selectedPackages.any {  pack.name==it}
                    }
                    val result = packagesComplete(
                        selectedPackages.map {
                            UninstallDomainModel(it.packageName)
                        }
                    )
                    when (result) {
                        is Result.Error<*> -> {
                            _events.emit(PackagesEvent.Toast(result.error))
                        }

                        is Result.Success -> _events.emit(PackagesEvent.NavigateToControlledMain)
                    }
                }
            }

            is PackagesAction.OnPackageClicked -> {
                val isPackageSelected = state.value.selectedPackages.contains(action.name)
                when (isPackageSelected) {
                    true -> {
                        list = list.filter {
                            it != action.name
                        }
                        _state.update {
                            it.copy(
                                selectedPackages = state.value.selectedPackages.filter {
                                    it != action.name
                                }
                            )
                        }
                    }
                    false -> {
                        val newList = state.value.selectedPackages.toMutableList()
                        newList.add(action.name)
                        list.toMutableList().add(action.name)
                        _state.update {
                            it.copy(
                                selectedPackages = newList.toList()
                            )
                        }
                    }
                }
            }

            is PackagesAction.OnTextChanged -> {
                _state.update {
                    it.copy(etValue = action.text)
                }
                job?.cancel()
                job = viewModelScope.launch(Dispatchers.IO) {
                    delay(1000)
                    if (action.text.isEmpty()) {
                        _state.update {
                            it.copy(allPackages = list)
                        }
                    } else {
                        val filtered = state.value.allPackages?.filter {
                            it.contains(action.text, ignoreCase = true)
                        }
                        _state.update {
                            it.copy(allPackages = filtered)
                        }
                    }
                }
            }
        }
    }

    init {
        val list = packagesRepository.getInstalledApps()
        _state.update {
            it.copy(allPackages = list.map {
                it.name
            })
        }
    }
}