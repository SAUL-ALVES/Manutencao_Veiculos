package com.example.manutencaodeveiculos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manutencaodeveiculos.data.Posto
import com.example.manutencaodeveiculos.network.RetrofitInstance
import kotlinx.coroutines.launch

class PostosViewModel : ViewModel() {

    private val _postos = MutableLiveData<List<Posto>>()
    val postos: LiveData<List<Posto>> = _postos

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        fetchPostos()
    }

    private fun fetchPostos() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getPostos()
                if (response.isSuccessful) {
                    _postos.postValue(response.body())
                    _errorMessage.postValue(null)
                } else {
                    _errorMessage.postValue("Erro: ${response.message()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Falha na conexão: ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}