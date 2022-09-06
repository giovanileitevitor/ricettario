package br.applabbs.ricettario.aux

import br.applabbs.ricettario.domain.local.models.Foto
import br.applabbs.ricettario.domain.local.models.Receita
import br.applabbs.ricettario.domain.local.models.Step

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
                detalhes = "",
                img = "A pessoa possui",
                isFavorito = if(i%2 !=0) true else false,
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
                detalhes = "Clique no botão + para adicionar uma nova receita",
                img = "R.drawable.livro_receita",
                isFavorito = true,
                steps = emptyList(),
                fotos = emptyList()
            )
        )
    }
    return emptyReceita
}