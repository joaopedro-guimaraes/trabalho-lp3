package br.ifsp.visao;

import br.ifsp.modelo.Pessoa.Endereco;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastrosEnderecoController {

    @FXML
    private TextField textFieldCEP;

    @FXML
    private TextField textFieldCidade;

    @FXML
    private TextField textFieldBairro;

    @FXML
    private ComboBox<String> comboBoxEstado;

    @FXML
    private TextField textFieldRua;

    @FXML
    private TextField textFieldNumero;

    @FXML
    private TextArea textAreaComplemento;

    @FXML
    private Button buttonConfirmar;
    
    @FXML
    private Button buttonCancelar;
    
    // ObservableList criada para preencher o ComboBox
    ObservableList<String> listEstados = FXCollections.observableArrayList(
            "ACRE", "ALAGOAS", "AMAPÁ", "AMAZONAS", "BAHIA", "CEARÁ",
            "DISTRITO FEDERAL", "ESPÍRITO SANTO", "GOIÁS", "MARANHÃO",
            "MATO GROSSO", "MATO GROSSO DO SUL", "MINAS GERAIS", "PARÁ",
            "PARAÍBA", "PARANÁ", "PERNAMBUCO", "PIAUÍ", "RIO DE JANEIRO",
            "RIO GRANDE DO NORTE", "RIO GRANDE DO SUL", "RONDÔNIA", "RORAIMA",
            "SANTA CATARINA", "SÃO PAULO", "SERGIPE", "TOCANTINS"
    );
    
    private Stage dialogStage;    
    private Endereco endereco;
    
    // Getters e Setters
    @FXML
    void Initialize() {
        comboBoxEstado.setItems(listEstados);
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }   
        
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
        setDTOEndereco();
    }   
    
    public void clickButtonConfirmar() {
        getDTOEndereco();        
        this.dialogStage.close();
    }
    
    public void clickButtonCancelar() {
        this.dialogStage.close();
    }
    
    private void setDTOEndereco() {
        textFieldCEP.setText(endereco.getCep());
        comboBoxEstado.setValue(endereco.getEstado());
        textFieldCidade.setText(endereco.getCidade());
        textFieldBairro.setText(endereco.getBairro());
        textFieldRua.setText(endereco.getRua());
        textFieldNumero.setText(endereco.getNumero());
        textAreaComplemento.setText(endereco.getComplemento());       
    }
    
    private void getDTOEndereco() {
        endereco.setCep(textFieldCEP.getText());
        endereco.setEstado(comboBoxEstado.getValue());
        endereco.setCidade(textFieldCidade.getText());
        endereco.setBairro(textFieldBairro.getText());
        endereco.setRua(textFieldRua.getText());
        endereco.setNumero(textFieldNumero.getText());
        endereco.setComplemento(textAreaComplemento.getText());      
    }
    
}