package br.ifsp.visao;

import br.ifsp.controle.dao.DaoCliente;
import br.ifsp.modelo.Pessoa.Cliente;
import br.ifsp.modelo.Pessoa.Contato;
import br.ifsp.modelo.Pessoa.Endereco;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.time.ZoneId;
import java.util.Date;

public class CadastrosClienteDialogController {
    @FXML
    private TextField textFieldNome;

    @FXML
    private TextField textFieldCPF;

    @FXML
    private TextField textFieldRG;

    @FXML
    private ComboBox<String> comboBoxSexo;

    @FXML
    private DatePicker fieldDataNasc;

    @FXML
    private Button buttonEndereco;

    @FXML
    private Button buttonContato;

    @FXML
    private Button buttonConfirmar;

    @FXML
    private Button buttonCancelar;
    
    private Stage dialogStage;
    private Cliente cliente = null;
    
    // ObservableList criada para preencher o ComboBox
    ObservableList<String> listSexo = FXCollections.observableArrayList("MASCULINO", "FEMININO");
    
    @FXML
    void initialize() {
        comboBoxSexo.setItems(listSexo); 
        buttonConfirmar.setOnAction((ActionEvent event) -> {
            clickButtonConfirmar();
        });
        buttonCancelar.setOnAction((ActionEvent event) -> {
            clickButtonCancelar();
        });
    }
    
    public void caseUpdate(Cliente aux){
        cliente = aux;  
        setDto();
    }
    
    private void caseNewClient(){
        Cliente aux = new Cliente();
        aux.setContatos(new Contato());
        aux.setEndereco(new Endereco());
        cliente = aux;
    }
    
    private void setDto(){
        textFieldNome.setText(this.cliente.getNome());
        textFieldCPF.setText(this.cliente.getCpf());
        textFieldRG.setText(this.cliente.getRg());
        comboBoxSexo.setValue((cliente.isSexo())? "MASCULINO" :"FEMININO");  
        Instant instant = Instant.ofEpochMilli(cliente.getNascimento().getTime());
        LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        fieldDataNasc.setValue(localDate);
        System.out.println(cliente.isSexo() + "-" + ((cliente.isSexo())? "MASCULINO":"FEMININO"));
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }  
        
    private boolean clickButtonConfirmar() {
        DaoCliente banco = new DaoCliente();
        this.cliente.setNome(textFieldNome.getText());
        this.cliente.setCpf(textFieldCPF.getText());
        this.cliente.setRg(textFieldRG.getText());
        Instant x = fieldDataNasc.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant();
        this.cliente.setNascimento(Date.from(x));
        
        if ( comboBoxSexo.getValue().equals("MASCULINO") )
            this.cliente.setSexo(true);
        else
            this.cliente.setSexo(false);
        
        if ( verificaPraSalvar() ) {            
            this.dialogStage.close();
            return (cliente.getId()==0)? banco.create(cliente): banco.update(cliente);
        } else {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Atenção");
            alerta.setHeaderText(null);
            alerta.setContentText("Dados incompletos, por favor verifique.");
            alerta.show();
        }
        return false;
    }
        
    private void clickButtonCancelar() {
        this.dialogStage.close();
    }
    
    public void clickButtonEndereco() throws IOException {
        if(cliente==null)
            caseNewClient();
                
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/br/ifsp/visao/CadastrosEndereco.fxml") );
        
        AnchorPane page = (AnchorPane) loader.load();
        
        Stage stage = new Stage();
        stage.setTitle("Cadastro de Endereço");
        stage.setResizable(false);
        
        Scene scene = new Scene(page);
        stage.setScene(scene);
        
        CadastrosEnderecoController controller = loader.getController();
        controller.setDialogStage(stage);
        controller.setEndereco(cliente.getEndereco());
        controller.Initialize();
        
        stage.showAndWait();
    }
    
    public void clickButtonContato() throws IOException {
        if(cliente==null){
            caseNewClient();
            System.out.println("cliente é nulo");
        }
        
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/br/ifsp/visao/CadastrosContato.fxml") );
        
        AnchorPane page = (AnchorPane) loader.load();
        
        Stage stage = new Stage();
        stage.setTitle("Cadastro de Contato");
        stage.setResizable(false);
        
        Scene scene = new Scene(page);
        stage.setScene(scene);
                
        CadastrosContatoController controller = loader.getController();
        controller.setDialogStage(stage); 
        controller.start(cliente.getContatos());         
        
        stage.showAndWait();                   
    }
    
    private boolean verificaPraSalvar(){
        if(textFieldNome.getText().length()>6 && textFieldCPF.getText().length() == 10)
            if(cliente.getEndereco().getCidade().length()>4 && 
                    cliente.getEndereco().getRua().length()>6 && 
                        cliente.getEndereco().getNumero().length()>0)
                return true;
        return false;     
    }
    
}
