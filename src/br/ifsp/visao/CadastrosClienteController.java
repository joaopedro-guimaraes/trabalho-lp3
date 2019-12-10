package br.ifsp.visao;

import br.ifsp.controle.dao.DaoCliente;
import br.ifsp.modelo.Pessoa.Cliente;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CadastrosClienteController {
    @FXML
    private AnchorPane tela;
    
    @FXML
    private TableView<Cliente> tableView;
    
    @FXML
    private TableColumn<Cliente, String> tableViewColumnID;

    @FXML
    private TableColumn<Cliente, String> tableViewColumnNome;

    @FXML
    private TableColumn<Cliente, String> tableViewColumnCPF;

    @FXML
    private TextField textFieldFiltro;

    @FXML
    private Label labelID;

    @FXML
    private Label labelNome;

    @FXML
    private Label labelCPF;

    @FXML
    private Label labelRG;

    @FXML
    private Label labelDataNasc;

    @FXML
    private Label labelSexo;
    
    @FXML
    private Label labelCEP;

    @FXML
    private Label labelEstado;

    @FXML
    private Label labelCidade;

    @FXML
    private Label labelBairro;

    @FXML
    private Label labelRua;

    @FXML
    private Label labelNumero;

    @FXML
    private Label labelCelular;

    @FXML
    private Label labelResidencial;

    @FXML
    private Label labelEmail;
    
    @FXML
    private Button buttonInserir;
    
    @FXML
    private Button buttonFechar;
    
    @FXML
    private Button buttonAlterar;
    
    @FXML
    private Button buttonexcluir;
    
    private AnchorPane anchorPanePai;
    private List<Cliente> listCliente;
    private ObservableList<Cliente> observableListCliente;
    private DaoCliente dao = new DaoCliente();
    private Cliente cliente = null;

    public AnchorPane getAnchorPanePai() {
        return anchorPanePai;
    }

    public void setAnchorPanePai(AnchorPane anchorPanePai) {
        this.anchorPanePai = anchorPanePai;
    }
    
    void Initialize() {
        carregarTableView("");
        
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableView(newValue));
        
        textFieldFiltro.textProperty().addListener(
                (observable, oldValue, newValue) -> carregarTableView(newValue));
        
        buttonInserir.setOnAction((ActionEvent event) ->{
            clickButtonInserir();
        });
        
        buttonFechar.setOnAction((ActionEvent event) -> {
            clickButtonFechar();
        });
        
        buttonAlterar.setOnAction((ActionEvent event) -> {
            clickAlterar();
        });
        
        buttonexcluir.setOnAction((ActionEvent event) -> {
            clickRmv();
        });
    }
    
    private void carregarTableView(String str) {
        tableViewColumnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableViewColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableViewColumnCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        
        listCliente = dao.readAll(true, str);
                
        observableListCliente = FXCollections.observableArrayList(listCliente);
        tableView.setItems(observableListCliente);
    }
    
    private void selecionarItemTableView(Cliente aux) {
        this.cliente = aux;
        if (aux != null)
            getDTO(aux);
    }
    
    private void getDTO(Cliente aux) {
        DateFormat dt = new SimpleDateFormat("dd/MM/yyyy");

        labelID.setText(String.valueOf(aux.getId()));
        labelNome.setText(aux.getNome());
        labelCPF.setText(aux.getCpf());
        labelRG.setText(aux.getRg());
        labelDataNasc.setText(dt.format(aux.getNascimento()));
        if (aux.isSexo())
            labelSexo.setText("MASCULINO");
        else
            labelSexo.setText("FEMININO");
        
        labelCEP.setText(aux.getEndereco().getCep());
        labelEstado.setText(aux.getEndereco().getEstado());
        labelCidade.setText(aux.getEndereco().getCidade());
        labelBairro.setText(aux.getEndereco().getBairro());
        labelRua.setText(aux.getEndereco().getRua());
        labelNumero.setText(aux.getEndereco().getNumero());
        
        labelCelular.setText(aux.getContatos().getNumeroCelular());
        labelResidencial.setText(aux.getContatos().getNumeroResidencial());
        labelEmail.setText(aux.getContatos().getEmail());
    }
    
    private void clickButtonInserir() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/br/ifsp/visao/CadastrosClienteDialog.fxml") );
        AnchorPane pane;
        
        try {
            pane = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastro de Cliente");
            dialogStage.setResizable(false);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);
        
            CadastrosClienteDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);  
            dialogStage.showAndWait();
        } catch (IOException ex) {
            System.out.println("Erro" + ex);
        }      
        carregarTableView("");
    }        
    
    private void clickAlterar(){
        if(cliente == null){
           Alert alerta = new Alert(Alert.AlertType.INFORMATION);
           alerta.setTitle("Atenção");
           alerta.setHeaderText(null);
           alerta.setContentText("Para alterar um cadastro é necessário selecionar um cliente da tabela.");
           alerta.show(); 
           return;
        }
       
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/br/ifsp/visao/CadastrosClienteDialog.fxml") );
        AnchorPane pane;
        
        try {
            pane = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastro de Cliente");
            dialogStage.setResizable(false);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);
        
            CadastrosClienteDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.caseUpdate(cliente);
            dialogStage.showAndWait();
        } catch (IOException ex) {
            System.out.println("Erro" + ex);
        }      
        carregarTableView("");
    }
    
    private void clickRmv(){
        if(cliente == null){
           Alert alerta = new Alert(Alert.AlertType.INFORMATION);
           alerta.setTitle("Atenção");
           alerta.setHeaderText(null);
           alerta.setContentText("Para alterar um cadastro é necessário selecionar um cliente da tabela.");
           alerta.show(); 
           return;
        }
        
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Atenção");
        alerta.setHeaderText(null);
        
        if(dao.delete(cliente.getId()))          
           alerta.setContentText("Cliente desativado com sucesso.");
        else
            alerta.setContentText("Erro ao tentar desativar cliente.");          
        
        alerta.show();
        carregarTableView("");
    }
    
    private void clickButtonFechar() {
        anchorPanePai.getChildren().remove(0);
    }
     
}
