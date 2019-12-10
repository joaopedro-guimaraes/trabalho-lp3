package br.ifsp.visao;


import br.ifsp.controle.dao.DaoProduto;
import br.ifsp.modelo.infoprod.Produto;
import br.ifsp.utils.FerramentasForms;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CadastroProdutoController {

    @FXML
    private TextArea txaDesc;    

    @FXML
    private Label lblFornecedor;

    @FXML
    private Button btnFechar;

    @FXML
    private Label lblStatus;

    @FXML
    private Label lblQtddInicial;

    @FXML
    private Button btnEditar;

    @FXML
    private CheckBox ckAtivo;

    @FXML
    private Label lblid;

    @FXML
    private TextField txbPesquisa;

    @FXML
    private TableView<Produto> tableViewProdutos;

    @FXML
    private TableColumn<Produto, Integer> tableColumnId;

    @FXML
    private TableColumn<Produto, String> tableColumnNome;
    
    @FXML
    private TableColumn<Produto, Integer> tableColumnEstoque;
    
    @FXML
    private TableColumn<Produto, String> tableColumnFornecedor;
       
    @FXML
    private Label lblLote;

    @FXML
    private Label lblQtdAtual;

    @FXML
    private Button btnExcluir;

    @FXML
    private Label lblNome;

    @FXML
    private Label lblCategoria;

    @FXML
    private Button btnNovo;

    @FXML
    private CheckBox ckInativo;

    private AnchorPane myPane;
    private List<Produto> produtos;
    private ObservableList<Produto> observableProduto;
    private DaoProduto banco = new DaoProduto();
    
    void initialize(){
        preencheLista();
        txaDesc.setEditable(false);
        tableViewProdutos.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldValue, newValue) -> selecionarItemTableView(newValue));
        
        btnNovo.setOnAction((ActionEvent event) -> {
            novoFuncionario();
        });
        
        btnEditar.setOnAction((ActionEvent event) -> {
            editarCadastro();
        });
        
        btnExcluir.setOnAction((ActionEvent event) -> {
            remover();
        });
        
        btnFechar.setOnAction((ActionEvent event) -> {
            fecha();
        });
    }
    
    public void setPane(AnchorPane pane){
        
        myPane = pane;
    }
    
    @FXML
    void preencheLista() {
            if((ckAtivo.isSelected() && ckInativo.isSelected()) || (!ckAtivo.isSelected() && !ckInativo.isSelected()))
            produtos = banco.readAll(txbPesquisa.getText());
        else
            if(ckAtivo.isSelected() && !ckInativo.isSelected())
                produtos = banco.readAll(true, txbPesquisa.getText());
            else  
                produtos = banco.readAll(false, txbPesquisa.getText());
            
            produtos.forEach((t) -> t.setNome(t.getRegistro().getNome()));
            produtos.forEach((t) -> t.setNomeFornecedor(t.getOrigem().getOrigem().getNome()));
        carregaTableView();
    }

    private void carregaTableView() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        tableColumnEstoque.setCellValueFactory(new PropertyValueFactory<>("quantidadeatual") );
        tableColumnFornecedor.setCellValueFactory(new PropertyValueFactory<>("nomeFornecedor")); 
        
        observableProduto = FXCollections.observableArrayList(produtos);
        tableViewProdutos.setItems(observableProduto);   
    }

    private void selecionarItemTableView(Produto aux) {
        if(aux == null)
            return;
        
        lblid.setText(Integer.toString(aux.getId()));
        lblNome.setText(aux.getNome());
        lblFornecedor.setText(aux.getNomeFornecedor());
        lblLote.setText(aux.getOrigem().toString());
        lblQtdAtual.setText(Integer.toString(aux.getQuantidadeatual()));
        lblQtddInicial.setText(Integer.toString(aux.getQuantidade()));
        lblCategoria.setText(aux.getRegistro().getCategoria().getNome());
        lblStatus.setText(aux.isStatus()?"Ativo":"Inativo");
        txaDesc.setText(aux.getRegistro().getDescricao());
    }

    private void novoFuncionario() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/br/ifsp/visao/CadastroProdutoDialog.fxml"));
        AnchorPane pane;
        try {
            pane = (AnchorPane) loader.load();            
            Scene cena = new Scene(pane);
            
            Stage palco = new Stage();
            palco.setTitle("Cadastro de Estoque");
            palco.setResizable(false);
            palco.setScene(cena);
            
            CadastroProdutoDialogController controller; 
            controller = loader.getController();  
            controller.setStage(palco);
            
            palco.showAndWait();
            
        } catch (IOException ex) {
            System.out.println(ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro ao tentar abrir form para um novo produto.");
            alert.show();
        }
        preencheLista();
    }

    private void editarCadastro() {
        
        Produto aux = tableViewProdutos.getSelectionModel().getSelectedItem();
        if(aux == null){
            FerramentasForms.showDialogInformation("É necessário selecionar o produto para editar.", "Atenção");
            return;
        }
        
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/br/ifsp/visao/CadastroProdutoDialog.fxml"));
        AnchorPane pane;
        try {
            pane = (AnchorPane) loader.load();            
            Scene cena = new Scene(pane);
            
            Stage palco = new Stage();
            palco.setTitle("Cadastro de Estoque");
            palco.setResizable(false);
            palco.setScene(cena);
            
            CadastroProdutoDialogController controller; 
            controller = loader.getController();  
            controller.setStage(palco);
            controller.setProduto(aux);
            
            palco.showAndWait();
            
        } catch (IOException ex) {
            System.out.println(ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro ao tentar abrir form para um novo produto.");
            alert.show();
        }
        preencheLista();
    }

    private void remover() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void fecha() {
        myPane.getChildren().remove(0);
    }
    
}
