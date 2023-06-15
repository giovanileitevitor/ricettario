package br.applabbs.ricettario.di

import br.applabbs.ricettario.data.local.db.LocalDB
import br.applabbs.ricettario.data.local.repositories.LocalRepository
import br.applabbs.ricettario.data.local.repositories.LocalRepositoryImpl
import br.applabbs.ricettario.domain.local.usecases.InventarioUseCase
import br.applabbs.ricettario.domain.local.usecases.InventarioUseCaseImpl
import br.applabbs.ricettario.domain.local.usecases.LocalDataUseCase
import br.applabbs.ricettario.domain.local.usecases.LocalDataUseCaseImpl
import br.applabbs.ricettario.domain.local.usecases.ReceitaUseCase
import br.applabbs.ricettario.domain.local.usecases.ReceitaUseCaseImpl
import br.applabbs.ricettario.ui.adicionar.AdicionarViewModel
import br.applabbs.ricettario.ui.configurar.ConfigurarViewModel
import br.applabbs.ricettario.ui.exibir.ExibirViewModel
import br.applabbs.ricettario.ui.home.HomeViewModel
import br.applabbs.ricettario.ui.splash.SplashViewModel
import br.applabbs.ricettario.uiInventtario.home.InventarioViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModules{

    val viewModelModule = module {

        viewModel { SplashViewModel(

        ) }

        viewModel { HomeViewModel(
            receitaUseCase = get()
        ) }

        viewModel { ConfigurarViewModel(
            receitaUseCase = get(),
            localDataUseCase = get()
        ) }

        viewModel { AdicionarViewModel(
            context = get(),
            receitaUseCase = get()
        ) }

        viewModel { ExibirViewModel(
            receitaUseCase = get()
        ) }

        viewModel { InventarioViewModel(
            context = get(),
            inventarioUseCase = get()
        ) }

    }

    val localUseCasesModule = module {
        factory<ReceitaUseCase> {
            ReceitaUseCaseImpl(
                localRepository = get()
            )
        }

        factory<InventarioUseCase> {
            InventarioUseCaseImpl(
                localRepository = get()
            )
        }

        factory<LocalRepository>{
            LocalRepositoryImpl(
                stepDao = get(),
                receitaDao = get(),
                fotoDao = get(),
                registroDao = get()
            )
        }
        factory<LocalDataUseCase> {
            LocalDataUseCaseImpl()
        }
    }

    val localRepositoriesModule = module {
        single { LocalDB.getDatabase(context = get())}
        single { get<LocalDB>().receitaDao()}
        single { get<LocalDB>().stepDao()}
        single { get<LocalDB>().fotoDao()}
        single { get<LocalDB>().registroDao()}
    }

}