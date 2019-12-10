package br.ifsp.visao;


import br.ifsp.controle.dao.DaoProdutoVendido;
import br.ifsp.controle.dao.DaoVenda;
import br.ifsp.modelo.infoprod.ProdutoVendido;
import br.ifsp.modelo.venda.Venda;
import br.ifsp.utils.QueryBetweenString;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GerenciaVendasController {
    
    @FXML
    private TextField txfNomeVendedor;    

    @FXML
    private TextField txfNomeCliente;

    @FXML
    private Label lblTotalCompra;

    @FXML
    private TextField txfCpfCliente;
    
    @FXML
    private TableView<ProdutoVendido> tableViewListaProduto;

    @FXML
    private TableView<Venda> tableViewVenda;

    @FXML
    private TableColumn<ProdutoVendido, Integer> columnIDProduto;

    @FXML
    private TableColumn<ProdutoVendido, Integer> columnQuantProduto;

    @FXML
    private TableColumn<Venda, Date> columnDataVenda;
    
    @FXML
    private TableColumn<Venda, Double> columnValorVenda;

    @FXML
    private TableColumn<ProdutoVendido, String> columnNomeProduto;    

    @FXML
    private TableColumn<Venda, Integer> columnIDVenda;
    
    @FXML
    private TextField txfIdVendedor;
    
    @FXML
    private TextField txfPesquisa;

    @FXML
    private Button btnPesquisar;
    
    @FXML
    private DatePicker inicio;
    
    @FXML
    private DatePicker fim;
    
    @FXML
    private ComboBox<String> cbxTabelas;
    
    private ObservableList<String> tabelas = FXCollections.observableArrayList("FUNCIONARIO", "CLIENTE");
    private ObservableList<Venda> observableVendas;
    private ObservableList<ProdutoVendido> observableItens;
    private List<Venda> vendas;
    private List<ProdutoVendido> produtosVendidos;
    private Pane mypane;
    
    @FXML
    void initialize(){
        cbxTabelas.setItems(tabelas);
        DaoVenda daovenda = new DaoVenda();
        
        txfNomeCliente.setEditable(false);
        txfCpfCliente.setEditable(false);
        txfIdVendedor.setEditable(false);
        txfNomeVendedor.setEditable(false);
        btnPesquisar.setOnAction((ActionEvent event) -> {
            preencheListaVendas();
        });
    }
    
    public void setPane(Pane pane){
        mypane = pane;
    }
    
    private QueryBetweenString criaObjetoConsulta(){
        QueryBetweenString dado = new QueryBetweenString();
        Instant x = inicio.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant();        
        dado.inicio = Date.from(x);
        x = fim.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant();
        dado.fim = Date.from(x);
        dado.str = txfPesquisa.getText();
        return dado;
    }
    
    private void carregaTableVenda(){        
        columnIDVenda.setCellValueFactory(new PropertyValueFactory<>("Id"));
        columnDataVenda.setCellValueFactory(new PropertyValueFactory<>("Data"));
        columnValorVenda.setCellValueFactory(new PropertyValueFactory<>("Valor") );
                
        observableVendas = FXCollections.observableArrayList(vendas);
        tableViewVenda.setItems(observableVendas);   
    }
    
    private void carregaTableItens(){        
        columnIDProduto.setCellValueFactory(new PropertyValueFactory<>("Id"));
        columnNomeProduto.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        columnQuantProduto.setCellValueFactory(new PropertyValueFactory<>("Quantidade") );
                
        observableItens = FXCollections.observableArrayList(produtosVendidos);
        tableViewListaProduto.setItems(observableItens);   
    }
    
    private void preencheListaVendas(){
        DaoVenda daoVendas = new DaoVenda();
        
        String tabela = cbxTabelas.getSelectionModel().getSelectedItem();
        
        if(tabela.equals("FUNCIONARIO")){
            vendas=daoVendas.readAllFuncionario(criaObjetoConsulta());
            carregaTableVenda();
            preencheListaItens();
        }else{
            if(tabela.equals("CLIENTE")){
                vendas=daoVendas.readAllCliente(criaObjetoConsulta());  
                preencheListaItens();
            }
        }
        carregaTableItens();
        carregaTableVenda();
    }
    
    private void preencheListaItens(){
        DaoProdutoVendido daoProdutoVendido = new DaoProdutoVendido();
        vendas.forEach((t) -> t.itens=daoProdutoVendido.readAll(t.getId()) );
    }
    
    
}

