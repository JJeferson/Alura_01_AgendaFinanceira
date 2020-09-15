package com.a.agendafinanceira.extension

import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*


fun BigDecimal.formataParaBrasileiro() : String {

    val formatoBrasileiro = DecimalFormat.
    getCurrencyInstance(Locale("pt","br"))

    val moedaFormatada = formatoBrasileiro.format(this).
    replace("R$","R$")

    return moedaFormatada

}