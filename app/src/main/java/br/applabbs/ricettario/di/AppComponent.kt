package br.applabbs.ricettario.di

import org.koin.core.module.Module

object AppComponent{

    fun getAllModules(): List<Module> = listOf(*getAppModules())

    private fun getAppModules(): Array<Module>{
        return arrayOf(
            AppModules.viewModelModule
        )
    }
}