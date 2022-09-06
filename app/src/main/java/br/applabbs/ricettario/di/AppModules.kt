package br.applabbs.ricettario.di

import br.applabbs.ricettario.domain.local.usecases.ReceitaUseCase
import br.applabbs.ricettario.domain.local.usecases.ReceitaUseCaseImpl
import br.applabbs.ricettario.ui.adicionar.AdicionarViewModel
import br.applabbs.ricettario.ui.home.HomeViewModel
import br.applabbs.ricettario.ui.configurar.ConfigurarViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModules{

    val viewModelModule = module(override = true){
        viewModel { HomeViewModel(
            context = get(),
            receitaUseCase = get()
        ) }
        viewModel { ConfigurarViewModel(
            context = get(),
            receitaUseCase = get()
        ) }
        viewModel { AdicionarViewModel(
            context = get(),
            receitaUseCase = get()
        ) }
    }

    val localUseCasesModule = module(override = true) {
        single<ReceitaUseCase> { ReceitaUseCaseImpl() }
    }

    val remoteUseCasesModule = module(override = true) {

    }
}