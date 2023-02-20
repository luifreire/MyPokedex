package com.example.mypokedex.pokemon.ui.camera

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecognitionViewModel: ViewModel() {
    private val _recognitionList = MutableLiveData<List<Recognition>>()
    val recognitionList = _recognitionList

    fun updateData(recognitions: List<Recognition>) {
        Log.v("pokedex", "Updating data with ${recognitions.toString()}")
        _recognitionList.postValue(recognitions)
    }
}
data class Recognition(val label: String, val confidence: Float) {
    override fun toString():String{
        return "$label / $probabilityString"
    }

    val probabilityString = String.format("%.1f%%", confidence * 100.0f)
}