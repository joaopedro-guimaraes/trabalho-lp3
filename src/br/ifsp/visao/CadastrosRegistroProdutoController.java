package br.ifsp.visao;

import br.ifsp.controle.dao.DaoRegistroProduto;
import br.ifsp.modelo.infoprod.RegistroProduto;
import br.ifsp.utils.FerramentasForms;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CadastrosRegistroProdutoController {

    @FXML
    private TextArea txaDesc;    

    @FXML
    private Button btnFechar;

    @FXML
    private Label lblStatus;

    @FXML
    private Button btnEditar;

    @FXML
    private CheckBox ckAtivo;

    @FXML
    private Label lblid;

    @FXML
    private TextField txbPesquisa;

    @FXML
    private TableView<RegistroProduto> tableViewRegistros;

    @FXML
    private TableColumn<RegistroProduto, Integer> tableColumnId;

    @FXML
    private TableColumn<RegistroProduto, String> tableColumnNome;
    
    @FXML
    private TableColumn<RegistroProduto, String> tableColumnCategoria;

    @FXML
    private Button btnExcluir;

    @FXML
    private Label lblNome;

    @FXML
    private Label lblCodigo;

    @FXML
    private Label lblCategoria;

    @FXML
    private Button btnNovo;

    @FXML
    private CheckBox ckInativo;

    private ObservableList<RegistroProduto> ObservableRgProd;
    private List<RegistroProduto> listRegistros;
    private AnchorPane pane;
    private DaoRegistroProduto banco = new DaoRegistroProduto();
    
    void initialize(){   
        
        txaDesc.setEditable(false);
        
        preencheLista();
        tableViewRegistros.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableView(newValue));
        
        txbPesquisa.textProperty().addListener(
                ((observable, oldValue, newValue) -> preencheLista()));
        
        btnNovo.setOnAction((ActionEvent event) -> {
            novoRegistro();
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
        tableColumnCategoria.setCellValueFactory(new PropertyValueFactory<>("CategoriaNome"));        
        
        ObservableRgProd = FXCollections.observableArrayList(listRegistros);
        tableViewRegistros.setItems(ObservableRgProd);
    }
    
    @FXML
    private void preencheLista(){
        
        if((ckAtivo.isSelected() && ckInativo.isSelected()) || (!ckAtivo.isSelected() && !ckInativo.isSelected()))
            listRegistros = banco.readAll(txbPesquisa.getText());
        else
            if(ckAtivo.isSelected() && !ckInativo.isSelected())
                listRegistros = banco.readAll(true, txbPesquisa.getText());
            else  
                listRegistros = banco.readAll(false, txbPesquisa.getText());
        listRegistros.forEach((t) -> t.setCategoriaNome(t.getCategoria().getNome()));
        carregaTableView();
    }

    private void selecionarItemTableView(RegistroProduto aux) {
        if(aux != null){
            lblid.setText(Integer.toString(aux.getId()));
            lblCodigo.setText(aux.getCodigo());
            lblNome.setText(aux.getNome());
            lblStatus.setText((aux.isStatus())?"Ativo":"Inativo");
            lblCategoria.setText(aux.getCategoria().getNome());
            txaDesc.setText(aux.getDescricao());
        }
    }

    private void novoRegistro() {
       FXMLLoader loader = new FXMLLoader( getClass().getResource("/br/ifsp/visao/CadastroRegistroProdutoDialog.fxml"));
        AnchorPane pane;
        try {
            pane = (AnchorPane) loader.load();            
            Scene cena = new Scene(pane);
            
            Stage palco = new Stage();
            palco.setTitle("Cadastro de Produto");
            palco.setResizable(false);
            palco.setScene(cena);
            
            CadastroRegistroProdutoDialogController controller; 
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

    private void editarCadastro() {
        RegistroProduto registro = tableViewRegistros.getSelectionModel().getSelectedItem();
        if(registro == null){
            FerramentasForms.showDialogInformation("É necessário selecionar um registro para edita-lo", "Atenção");
            return;
        } 
        
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/br/ifsp/visao/CadastroRegistroProdutoDialog.fxml"));
        AnchorPane pane;
        try {
            pane = (AnchorPane) loader.load();            
            Scene cena = new Scene(pane);
            
            Stage palco = new Stage();
            palco.setTitle("Cadastro de Produto");
            palco.setResizable(false);
            palco.setScene(cena);
            
            CadastroRegistroProdutoDialogController controller; 
            controller = loader.getController();  
            controller.setStage(palco);
            controller.setRegistro(registro);
            
            palco.showAndWait();
            
        } catch (IOException ex) {
            System.out.println(ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro ao tentar abrir form para um novo funcionário.");
            alert.show();
        }   
        preencheLista();
    }

    private void remover() {
        RegistroProduto registro = tableViewRegistros.getSelectionModel().getSelectedItem();
        if(registro == null){
            FerramentasForms.showDialogInformation("É necessário selecionar um registro para remove-lo", "Atenção");
            return;
        }     
        
        Stage stage;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Tem certeza que deseja excluir essa categoria?");
        stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("br/ifsp/images/Panda.png"));
        Optional <ButtonType> result = alert.showAndWait();

        if ( result.isPresent() && (result.get() == ButtonType.OK) ) {                
            if(banco.delete(registro.getId())){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Registro desativado com sucesso!");
                stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("br/ifsp/images/Panda.png"));
                stage.showAndWait();
            }
            preencheLista();
        }        
    }   

    private void fecha() {
      pane.getChildren().remove(0);
    }

    
    

}
