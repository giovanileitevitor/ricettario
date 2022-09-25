package br.applabbs.ricettario.di

import androidx.multidex.MultiDexApplication
import br.applabbs.ricettario.di.AppComponent.getAllModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

open class RicettarioApplication: MultiDexApplication(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI(){
        startKoin {
            androidLogger()
            androidContext(this@RicettarioApplication)
            koin.loadModules(getAllModules())
            //koin.createRootScope()
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}