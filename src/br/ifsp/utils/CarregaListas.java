/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsp.utils;

import br.ifsp.controle.dao.DaoPagamento;
import br.ifsp.controle.dao.DaoProdutoVendido;
import br.ifsp.modelo.venda.Venda;

/**
 *
 * @author Thon
 */
public class CarregaListas {
    public static void carregaPagamentosEProdutos(Venda aux){
        DaoProdutoVendido daoProdutoVendido = new DaoProdutoVendido();
        DaoPagamento daoPagamento = new DaoPagamento();
        aux.itens = daoProdutoVendido.readAll(aux.getId());
        aux.pagamentos = daoPagamento.readAll(aux.getId());
    }
}
