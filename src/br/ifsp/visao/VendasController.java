/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsp.visao;

import br.ifsp.controle.dao.DaoCliente;
import br.ifsp.controle.dao.DaoProduto;
import br.ifsp.controle.dao.DaoProdutoVendido;
import br.ifsp.controle.dao.DaoVenda;
import br.ifsp.controle.dao.DaoVendedor;
import br.ifsp.modelo.Pessoa.Cliente;
import br.ifsp.modelo.Pessoa.Vendedor;
import br.ifsp.modelo.infoprod.Produto;
import br.ifsp.modelo.infoprod.ProdutoVendido;
import br.ifsp.modelo.venda.Venda;
import br.ifsp.utils.FerramentasForms;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class VendasController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnConfirmar;  

    @FXML
    private Label lblTotalCompra;
    
    @FXML
    private Button btnRmv;  
        
    @FXML
    private Button btnAdd;    

    @FXML
    private Button btnCancelar;
    
    @FXML
    private TextField txfNomeCliente;
    
    @FXML
    private TextField txfCpfCliente;
    
    @FXML
    private TextField txfNomeVendedor;
    
    @FXML
    private TextField txfIdVendedor;
    
    @FXML
    private TableView<ProdutoVendido> tableViewListaCliente; 
    
    @FXML
    private TableColumn<ProdutoVendido, String> columnIDCliente;
    
    @FXML
    private TableColumn<ProdutoVendido, String> columnNomeCliente;
    
    @FXML
    private TableColumn<ProdutoVendido, String> columnQuantCliente;

    @FXML
    private TableView<Produto> tableViewLoja;
    
    @FXML
    private TableColumn<Produto, String> columnIDLoja; 
    
    @FXML
    private TableColumn<Produto, String> columnNomeLoja;
    
    @FXML
    private TableColumn<Produto, String> columnQuantLoja;
        
    private ObservableList<Produto> observableListProduto;
    private ObservableList<ProdutoVendido> observableListLista;
    private List<Produto> listLoja;
    private List<ProdutoVendido> listCliente;
    private final DaoProduto daoProd = new DaoProduto();
    private Venda venda = new Venda();
    private AnchorPane panePai;
    private Vendedor vendedor;
    
    
    @FXML
    void initialize() {        
        
        preencheList();       
                
        btnAdd.setOnAction((ActionEvent event) -> {
            adicionaProduto();
        });
        
        btnRmv.setOnAction((ActionEvent event) -> {
            removeProduto();            
        });
              
        
        txfCpfCliente.textProperty().addListener(((event) ->  {
            DaoCliente daoCliente = new DaoCliente();
            Cliente aux = daoCliente.read(txfCpfCliente.getText());
            if(aux != null){
                venda.setComprador(aux);
                txfNomeCliente.setText(aux.getNome());                
            }else{                
                txfNomeCliente.setText(""); 
                venda.setComprador(null);
            }            
        }));
        
        btnConfirmar.setOnAction((ActionEvent event) -> {
            salvar();            
            preencheList();
        });
        
        btnCancelar.setOnAction((ActionEvent event) -> {
            preencheList();
        });
        
        txfIdVendedor.setEditable(false);
        txfNomeVendedor.setEditable(false);
        txfNomeCliente.setEditable(false);        
    }
    
    public void setPanePai(AnchorPane panePai) {
        this.panePai = panePai;
    }
    
    public void setVendedor(Vendedor aux){
        vendedor = aux;
        txfIdVendedor.setText(Integer.toString(aux.getId()));
        txfNomeVendedor.setText(aux.getNome());
        venda.setFuncionario(vendedor);
    }
    
    private void preencheList(){
        listLoja = daoProd.readAll(true, "");
        listLoja.forEach((t) -> t.setNome(t.getRegistro().getNome()));        
        listCliente = new ArrayList<>();
        carregarTableViewLoja();
        carregarTableViewListaCliente();
    }
    
    private void carregarTableViewLoja() {
        if(observableListProduto!=null)
            observableListProduto.removeAll(listLoja);
        columnIDLoja.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNomeLoja.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnQuantLoja.setCellValueFactory(new PropertyValueFactory<>("quantidadeatual"));
        
        observableListProduto = FXCollections.observableArrayList(listLoja);
        tableViewLoja.setItems(observableListProduto);
    }
    
    private void carregarTableViewListaCliente() {
        if(observableListLista!=null)
            observableListLista.removeAll(listCliente);
        columnIDCliente.setCellValueFactory(new PropertyValueFactory<>("idProduto"));
        columnNomeCliente.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
        columnQuantCliente.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        
        listCliente.forEach((t) -> t.setIdProduto(t.getProduto().getId()));
        listCliente.forEach((t) -> t.setNomeProduto(t.getProduto().getRegistro().getNome()));        
        
        observableListLista = FXCollections.observableArrayList(listCliente);
        tableViewListaCliente.setItems(observableListLista);
    }
    
    private void adicionaProduto() {
        Produto aux = tableViewLoja.getSelectionModel().getSelectedItem();
        if(aux == null ){
            FerramentasForms.showDialogInformation("Selecione um produto para adiciona-lo a lista.", "Atenção");
            return;
        }
        
        boolean jaEstaNaLista=false;
        if (aux.getQuantidadeatual() - 1 >= 0) {
            for(ProdutoVendido p: listCliente)
                if(aux.getId() == p.getIdProduto()){
                    incrementaProdutoVendido(p, aux);
                    jaEstaNaLista=true;
                }
            if(!jaEstaNaLista)
                criaProdutoVendido(aux);
        
            carregarTableViewLoja();
            carregarTableViewListaCliente();
            calculaValor();
        }else
            FerramentasForms.showDialogInformation("Item esgotado!", "Atenção");
        
    }
    
    private void removeProduto() {
        ProdutoVendido aux = tableViewListaCliente.getSelectionModel().getSelectedItem();
        if(aux == null)
            return;
        if (aux.getQuantidade() > 0)
            for(Produto p:listLoja)
                if(p.getId() == aux.getIdProduto()){
                    p.setQuantidadeatual(p.getQuantidadeatual() + 1 );
                    aux.setQuantidade(aux.getQuantidade() - 1);
                }
        calculaValor();
         if(aux.getQuantidade() == 0)           
            listCliente.remove(aux);        
        
        carregarTableViewListaCliente();
        carregarTableViewLoja();
    }
    
    private void calculaValor(){
        venda.setTotal(0);
        listCliente.forEach((t) -> venda.setTotal(venda.getTotal() + (t.getProduto().getValor()*t.getQuantidade())));
        lblTotalCompra.setText(Double.toString(venda.getTotal()));
    }
    
    private void criaProdutoVendido(Produto prodLoja){
        
        ProdutoVendido prodCliente = new ProdutoVendido();
        prodCliente.setIdProduto(prodLoja.getId());
        prodCliente.setNomeProduto(prodLoja.getRegistro().getNome());
        prodCliente.setQuantidade(1);
        prodLoja.setQuantidadeatual(prodLoja.getQuantidadeatual()-1);
        prodCliente.setProduto(prodLoja);
        listCliente.add(prodCliente);
    }
    
    private void incrementaProdutoVendido(ProdutoVendido prodCliente, Produto prodLoja){
        
        prodCliente.setQuantidade(prodCliente.getQuantidade() + 1);
        prodLoja.setQuantidadeatual(prodLoja.getQuantidadeatual() - 1);
    }

    private void salvar() {
        venda.setFuncionario(vendedor);
        venda.setData(new Date());
        DaoVenda daoVenda = new DaoVenda();
        DaoProdutoVendido daoProdutoVendido = new DaoProdutoVendido();
        
        if(listCliente.size()>0 && venda.getComprador()!=null && venda.getFuncionario() != null){
            if(daoVenda.create(venda)){
                int id = daoVenda.retornaUltimaVenda();
                venda.setId(id);
                listCliente.forEach((t) -> t.setVendaOrigem(venda));
                //cria produto vendido e subtrai quantidade de produto
                listCliente.forEach((t) -> {
                    if(daoProdutoVendido.create(t)){
                        t.getProduto().setQuantidadeatual(t.getProduto().getQuantidadeatual());
                        daoProd.update(t.getProduto());
                    }                        
                });
                FerramentasForms.showDialogInformation("Venda efetuada com sucesso!", "Atenção");
                preencheList();
            }      
        }else
            FerramentasForms.showDialogInformation("Erro por favor verifique o login e o cliente!", "Atenção");
    }
    
}

