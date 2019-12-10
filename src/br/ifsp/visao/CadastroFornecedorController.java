package br.ifsp.visao;

import br.ifsp.controle.dao.DaoFornecedor;
import br.ifsp.modelo.infoprod.Fornecedor;
import java.io.IOException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CadastroFornecedorController {

    @FXML
    private TableColumn<Fornecedor, String> columnID;

    @FXML
    private Button btnFechar;

    @FXML
    private Button btnInserir;

    @FXML
    private Label lblEmail;

    @FXML
    private TableColumn<Fornecedor, String> columnNome;

    @FXML
    private Label lblID;

    @FXML
    private Label lblCNPJ;

    @FXML
    private Label lblTel2;

    @FXML
    private Button btnAlterar;

    @FXML
    private CheckBox ckAtivo;
    
    @FXML
    private CheckBox ckInativo;

    @FXML
    private Label lblTel1;

    @FXML
    private Label lblNome;

    @FXML
    private TextField txtFieldFiltro;

    @FXML
    private Button btnRemover;
    
    @FXML
    private TableView<Fornecedor> tableView;
    
    private AnchorPane panePai;
    private List<Fornecedor> listFornecedor;
    private ObservableList<Fornecedor> observableListFornecedor;
    private DaoFornecedor dao = new DaoFornecedor();
    private Fornecedor fornecedor = null;
   
    public void setPanePai(AnchorPane panePai) {
        this.panePai = panePai;
    }
    
    void initialize() {
        preencheLista();
        
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableView(newValue));
        
        txtFieldFiltro.textProperty().addListener(
                (observable, oldValue, newValue) -> carregarTableView());
        
        btnInserir.setOnAction((ActionEvent event) -> {
            inserir();
        });
        
        btnFechar.setOnAction((ActionEvent event) -> {
            fechar();
        });
        
        btnAlterar.setOnAction((ActionEvent event) -> {
            alterar();
        });
        
        btnRemover.setOnAction((ActionEvent event) -> {
            remover();
        });
    }
    
    private void carregarTableView() {
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        
        observableListFornecedor = FXCollections.observableArrayList(listFornecedor);
        tableView.setItems(observableListFornecedor);
    }
    
    @FXML
    private void preencheLista(){
        
        if((ckAtivo.isSelected() && ckInativo.isSelected()) || (!ckAtivo.isSelected() && !ckInativo.isSelected()))
            listFornecedor = dao.readAll(txtFieldFiltro.getText());
        else
            if(ckAtivo.isSelected() && !ckInativo.isSelected())
                listFornecedor = dao.readAll(true, txtFieldFiltro.getText());
            else  
                listFornecedor = dao.readAll(false, txtFieldFiltro.getText());
        carregarTableView();
    }
    
    private void selecionarItemTableView(Fornecedor aux) {
        this.fornecedor = aux;
        if (aux != null)
            getDTO(aux);
    }
    
    private void getDTO(Fornecedor aux) {
        lblID.setText(String.valueOf(aux.getId()));
        lblNome.setText(aux.getNome());
        lblCNPJ.setText(aux.getCnpj());
        
        lblTel1.setText(aux.getContato().getNumeroCelular());
        lblTel2.setText(aux.getContato().getNumeroResidencial());
        lblEmail.setText(aux.getContato().getEmail());
    }
    
    private void inserir() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/br/ifsp/visao/CadastroFornecedorDialog.fxml") );
        AnchorPane pane;
        
        try {
            pane = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastro de Fornecedor");
            dialogStage.setResizable(false);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);
        
            CadastroFornecedorDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.caseNewFornecedor();
            controller.initialize();
            
            dialogStage.showAndWait();
        } catch (IOException ex) {
            System.out.println("Erro" + ex);
        }      
        preencheLista();
    }
    
    private void alterar(){
        if(fornecedor == null){
           Alert alerta = new Alert(Alert.AlertType.INFORMATION);
           alerta.setTitle("Atenção");
           alerta.setHeaderText(null);
           alerta.setContentText("Para alterar um cadastro é necessário selecionar um item da tabela.");
           alerta.show(); 
           return;
        }
       
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/br/ifsp/visao/CadastroFornecedorDialog.fxml") );
        AnchorPane pane;
        
        try {
            pane = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastro de Fornecedor");
            dialogStage.setResizable(false);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);
        
            CadastroFornecedorDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.caseUpdate(fornecedor);
            controller.initialize();
            
            dialogStage.showAndWait();
        } catch (IOException ex) {
            System.out.println("Erro" + ex);
        }      
        preencheLista();
    }
    
    private void remover(){
        if(fornecedor == null){
           Alert alerta = new Alert(Alert.AlertType.INFORMATION);
           alerta.setTitle("Atenção");
           alerta.setHeaderText(null);
           alerta.setContentText("Para alterar/remover é necessário selecionar um item da tabela.");
           alerta.show(); 
           return;
        }
        
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Atenção");
        alerta.setHeaderText(null);
        
        if(dao.delete(fornecedor.getId()))          
           alerta.setContentText("Fornecedor desativado com sucesso.");
        else
            alerta.setContentText("Erro ao tentar desativar fornecedor.");          
        
        alerta.show();
        preencheLista();
    }
    
    private void fechar() {
        panePai.getChildren().remove(0);
    }
    
}
