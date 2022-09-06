package br.applabbs.ricettario.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.applabbs.ricettario.domain.local.models.Receita
import br.applabbs.ricettario.domain.local.usecases.ReceitaUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val context: Context,
    private val receitaUseCase: ReceitaUseCase
): ViewModel() {

    val getReceitas: LiveData<List<Receita>> get() = _getReceitas
    private val _getReceitas: MutableLiveData<List<Receita>> = MutableLiveData()

    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun getAllReceitas(){
        viewModelScope.launch {
            _getReceitas.postValue(receitaUseCase.getReceitas())
        }
    }

    fun getAllSuggestedReceitas(){
        viewModelScope.launch {
            //TODO - buscar todas as receitas sugeridas na api (retrofit)
        }
    }

}