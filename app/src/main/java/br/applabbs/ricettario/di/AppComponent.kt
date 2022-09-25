package br.applabbs.ricettario.di

import br.applabbs.ricettario.di.AppModules.localRepositoriesModule
import br.applabbs.ricettario.di.AppModules.localUseCasesModule
import br.applabbs.ricettario.di.AppModules.viewModelModule
import org.koin.core.module.Module

object AppComponent{

    fun getAllModules(): List<Module> = listOf(*getAppModules())

    private fun getAppModules(): Array<Module>{
        return arrayOf(
            viewModelModule,
            localUseCasesModule,
            localRepositoriesModule
            //remoteUseCasesModule
        )
    }
}