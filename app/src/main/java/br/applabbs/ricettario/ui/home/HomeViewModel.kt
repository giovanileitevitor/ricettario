package br.applabbs.ricettario.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HomeViewModel(): ViewModel() {

    fun getAllReceitas(){
        viewModelScope.launch {
            //TODO - buscar todas as receitas salvas em memória (room)
        }
    }

    fun getAllSuggestedReceitas(){
        viewModelScope.launch {
            //TODO - buscar todas as receitas sugeridas na api (retrofit)
        }
    }

}