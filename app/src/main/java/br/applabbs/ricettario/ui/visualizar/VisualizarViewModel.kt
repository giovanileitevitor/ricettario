package br.applabbs.ricettario.ui.visualizar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class VisualizarViewModel(): ViewModel() {

    fun getDetalhesReceita(){
        viewModelScope.launch {
            //TODO - Buscar todas os detalhes da receita (ingredientes, fotos, passo-a-passo, comentários, etc)
        }
    }
}