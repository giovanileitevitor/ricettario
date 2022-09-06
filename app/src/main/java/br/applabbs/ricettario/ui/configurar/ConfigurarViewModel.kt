package br.applabbs.ricettario.ui.configurar

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.applabbs.ricettario.domain.local.usecases.ReceitaUseCase
import kotlinx.coroutines.launch

class ConfigurarViewModel(
    private val context: Context,
    private val receitaUseCase: ReceitaUseCase
): ViewModel() {

    fun getDetalhesReceita(){
        viewModelScope.launch {
            //TODO - Buscar todas os detalhes da receita (ingredientes, fotos, passo-a-passo, comentários, etc)
        }
    }
}