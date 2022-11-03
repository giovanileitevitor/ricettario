package br.applabbs.ricettario.ui.error

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import br.applabbs.ricettario.databinding.ActivityCustomizedErrorBinding
import br.applabbs.ricettario.ui.home.HomeActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("SetTextI18n")

class CustomizedErrorActivity: AppCompatActivity() {

    private lateinit var binding: ActivityCustomizedErrorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomizedErrorBinding.inflate(layoutInflater)
        GlobalExceptionHandler.getThrowableFromIntent(intent).let{
            Log.e(TAG, "Error details: ", it)
            setupView(error = it.toString())
        }
        setContentView(binding.root)
        setupListeners()
    }

    private fun setupView(error: String){
        binding.txtInternal.text = error
    }

    private fun setupListeners(){
        binding.btnError.setOnClickListener {
            lifecycleScope.launch {
                delay(2000)
            }
            finishAffinity()
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }

    companion object{
        private const val TAG = "CrashReport"
    }
}

