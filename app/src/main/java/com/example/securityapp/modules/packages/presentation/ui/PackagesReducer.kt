package com.example.securityapp.modules.packages.presentation.ui

import com.example.securityapp.modules.packages.presentation.models.PackagesAction
import com.example.securityapp.modules.packages.presentation.models.PackagesState
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class PackagesReducer @Inject constructor() {
    fun onPackageClicked(action: PackagesAction.OnPackageClicked, state: PackagesState): PackagesState {
        val isPackageSelected = state.selectedPackages.contains(action.name)
        return when (isPackageSelected) {
            true -> {
                val newStringList = state.fullStringList.filter {
                    it != action.name
                }
                state.copy(
                    selectedPackages = state.selectedPackages.filter {
                        it != action.name
                    }
                )
            }

            false -> {
                val newList = state.selectedPackages.toMutableList()
                newList.add(action.name)
                val newStringList = state.fullStringList.toMutableList()
                newStringList.add(action.name)
                state.copy(
                    selectedPackages = newList.toList()
                )
            }
        }
    }
    fun onQueryChanged(action: PackagesAction.OnTextChanged,state: PackagesState): PackagesState {
        return if (action.text.isEmpty()) {
            state.copy(allPackages = state.fullStringList)
        } else {
            val filtered = state.allPackages?.filter {
                it.contains(action.text, ignoreCase = true)
            }
            state.copy(allPackages = filtered)
        }
    }
}
