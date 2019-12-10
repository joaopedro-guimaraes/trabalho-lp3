/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsp.visao;

import br.ifsp.controle.dao.DaoVendedor;
import br.ifsp.modelo.Pessoa.Vendedor;
import br.ifsp.utils.FerramentasForms;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Thon
 */
public class CadastrosFuncionarioController  {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txbPesquisa;
    
    @FXML
    private Label lblId;
    
    @FXML
    private Label lblNome;
    
    @FXML
    private Label lblCpf;
    
    @FXML
    private Label lblRg;
    
    @FXML
    private Label lblSexo;
    
    @FXML
    private DatePicker DtpNascimento;
    
    @FXML
    private Label lblCargo;
    
    @FXML
    private Label lblStatus;
    
    @FXML
    private Label lblCelular;
    
    @FXML
    private Label lblResidencial;
    
    @FXML
    private Label lblEmail;
    
    @FXML
    private Label lblCep;
    
    @FXML
    private Label lblEstado;
    
    @FXML
    private Label lblCidade;
    
    @FXML
    private Label lblBairro;
    
    @FXML
    private Label lblRua;
    
    @FXML
    private Label lblNumero;
    
    @FXML
    private Label lblComplemento;
    
    @FXML
    private Button btnNovo;
    
    @FXML
    private Button btnEditar;
    
    @FXML
    private Button btnExcluir;
    
    @FXML
    private Button btnFechar;
    
    @FXML
    private CheckBox ckAtivo;
    
    @FXML
    private CheckBox ckInativo;
    
    @FXML    
    private TableColumn<Vendedor, Integer> tableColumnId;
    
    @FXML    
    private TableColumn<Vendedor, String> tableColumnNome;
    
    @FXML    
    private TableColumn<Vendedor, String> tableColumnCpf;
    
    @FXML
    private TableView<Vendedor> tableViewFuncionario;
    
    private List<Vendedor> vendedores;
    private ObservableList<Vendedor> observableVendedor;    
    DaoVendedor banco = new DaoVendedor();
    private int cont;
    private AnchorPane pane;
    
    @FXML
    public void initialize() {
        preencheLista();        
        DtpNascimento.setDisable(true);
        
        tableViewFuncionario.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableView(newValue));
        
        txbPesquisa.textProperty().addListener(
                ((observable, oldValue, newValue) -> preencheLista()));
        
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
    
    public void setAnchorPane(AnchorPane pane){
        this.pane = pane;
    }
    
    private void carregaTableView(){
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        tableColumnCpf.setCellValueFactory(new PropertyValueFactory<>("Cpf"));        
        
        observableVendedor = FXCollections.observableArrayList(vendedores);
        tableViewFuncionario.setItems(observableVendedor);
    }
       
    @FXML
    private void preencheLista(){
        
        if((ckAtivo.isSelected() && ckInativo.isSelected()) || (!ckAtivo.isSelected() && !ckInativo.isSelected()))
            vendedores = banco.readAll(txbPesquisa.getText());
        else
            if(ckAtivo.isSelected() && !ckInativo.isSelected())
                vendedores = banco.readAll(true, txbPesquisa.getText());
            else  
                vendedores = banco.readAll(false, txbPesquisa.getText());
        carregaTableView();
    }

    private void selecionarItemTableView(Vendedor aux){
        if(aux != null){    
        lblId.setText(Integer.toString(aux.getId()));
        lblNome.setText(aux.getNome());
        lblCpf.setText(aux.getCpf());
        lblRg.setText(aux.getRg());
        lblSexo.setText((aux.isSexo())?" Masculino" : "Feminino");
        Instant instant = Instant.ofEpochMilli(aux.getNascimento().getTime());
        LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        DtpNascimento.setValue(localDate);
        lblCargo.setText((aux.isCargo())?"Administrador":"Vendedor");
        lblStatus.setText((aux.isStatus())?"Ativo":"Inativo");
        lblCelular.setText(aux.getContatos().getNumeroCelular());
        lblResidencial.setText(aux.getContatos().getNumeroResidencial());
        lblEmail.setText(aux.getContatos().getEmail());
        lblCep.setText(aux.getEndereco().getCep());
        lblEstado.setText(aux.getEndereco().getEstado());
        lblCidade.setText(aux.getEndereco().getCidade());
        lblBairro.setText(aux.getEndereco().getBairro());
        lblRua.setText(aux.getEndereco().getRua());
        lblNumero.setText(aux.getEndereco().getNumero());
        lblComplemento.setText(aux.getEndereco().getComplemento());
        }
    }
    
    private void novoFuncionario() {
        
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/br/ifsp/visao/CadastroFuncionarioDialog.fxml"));
        AnchorPane pane;
        try {
            pane = (AnchorPane) loader.load();            
            Scene cena = new Scene(pane);
            
            Stage palco = new Stage();
            palco.setTitle("Cadastro de Funcionário");
            palco.setResizable(false);
            palco.setScene(cena);
            
            CadastroFuncionarioDialogController controller; 
            controller = loader.getController();  
            controller.setStage(palco);
            
            palco.showAndWait();
            
        } catch (IOException ex) {
            System.out.println(ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro ao tentar abrir form para um novo funcionário.");
            alert.show();
        }    
        preencheLista();
    }
    
    private void editarCadastro(){
        
        Vendedor funcionario = tableViewFuncionario.getSelectionModel().getSelectedItem();
        if(funcionario == null){
            FerramentasForms.showDialogInformation("É necessário selecionar um funcionario para edita-lo", "Atenção");
            return;
        }        
        
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/br/ifsp/visao/CadastroFuncionarioDialog.fxml"));
        AnchorPane pane;
        try {
            pane = (AnchorPane) loader.load();            
            Scene cena = new Scene(pane);
            
            Stage palco = new Stage();
            palco.setTitle("Cadastro de Funcionário");
            palco.setResizable(false);
            palco.setScene(cena);
            
            CadastroFuncionarioDialogController controller; 
            controller = loader.getController();  
            controller.setStage(palco);
            controller.setFuncionario(funcionario);
            
            palco.showAndWait();
            preencheLista();
            
        } catch (IOException ex) {
            System.out.println(ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro ao tentar abrir form para um novo funcionário.");
            alert.show();
        } 
        preencheLista();
    }
    
    private void remover(){
        
        DaoVendedor banco = new DaoVendedor();
        Vendedor aux = tableViewFuncionario.getSelectionModel().getSelectedItem();
        Stage palco;
        
        if (aux != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Tem certeza que deseja excluir essa categoria?");
            palco = (Stage) alert.getDialogPane().getScene().getWindow();
            palco.getIcons().add(new Image("br/ifsp/images/Panda.png"));
            Optional <ButtonType> result = alert.showAndWait();
            
            if ( result.isPresent() && (result.get() == ButtonType.OK) ) {                
                banco.delete(aux.getId());
                preencheLista();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, selecione um item da tabela.");
            palco = (Stage) alert.getDialogPane().getScene().getWindow();
            palco.getIcons().add(new Image("br/ifsp/images/Panda.png"));
            alert.show();
        }
        preencheLista();
    }
    
    private void fecha(){
        pane.getChildren().remove(0);
    }

}
