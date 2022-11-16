package br.applabbs.ricettario.ui.exibir

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.applabbs.ricettario.domain.local.models.Foto
import br.applabbs.ricettario.domain.local.usecases.ReceitaUseCase
import kotlinx.coroutines.launch

class ExibirViewModel(
    private val receitaUseCase: ReceitaUseCase
): ViewModel() {

    val getFotos: LiveData<List<Foto>> get() = _getFotos
    private val _getFotos: MutableLiveData<List<Foto>> = MutableLiveData()
    private lateinit var fotos : List<Foto>

    fun getFotosReceita(idReceita: Int){
        viewModelScope.launch {
           _getFotos.postValue(receitaUseCase.getFotos(isTemporary = true))
        }
    }

}