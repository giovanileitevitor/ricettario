package br.applabbs.ricettario.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.applabbs.ricettario.domain.local.models.Receita
import br.applabbs.ricettario.domain.local.models.Step
import br.applabbs.ricettario.domain.local.usecases.ReceitaUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val context: Context,
    private val receitaUseCase: ReceitaUseCase
): ViewModel() {

    val getReceitas: LiveData<List<Receita>> get() = _getReceitas
    private val _getReceitas: MutableLiveData<List<Receita>> = MutableLiveData()

    val getStepsReceita: LiveData<List<Step>> get() = _getStepsReceita
    private val _getStepsReceita: MutableLiveData<List<Step>> = MutableLiveData()

    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()

    val isError: LiveData<Boolean> get() = _isError
    private val _isError: MutableLiveData<Boolean> = MutableLiveData()

    fun getAllReceitas(){
        viewModelScope.launch(Dispatchers.IO) {
            _getReceitas.postValue(receitaUseCase.getAllReceitas())
        }
    }

    fun getStepsReceita(idReceita: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _getStepsReceita.postValue(receitaUseCase.getStepsFromIdReceita(idReceita = idReceita))
        }
    }

    fun setReceitaAsFavorite(idReceita: Int){
        viewModelScope.launch {
            receitaUseCase.setReceitaAsFavorite(idReceita = idReceita)
            _getReceitas.postValue(receitaUseCase.getAllReceitas())
        }
    }

    fun deleteReceita(idReceita: Int){
        viewModelScope.launch {
            receitaUseCase.deleteReceita(idReceita = idReceita)
            _getReceitas.postValue(receitaUseCase.getAllReceitas())
        }
    }

    fun searchReceita(titleReceita: String){
        viewModelScope.launch {
            //_getReceitas.postValue(receitaUseCase.searchReceita(titleReceita = titleReceita))
        }
    }

    fun getAllSuggestedReceitas(){
        viewModelScope.launch {
            //TODO - buscar todas as receitas sugeridas na api (retrofit)
        }
    }

}