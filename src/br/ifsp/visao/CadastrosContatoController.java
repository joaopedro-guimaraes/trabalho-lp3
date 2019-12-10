package br.ifsp.visao;

import br.ifsp.modelo.Pessoa.Contato;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastrosContatoController {

    @FXML
    private TextField textFieldCelular;

    @FXML
    private TextField textFieldResidencial;

    @FXML
    private TextField textFieldEmail;

    @FXML
    private Button buttonConfirmar;

    @FXML
    private Button buttonCancelar;
    
    private Contato contato;   
    private Stage dialogStage;  
    
    @FXML
    void initialize() {      
        
    }       
    
    public void start(Contato aux) {
        contato = aux;      
        if(contato==null)
        System.out.println("não fode");
        setDTOContato();
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void clickButtonConfirmar() {
        getDTOContato(this.contato);        
        this.dialogStage.close();       
    }
    
    public void clickButtonCancelar() {
        this.dialogStage.close();         
    }
    
    private void getDTOContato(Contato contato) {        
        contato.setNumeroCelular(textFieldCelular.getText());
        contato.setNumeroResidencial(textFieldResidencial.getText());
        contato.setEmail(textFieldEmail.getText());
    }
    
    private void setDTOContato() {        
        textFieldCelular.setText(contato.getNumeroCelular());
        textFieldResidencial.setText(contato.getNumeroResidencial());
        textFieldEmail.setText(contato.getEmail());
    }
}
