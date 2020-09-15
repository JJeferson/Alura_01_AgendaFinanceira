package com.a.agendafinanceira.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.a.agendafinanceira.R
import com.a.agendafinanceira.extension.formataNome
import com.a.agendafinanceira.extension.formataParaBrasileiro
import com.a.agendafinanceira.model.modeloLista
import com.a.agendafinanceira.model.tipo_lancamentos
import kotlinx.android.synthetic.main.item_da_lista.view.*
import java.math.RoundingMode
import java.text.NumberFormat

class listaAdapter(
    lista: List<modeloLista>,
    context: Context) : BaseAdapter() {

    private val recebeLista = lista
    private var context = context

    override fun getView(posicao: Int, view: View?, parent: ViewGroup?): View {

      var posicao_na_lista = recebeLista[posicao]




      var viewCriada=  LayoutInflater.from(context).
                             inflate(R.layout.item_da_lista,
                             parent,
                            false)


        if (posicao_na_lista.tipoLancamento== "entrada"){
            viewCriada.nomeID.setTextColor(ContextCompat.getColor(context,R.color.Green))
            viewCriada.valorID.setTextColor(ContextCompat.getColor(context,R.color.Green))
        }
        if (posicao_na_lista.tipoLancamento== "saida"){
            viewCriada.nomeID.setTextColor(ContextCompat.getColor(context,R.color.Red))
            viewCriada.valorID.setTextColor(ContextCompat.getColor(context,R.color.Red))
        }
        if (posicao_na_lista.tipoLancamento== "indefinida"){
            viewCriada.nomeID.setTextColor(ContextCompat.getColor(context,R.color.Cu))
            viewCriada.valorID.setTextColor(ContextCompat.getColor(context,R.color.Cu))
        }






        viewCriada.nomeID.setText(posicao_na_lista.nome.formataNome())
        viewCriada.dataID.setText(posicao_na_lista.dataAgora.formataParaBrasileiro())
        viewCriada.tipoLancamentoID.setText(posicao_na_lista.tipoLancamento.toString())
        viewCriada.valorID.setText(""+posicao_na_lista.valor.formataParaBrasileiro())
        //viewCriada.valorID.setText(""+exibeValor)


        return viewCriada
    }

    override fun getItem(posicao: Int): modeloLista {
        return recebeLista[posicao]

    }

    override fun getItemId(p0: Int): Long {
       return 0
    }

    override fun getCount(): Int {
        return recebeLista.size
    }



}