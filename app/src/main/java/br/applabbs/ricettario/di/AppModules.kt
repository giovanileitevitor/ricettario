package br.applabbs.ricettario.di

import br.applabbs.ricettario.ui.adicionar.AdicionarViewModel
import br.applabbs.ricettario.ui.home.HomeViewModel
import br.applabbs.ricettario.ui.visualizar.VisualizarViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module

@KoinApiExtension
object AppModules{

    val viewModelModule = module{
        viewModel { HomeViewModel() }
        viewModel { VisualizarViewModel() }
        viewModel { AdicionarViewModel() }
    }
}