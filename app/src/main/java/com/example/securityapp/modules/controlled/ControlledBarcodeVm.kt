package com.example.securityapp.modules.controlled

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.barcode.generateBarcode
import com.example.securityapp.core.data.DataStoreRepositoryImplementation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ControlledBarcodeVm @Inject constructor(
    private val datastore : DataStoreRepositoryImplementation
) : ViewModel(){
    private val _bitmap = MutableStateFlow<Bitmap?>(null)
    val bitmap = _bitmap.asStateFlow()
    init {
        viewModelScope.launch {
            datastore.barcode.collectLatest {
                Log.d("KHAN","BARCODE IS ")
                val bitmap = generateBarcode(it)
                _bitmap.value = bitmap
            }
        }
    }
}