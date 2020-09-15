package com.a.agendafinanceira

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import com.a.agendafinanceira.extension.formataParaBrasileiro
import com.a.agendafinanceira.model.modeloLista
import com.a.agendafinanceira.model.tipo_lancamentos
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.math.BigDecimal

class exibeTitulo(private val view: View, context: Context) {


    private var context = context




    fun calculaTotais(Recebelista:List<modeloLista> ){

        var ValoresListagem = Recebelista
        //aqui tou usando um for para pesqusiar na lista
        //objetivo aqui gerar os totais
        var totalEntrada = BigDecimal.ZERO
        var totalSaida = BigDecimal.ZERO
        for (modeloLista in ValoresListagem){
            //----------------------------------------------
            /*
            apenas informativo mas da pra fazer isso de outro modo.
            Substituir o if e o calculo usando a função plus pra somar
            exemplo:

            var totalEntradaComLambda = ValoresListagem
                .filter ({modeloLista->modeloLista.tipoLancamento == tipo_lancamentos.entrada})
                .sumByDouble ({modeloLista->modeloLista.valor.toDouble()})
            //-----------------------------------------------
            pode-se escrever assim tbm
            só que neste cenario tá criando um bigdecimal e não um double
            var totalEntradaComLambdaBigdecimal = BigDecimal (ValoresListagem
                .filter ({it.tipoLancamento == tipo_lancamentos.entrada})
                .sumByDouble ({it.valor.toDouble()}))

            o resultado em toda essa linha é o mesmo do if abaixo
            Objetivo é poupar digitação mas não vi economia de tempo nenhum nisso.

            Talvez em arrays mais complexos isso vá valer a pena
            Isso é implementação de expressões lambda fnções vazias para pesquisa em arrays e listas

            */
            //----------------------------------------------



            if (modeloLista.tipoLancamento == "entrada"){

                totalEntrada = totalEntrada.plus(modeloLista.valor)
                view.exibeReceita.setText(totalEntrada.formataParaBrasileiro())
                view.exibeReceita.setTextColor(ContextCompat.getColor( context, R.color.Green))

            }
            //----------------------------------------------
            if (modeloLista.tipoLancamento == "saida"){

                totalSaida = totalSaida.plus(modeloLista.valor)
                view.exibeDespesa.setText(totalSaida.formataParaBrasileiro())
                view.exibeDespesa.setTextColor(ContextCompat.getColor(context, R.color.Red))
            }
            var totalSaldo = totalEntrada-totalSaida
            view.exibeTotal.setText(totalSaldo.formataParaBrasileiro())
            //Se o total for menor que zero vermelho
            //caso contrario verde

            if (totalSaldo.compareTo(BigDecimal.ZERO) < 0){
                view.exibeTotal.setTextColor(ContextCompat.getColor(context, R.color.Red))
            }else{
                view.exibeTotal.setTextColor(ContextCompat.getColor(context, R.color.verdeArrumadinho))
            }

        }

    }


}