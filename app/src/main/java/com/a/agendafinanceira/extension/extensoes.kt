package com.a.agendafinanceira.extension

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.formataParaBrasileiro() : String{

    val formatoBrasileiro = "dd/MM/yyyy"
    val format = SimpleDateFormat(formatoBrasileiro)
    val dataFormatada = format.format(this.time)

    return dataFormatada


}

