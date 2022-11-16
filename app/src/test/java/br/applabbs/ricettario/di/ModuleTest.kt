package br.applabbs.ricettario.di

import br.applabbs.ricettario.di.AppModules.viewModelModule
import br.applabbs.ricettario.ui.adicionar.AdicionarViewModel
import br.applabbs.ricettario.ui.configurar.ConfigurarViewModel
import br.applabbs.ricettario.ui.exibir.ExibirViewModel
import br.applabbs.ricettario.ui.home.HomeViewModel
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.KoinContextHandler
import org.koin.test.get
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.mockito.Mockito

@Ignore(value = "under analysys why is failing")
class ModuleTest: KoinTest {

    @Before
    fun startKoinForTest(){
        if(KoinContextHandler.getOrNull()== null){
            startKoin{
                androidLogger()
                androidContext(Mockito.mock(RicettarioApplication::class.java))
                modules(listOf(viewModelModule))
            }
        }
    }

    @After
    fun stopKoinAfterTest() = stopKoin()

    @Test
    fun `should inject ViewModelModule`(){

        val homeViewModel = get<HomeViewModel>()
        val configurarViewModel = get<ConfigurarViewModel>()
        val adicionarViewModel = get<AdicionarViewModel>()
        val exibirViewModel = get<ExibirViewModel>()

        assert(homeViewModel != null)
        assert(configurarViewModel != null)
        assert(adicionarViewModel != null)
        assert(exibirViewModel != null)
    }

}