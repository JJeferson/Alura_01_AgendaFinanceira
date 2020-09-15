package com.a.agendafinanceira.model

import java.math.BigDecimal
import java.util.*

class modeloLista(
    var nome: String,
    var tipoLancamento: String = "indefinida",
    var dataAgora:Calendar = Calendar.getInstance(), var valor: BigDecimal = BigDecimal.ZERO){

     //No kotlin você não precisa de getters and setters.
     // Ele entende sozinho basta declarar na classe modelo os campos.

    //você pode adicionar valore padrão no modelo daquele modo ali feito no calendar
   constructor(nome: String,
               dataAgora: Calendar) : this(
                                        nome,
                                        tipoLancamento ="indefinida",
                                        dataAgora = dataAgora,
                                        valor = BigDecimal.ZERO
    )
        //Sobrecarga de construtores é quando vocÊ cria na classe modelo
        // construtores secunrarios para não precisar preencher toda hora os campos do modelo
        // quando as vezes você não precisaria.
        // Isso diminui quantdade de variaveis que precisa manipular diversas vezes ao atualizar listas
        // o que diminui o custo de memoria e processamento do aparelho

}