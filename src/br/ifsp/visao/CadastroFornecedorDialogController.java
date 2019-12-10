package br.ifsp.visao;

import br.ifsp.controle.dao.DaoFornecedor;
import br.ifsp.modelo.Pessoa.Contato;
import br.ifsp.modelo.infoprod.Fornecedor;
import br.ifsp.utils.FerramentasForms;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CadastroFornecedorDialogController {
    @FXML
    private TextField txtFieldNome;

    @FXML
    private TextField txtFieldCNPJ;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnContato;
    
    @FXML
    private Button btnConfirmar;
    
    private Stage dialogStage;
    private Fornecedor fornecedor = null;

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    void initialize() {
        btnConfirmar.setOnAction((ActionEvent event) -> {
            clickButtonConfirmar();
        });
        
        btnCancelar.setOnAction((ActionEvent event) -> {
            clickButtonCancelar();
        });
        
        btnContato.setOnAction((ActionEvent event) -> {
            chamaContato();
        });
    }
    
    public void caseNewFornecedor(){
        this.fornecedor = new Fornecedor();
        this.fornecedor.setContato(new Contato());
    }
    
    public void caseUpdate(Fornecedor aux) {
        this.fornecedor = aux;  
        setDto();
    }
    
    private void setDto() {
        txtFieldNome.setText(this.fornecedor.getNome());
        txtFieldCNPJ.setText(this.fornecedor.getCnpj());
    }
    
    private boolean clickButtonConfirmar() {
        DaoFornecedor banco = new DaoFornecedor();
        this.fornecedor.setNome(txtFieldNome.getText());
        this.fornecedor.setCnpj(txtFieldCNPJ.getText());
        
        if ( this.fornecedor.getContato() != null ) {            
            this.dialogStage.close();
            return (fornecedor.getId()==0)? banco.create(fornecedor): banco.update(fornecedor);
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
    
    private void chamaContato(){
        if(fornecedor == null)
           caseNewFornecedor();
       
        try {
            FXMLLoader loader = new FXMLLoader( getClass().getResource("/br/ifsp/visao/CadastrosContato.fxml"));
            AnchorPane root = (AnchorPane) loader.load(); 
            
            Scene cena = new Scene(root);
            Stage stage = new Stage();
            
            stage.setScene(cena);            
            stage.setTitle("Cadastro de Contato");
            stage.setResizable(false);
            stage.getIcons().add(new Image("br/ifsp/images/Panda.png"));
            
            CadastrosContatoController controller;
            controller = loader.getController();
            controller.setDialogStage(stage);
            controller.start(fornecedor.getContato());
            
            stage.showAndWait();
            
        } catch (IOException ex) {
            System.out.println(ex);
            FerramentasForms.showDialogInformation("Erro ao tentar abrir form de contato.", "Atenção");
        }
    }
}
