package br.applabbs.ricettario.aux

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import br.applabbs.ricettario.domain.local.models.Foto
import br.applabbs.ricettario.domain.local.models.Receita
import br.applabbs.ricettario.domain.local.models.Step
import kotlin.random.Random

fun geradorReceitas(qtd: Int): ArrayList<Receita>{
    val list = ArrayList<Receita>()
    val steps = ArrayList<Step>()
    val fotos = ArrayList<Foto>()
    val comments = ArrayList<String>()

    for(i in 1..qtd){
        steps.add(
            Step(
                id = i,
                info = "Adicionar o ingrediente $i"
            )
        )
        fotos.add(
            Foto(
                imgAddress = "IMG:$i"
            )
        )
        comments.add(
            element = if(i%2 !=0) "Muito Boa" else "Testando com a família"
        )
    }

    for(i in 1..qtd){
        list.add(
            Receita(
                id = i,
                titulo = if(i%2 !=0) "Pudim cremoso" else "Coxinha recheada",
                img = "A pessoa possui",
                isFavorito = i%2 !=0,
                steps = steps,
                fotos = fotos
            )
        )
    }

    return list
}

fun geradorSteps(qtd: Int): ArrayList<Step>{
   val list = ArrayList<Step>()
   for(i in 1..qtd){
       list.add(
           Step(
               id = i,
               info = "Adicione o ingrediente $i"
           )
       )
   }
    return list
}

fun geradorFotos(qtd: Int): ArrayList<Foto>{
    val list = ArrayList<Foto>()
    for(i in 1..qtd){
        list.add(
            Foto(
                imgAddress = "endereço da foto"
            )
        )
    }
    return list
}

fun emptyReceita(): List<Receita>{
    val emptyReceita = ArrayList<Receita>()
    for(i in 0..0){
        emptyReceita.add(
            Receita(
                id = i,
                titulo = "RECEITAS",
                img = "R.drawable.livro_receita",
                isFavorito = false,
                steps = emptyList(),
                fotos = emptyList()
            )
        )
    }
    return emptyReceita
}

fun randomicInteger(): Int {
    return Random.nextInt(1, 99999)
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}