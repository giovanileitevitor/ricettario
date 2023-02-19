package br.applabbs.ricettario.di

import br.applabbs.pixells.ui.home.HomePixelViewModel
import br.applabbs.pixells.ui.splash.SplashPixelViewModel
import br.applabbs.ricettario.data.local.db.LocalDB
import br.applabbs.ricettario.data.local.repositories.LocalRepository
import br.applabbs.ricettario.data.local.repositories.LocalRepositoryImpl
import br.applabbs.ricettario.domain.local.usecases.LocalDataUseCase
import br.applabbs.ricettario.domain.local.usecases.LocalDataUseCaseImpl
import br.applabbs.ricettario.domain.local.usecases.ReceitaUseCase
import br.applabbs.ricettario.domain.local.usecases.ReceitaUseCaseImpl
import br.applabbs.ricettario.ui.adicionar.AdicionarViewModel
import br.applabbs.ricettario.ui.configurar.ConfigurarViewModel
import br.applabbs.ricettario.ui.exibir.ExibirViewModel
import br.applabbs.ricettario.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModules{

    val viewModelModule = module {
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

        viewModel { SplashPixelViewModel(

        ) }

        viewModel { HomePixelViewModel(

        ) }
    }

    val localUseCasesModule = module {
        factory<ReceitaUseCase> {
            ReceitaUseCaseImpl(
                localRepository = get()
            )
        }
        factory<LocalRepository>{
            LocalRepositoryImpl(
                stepDao = get(),
                receitaDao = get(),
                fotoDao = get()
            )
        }
        factory<LocalDataUseCase> {
            LocalDataUseCaseImpl()
        }
    }

    val localRepositoriesModule = module {
        single { LocalDB.createDatabase(context = get())}
        single { get<LocalDB>().receitaDao()}
        single { get<LocalDB>().stepDao()}
        single { get<LocalDB>().fotoDao() }
    }

}