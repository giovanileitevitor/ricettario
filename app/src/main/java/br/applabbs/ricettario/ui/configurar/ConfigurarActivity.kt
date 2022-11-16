package br.applabbs.ricettario.ui.configurar

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.applabbs.ricettario.aux.onDebouncedListener
import br.applabbs.ricettario.databinding.ActivityConfigurarBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class ConfigurarActivity: AppCompatActivity() {

    private val viewModel : ConfigurarViewModel by viewModel()
    private lateinit var binding: ActivityConfigurarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigurarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun setupListeners(){
        binding.btnVoltar.onDebouncedListener {
            super.onBackPressed()
        }

        binding.btnCrash.onDebouncedListener{
            throw Error("i m the crash")
            //throw RuntimeException("i m the crash")
        }

        binding.txtLinkedin.onDebouncedListener {
            val link = "https://www.linkedin.com/in/giovani-leite-vitor-7803961b9/"
            startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse(link)
                )
            )
        }

        binding.txtWhatsapp.onDebouncedListener {
            val foneNumber = "+11975313142"
            val message = "Adorei o app de receitas"
            startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=$foneNumber&text=$message"
                    )
                )
            )
        }
    }

}