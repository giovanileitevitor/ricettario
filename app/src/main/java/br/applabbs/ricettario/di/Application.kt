package br.applabbs.ricettario.di

import androidx.multidex.MultiDexApplication
import br.applabbs.ricettario.di.AppComponent.getAllModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin


@KoinApiExtension
open class Application: MultiDexApplication(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI(){
        startKoin {
            androidLogger()
            androidContext(this@Application)
            koin.loadModules(getAllModules())
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}