package com.example.securityapp.modules.controlled.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.barcode.generateBarcode
import com.example.securityapp.core.data.DataStoreRepositoryImplementation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ControlledBarcodeVm @Inject constructor(
    private val datastore : DataStoreRepositoryImplementation
) : ViewModel(){
    fun onAction(action: ControlledAction) {
        when(action){
            is ControlledAction.OnTabSelected -> {
                if(action.index!=state.value.selectedIndex){
                    _state.update {
                        it.copy(selectedIndex = action.index)
                    }
                }
            }
        }
    }

    private val _state = MutableStateFlow(ControlledState())
    val state = _state.asStateFlow()
    init {
        viewModelScope.launch {
            datastore.barcode.collectLatest {
                val bitmap = generateBarcode(it)
                _state.update {
                    it.copy(bitmap = bitmap)
                }
            }
        }
    }
}