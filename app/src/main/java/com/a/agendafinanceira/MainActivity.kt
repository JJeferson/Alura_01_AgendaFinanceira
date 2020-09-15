package com.a.agendafinanceira

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog.show
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.a.agendafinanceira.adapter.listaAdapter
import com.a.agendafinanceira.extension.formataParaBrasileiro
import com.a.agendafinanceira.model.modeloLista
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.alert_dialog_layout.*
import kotlinx.android.synthetic.main.alert_dialog_layout.dataID
import kotlinx.android.synthetic.main.alert_dialog_layout.nomeID
import kotlinx.android.synthetic.main.alert_dialog_layout.view.*
import kotlinx.android.synthetic.main.alert_dialog_layout.view.nomeID
import kotlinx.android.synthetic.main.item_da_lista.*
import kotlinx.android.synthetic.main.item_da_lista.view.*
import kotlinx.android.synthetic.main.item_da_lista.view.dataID
import kotlinx.android.synthetic.main.item_da_lista.view.tipoLancamentoID
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.item_da_lista.view.nomeID as nomeID1
import kotlinx.android.synthetic.main.item_da_lista.view.valorID as valorID1

class MainActivity   : AppCompatActivity() {


    var lista_Com_Modelo : MutableList<modeloLista> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        processamentoContadores()
        montaLista ()
        eventosOnclick()
        acoesBotoes()

    }//fim do oncreate

    fun acoesBotoes(){

        /*
        floatingActionButtonSaida.setOnClickListener { view ->
            Snackbar.make(view, "Here's a Snackbar - Clicou em Saida", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }
        */

        floatingActionButtonEntrada.setImageResource(R.drawable.somar);
        floatingActionButtonSaida.setImageResource(R.drawable.saida);

        floatingActionButtonEntrada.setOnClickListener { view ->

            criaAlertDialog("",
                            "",
                            "",
                            "",
                            0)


        }//final do botão movimentação de entrada.

        floatingActionButtonSaida.setOnClickListener { view ->
         //segundo botao não implementei nada.
            Toast.makeText(this, "Nada foi implementado neste botão, use o de cima.", Toast.LENGTH_SHORT).show()

        }

    }

    fun montaLista (){
        listView.setAdapter(listaAdapter(lista_Com_Modelo,this))

    }
    fun eventosOnclick(){
        //click curto
        listView.setOnItemClickListener(){ parent, view: View, posicao, id ->

            var itemClicado = lista_Com_Modelo[posicao]
            /*criaAlertDialog("teste","20/10/1985","entrada","10")*/

            criaAlertDialog(itemClicado.nome,
                itemClicado.dataAgora.formataParaBrasileiro(),
                itemClicado.tipoLancamento,
                itemClicado.valor.toString(),posicao)
        }//final do evento onclik

        //click longo, efeito de context
        listView.setOnCreateContextMenuListener { menu,
                                                  view,
                                                  contextMenuInfo ->
            menu.add(Menu.NONE,1,Menu.NONE,"Remover")

        }//final do context menu


    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        var idDoMenu = item.itemId
        if (idDoMenu == 1){
        //esse id igual a 1 é pra dizer que é o botao remover que cliquei
        var adapterMenuInfo =  item.menuInfo as AdapterView.AdapterContextMenuInfo
        var posicaoDaLista = adapterMenuInfo.position
         lista_Com_Modelo.removeAt(posicaoDaLista)
         //listView.setAdapter(listaAdapter(lista_Com_Modelo,this))
         montaLista ()
         processamentoContadores()


        }
        return super.onContextItemSelected(item)
    }

    fun processamentoContadores(){

        exibeReceita.setText(BigDecimal.ZERO.formataParaBrasileiro())
        exibeDespesa.setText(BigDecimal.ZERO.formataParaBrasileiro())
        exibeTotal.setText(BigDecimal.ZERO.formataParaBrasileiro())


        val view = window.decorView
        exibeTitulo(view,this).calculaTotais(lista_Com_Modelo)
    }

    fun criaAlertDialog(preencheNome: String,
                        preencheData: String,
                        preencheTipo: String,
                        preencheValor:String,
                        preenchePosicao: Int ){


        //aqui começa o Dialog

        val view = window.decorView
        var view_AlertDialog =  LayoutInflater.from(this).
        inflate(R.layout.alert_dialog_layout,view as ViewGroup,false)

        //-------------------------------------------------------------------------
        // aqui serve para poder usar mesma função para alterar e para inserir
        var mostraNome    = preencheNome
        var mostraData    = preencheData
        var mostraTipo    = preencheTipo
        var mostraValor   = preencheValor
        var mostraPosicao   = preenchePosicao


        if(mostraNome.length != 0) {
            montaTela_UPDATE_AlertDialog(view_AlertDialog,mostraPosicao)
            datePickerAlerDialog(view_AlertDialog)
            spinnerArrayAlertDialog(view_AlertDialog)


            view_AlertDialog.nomeID.setText(mostraNome)
            view_AlertDialog.dataID.setText(mostraData)
            view_AlertDialog.valorID.setText(mostraValor)

            var tiposQueExistem = this.resources.getStringArray(R.array.tiposLancamentoArrayNoStrings)
            var categoriaQueVeioNaposicao = tiposQueExistem.indexOf(mostraTipo)
            view_AlertDialog.spinnerID.setSelection(categoriaQueVeioNaposicao,true)


        }
        //--------------------------------------------------------------------------
        else {
        montaTela_INSERT_AlertDialog(view_AlertDialog)
        datePickerAlerDialog(view_AlertDialog)
        spinnerArrayAlertDialog(view_AlertDialog)
       }




  }

    fun montaTela_UPDATE_AlertDialog(view_AlertDialog:View, posicao : Int){
        val view = window.decorView

        AlertDialog.Builder(this).
        setTitle("Altera valores").
        setView(view_AlertDialog).
        setPositiveButton("Alterar",
            DialogInterface.OnClickListener { dialogInterface, i ->
                var recebeNome = view_AlertDialog.nomeID.text.toString()
                var recebeData = view_AlertDialog.dataID.text.toString()
                var recebeTipo = view_AlertDialog.spinnerID.selectedItem.toString()
                var recebeValor =    view_AlertDialog.valorID.text.toString()
                if (recebeValor.length == 0) {
                    recebeValor ="0"
                }
                if (recebeNome.length ==0){
                    recebeNome = "Não informado."
                }
                val valor = BigDecimal(recebeValor)
                val formatoBrasileiro = SimpleDateFormat("dd/MM/yyyyy")
                val dataConvertida = formatoBrasileiro.parse(recebeData)
                val data = Calendar.getInstance()
                data.time = dataConvertida

                val transacao = modeloLista(recebeNome,recebeTipo,data,valor)

                lista_Com_Modelo.set(posicao,transacao)
                //lista_Com_Modelo.add(transacao)

                //listView.setAdapter(listaAdapter(lista_Com_Modelo,this))
                montaLista ()
                processamentoContadores()

            }).
        setNegativeButton("Sair",null).
        show()

    }




    fun montaTela_INSERT_AlertDialog(view_AlertDialog:View){
        val view = window.decorView

        AlertDialog.Builder(this).
        setTitle("Entrada de valores").
        setView(view_AlertDialog).
        setPositiveButton("Gravar",
            DialogInterface.OnClickListener { dialogInterface, i ->
                var recebeNome = view_AlertDialog.nomeID.text.toString()
                var recebeData = view_AlertDialog.dataID.text.toString()
                var recebeTipo = view_AlertDialog.spinnerID.selectedItem.toString()
                var recebeValor =    view_AlertDialog.valorID.text.toString()
                if (recebeValor.length == 0) {
                    recebeValor ="0"
                }
                if (recebeNome.length ==0){
                    recebeNome = "Não informado."
                }
                val valor = BigDecimal(recebeValor)
                val formatoBrasileiro = SimpleDateFormat("dd/MM/yyyyy")
                val dataConvertida = formatoBrasileiro.parse(recebeData)
                val data = Calendar.getInstance()
                data.time = dataConvertida

                val transacao = modeloLista(recebeNome,recebeTipo,data,valor)

                lista_Com_Modelo.add(transacao)
                //exibeTitulo(view,this).calculaTotais(lista_Com_Modelo)
                montaLista ()
                processamentoContadores()

            }).
        setNegativeButton("Sair",null).
        show()

    }


    fun datePickerAlerDialog(view_AlertDialog:View){

        //-----------------------------------------------------
        //Lidando com o date / date picker
        var dataAgora = Calendar.getInstance()

        var exibeAno =  dataAgora.get(Calendar.YEAR)
        var exibeMes =  dataAgora.get(Calendar.MONTH)
        var exibeDia = dataAgora.get(Calendar.DAY_OF_MONTH)

        view_AlertDialog.dataID.setText(dataAgora.formataParaBrasileiro())
        view_AlertDialog.dataID.setOnClickListener { view ->
            DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener{ view, ano, mes, dia ->
                    var dataPicker = Calendar.getInstance()
                    dataPicker.set(ano,mes,dia)
                    view_AlertDialog.dataID.setText(dataPicker.formataParaBrasileiro())
                },exibeAno,exibeMes,exibeDia).show()}
    }


    fun spinnerArrayAlertDialog(view_AlertDialog:View){
        //-----------------------------------------------------
        val adapterTipo =
            ArrayAdapter.createFromResource(this,
                R.array.tiposLancamentoArrayNoStrings,
                android.R.layout.simple_spinner_dropdown_item)

        view_AlertDialog.spinnerID.adapter = adapterTipo
    }
}//fim da classe
