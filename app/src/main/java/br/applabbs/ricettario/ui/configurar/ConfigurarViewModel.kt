package br.applabbs.ricettario.ui.configurar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.applabbs.ricettario.domain.local.models.ErrorState
import br.applabbs.ricettario.domain.local.usecases.LocalDataUseCase
import br.applabbs.ricettario.domain.local.usecases.ReceitaUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ConfigurarViewModel(
    private val receitaUseCase: ReceitaUseCase,
    private val localDataUseCase: LocalDataUseCase
): ViewModel() {

    val hasTimeout: LiveData<Boolean> get() = _hasTimeout
    private val _hasTimeout: MutableLiveData<Boolean> = MutableLiveData()

    val totalContacts: LiveData<Int> get() = _totalContacts
    private val _totalContacts: MutableLiveData<Int> = MutableLiveData()

    fun startAlarm(){
        viewModelScope.launch {
            _hasTimeout.postValue(true)
            delay(3000)
            _hasTimeout.postValue(false)
        }
    }

    fun getTotalContacts(){
        viewModelScope.launch {
            _totalContacts.postValue(receitaUseCase.getAllReceitas().size ?: 0)
        }
    }

}