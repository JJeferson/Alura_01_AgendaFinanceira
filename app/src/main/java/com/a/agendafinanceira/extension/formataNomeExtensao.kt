package com.a.agendafinanceira.extension

import com.a.agendafinanceira.model.modeloLista
import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*

fun String.formataNome() : String {

    if(this.length>14){
     var nomeFormatado = this.substring(0,14)
         nomeFormatado = nomeFormatado+"..."
         return nomeFormatado
    }
    return this
   //caso ele não caia no laço de repetição retorna a propia string
   //caso caia retorna ela formatada
}