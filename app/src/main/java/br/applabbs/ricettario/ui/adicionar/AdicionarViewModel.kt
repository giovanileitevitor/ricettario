package br.applabbs.ricettario.ui.adicionar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AdicionarViewModel(): ViewModel() {

    fun addNovaReceita(){
        viewModelScope.launch {
            //TODO - faz a gravação no DB de uma nova receita
        }
    }
}