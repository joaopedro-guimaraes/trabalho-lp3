package br.ifsp.visao;

import br.ifsp.controle.dao.DaoLote;
import br.ifsp.controle.dao.DaoProduto;
import br.ifsp.modelo.infoprod.Lote;
import br.ifsp.modelo.infoprod.Produto;
import br.ifsp.utils.FerramentasForms;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CadastroLoteController {

    @FXML
    private TableColumn<Lote, String> columnID;

    @FXML
    private Label lblFornecedor;

    @FXML
    private Button btnFechar;

    @FXML
    private TableView<Lote> tableView;

    @FXML
    private Button btnInserir;

    @FXML
    private TableColumn<Lote, String> columnFornecedor;

    @FXML
    private Label lblID;

    @FXML
    private Label lblLucro;

    @FXML
    private ListView<Produto> listView;

    @FXML
    private Label lblValor;
    
    @FXML
    private Label lblStatus;

    @FXML
    private Button btnAlterar;
    
    @FXML
    private CheckBox ckAtivo;
    
    @FXML
    private CheckBox ckInativo;

    @FXML
    private TableColumn<Lote, String> columnData;

    @FXML
    private DatePicker dtPicker;

    @FXML
    private TextField txtFieldFiltro;

    @FXML
    private Button btnRemover;
    
    private AnchorPane panePai;
    private List<Lote> listLote;
    private ObservableList<Lote> observableList;
    private DaoLote banco = new DaoLote();
       
    @FXML
    void initialize() {
        dtPicker.setEditable(false);        
        preencheLista();
        
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableView(newValue));
        
        txtFieldFiltro.textProperty().addListener(
                ((observable, oldValue, newValue) -> preencheLista()));
        
        btnInserir.setOnAction((ActionEvent event) -> {
            inserir();
        });
        
        btnAlterar.setOnAction((ActionEvent event) -> {
            alterar();
        });
        
        btnRemover.setOnAction((ActionEvent event) -> {
            remover();
        });
        
        btnFechar.setOnAction((ActionEvent event) -> {
            fechar();
        });
       
    }

    public void setPanePai(AnchorPane panePai) {
        this.panePai = panePai;
    }
    
    @FXML
    private void preencheLista(){
        if((ckAtivo.isSelected() && ckInativo.isSelected()) || (!ckAtivo.isSelected() && !ckInativo.isSelected()))
            listLote = banco.readAll(txtFieldFiltro.getText());
        else
            if(ckAtivo.isSelected() && !ckInativo.isSelected())
                listLote = banco.readAll(true, txtFieldFiltro.getText());
            else  
                listLote = banco.readAll(false, txtFieldFiltro.getText());
        
        listLote.forEach((t) -> t.setNomeFornecedor(t.getOrigem().getNome()));
        carregaTableView();
    }
    
    private void carregaTableView(){
        columnID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        columnData.setCellValueFactory(new PropertyValueFactory<>("Data"));
        columnFornecedor.setCellValueFactory(new PropertyValueFactory<>("nomeFornecedor"));        
        
        observableList = FXCollections.observableArrayList(listLote);
        tableView.setItems(observableList);
    }

    private void selecionarItemTableView(Lote aux) {
        if(aux == null)
            return;
        
        lblID.setText(Integer.toString(aux.getId()));
        lblFornecedor.setText(aux.getOrigem().getNome());
        lblLucro.setText(Double.toString(aux.getLucroPerct()));
        lblStatus.setText((aux.isStatus())?"Ativo":"Inativo");
        lblValor.setText(Double.toString(aux.getValor())); 
        Instant instant = Instant.ofEpochMilli(aux.getData().getTime());
        LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();        
        dtPicker.setValue(localDate);
    }
    
    private void inserir(){
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/br/ifsp/visao/CadastroLoteDialog.fxml"));
        AnchorPane pane;
        try {
            pane = (AnchorPane) loader.load();            
            Scene cena = new Scene(pane);
            
            Stage palco = new Stage();
            palco.setTitle("Cadastro de Lote");
            palco.setResizable(false);
            palco.setScene(cena);
            
            CadastroLoteDialogController controller; 
            controller = loader.getController();  
            controller.setDialogStage(palco);
            
            palco.showAndWait();
            
        } catch (IOException ex) {
            System.out.println(ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro ao tentar abrir form para um novo lote.");
            alert.show();
        }
        preencheLista();
    }
    
    private void alterar(){
        
        Lote aux = tableView.getSelectionModel().getSelectedItem();
        if(aux == null){
            FerramentasForms.showDialogInformation("É necessário selecionar um lote para edita-lo", "Atenção");
            return;
        }
            
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/br/ifsp/visao/CadastroLoteDialog.fxml"));
        AnchorPane pane;
        try {
            pane = (AnchorPane) loader.load();            
            Scene cena = new Scene(pane);
            
            Stage palco = new Stage();
            palco.setTitle("Cadastro de Lote");
            palco.setResizable(false);
            palco.setScene(cena);
            
            CadastroLoteDialogController controller; 
            controller = loader.getController();  
            controller.setDialogStage(palco);
            controller.setCategoria(aux);
            
            palco.showAndWait();
            
        } catch (IOException ex) {
            System.out.println(ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro ao tentar abrir form para um novo lote.");
            alert.show();
        }
        
        preencheLista();
    }
    
    private void remover(){
        
        Lote aux = tableView.getSelectionModel().getSelectedItem();
        if(aux == null){
            FerramentasForms.showDialogInformation("É necessário selecionar um lote para edita-lo", "Atenção");
            return;
        }
        Stage stage;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Tem certeza que deseja excluir essa categoria?");
        stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("br/ifsp/images/Panda.png"));
        Optional <ButtonType> result = alert.showAndWait();
            
        if ( result.isPresent() && (result.get() == ButtonType.OK) )                 
            if(banco.delete(aux.getId())){
                DaoProduto daoProd = new DaoProduto();
                if(daoProd.desativaPorLote(aux.getId())){
                    FerramentasForms.showDialogInformation("Lote removido", "Atenção");        
                    preencheLista();
                }  
            }
        
    }
    
    private void fechar(){
        panePai.getChildren().remove(0);
    }
}

