package br.applabbs.ricettario.ui.configurar

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import br.applabbs.ricettario.aux.BaseActivity
import br.applabbs.ricettario.aux.onDebouncedListener
import br.applabbs.ricettario.databinding.ActivityConfigurarBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate


class ConfigurarActivity: BaseActivity() {

    private val viewModel : ConfigurarViewModel by viewModel()
    private lateinit var binding: ActivityConfigurarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigurarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
        setupObservers()
        Log.d("APP", "on Create")
    }

    override fun onStart() {
        super.onStart()
        Log.d("APP", "on Start")
    }

    override fun onResume() {
        super.onResume()
        Log.d("APP", "on Resume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("APP", "on Pause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("APP", "on Stop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("APP", "on Destroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("APP", "on Restart")
    }

    private fun setupListeners(){
        binding.btnVoltar.onDebouncedListener {
            super.onBackPressed()
        }

        binding.btnCrash.onDebouncedListener{
            throw Error("i m the crash")
            //throw RuntimeException("i m the crash")
        }

        binding.btnAlarm.setOnClickListener {
            val time = LocalDate.now()
            viewModel.startAlarm()
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

    private fun setupObservers(){
        viewModel.hasTimeout.observe(this, Observer { finishTime ->
            when(finishTime){
                true -> Toast.makeText(this, "Inicio do tempo de espera", Toast.LENGTH_SHORT).show()
                false -> Toast.makeText(this, "Fim do tempo de espera", Toast.LENGTH_SHORT).show()
            }
        })
    }

}

//fun isAppForeground(): Boolean {
//    val mActivityManager: ActivityManager =
//        context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//    val l: List<ActivityManager.RunningAppProcessInfo> = mActivityManager
//        .getRunningAppProcesses()
//    val i = l.iterator()
//    while (i.hasNext()) {
//        val info = i.next()
//        if (info.uid == context.getApplicationInfo().uid &&
//            info.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
//            return true
//        }
//    }
//    return false
//}