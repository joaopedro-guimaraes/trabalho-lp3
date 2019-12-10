package br.ifsp.visao;

import br.ifsp.modelo.infoprod.Categoria;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastrosCategoriaDialogController {

    @FXML
    private TextField textFieldNome;

    @FXML
    private Button buttonCancelar;

    @FXML
    private Button buttonConfirmar;
    
    private Stage dialogStage;
    private boolean clickConfirmar = false;
    private Categoria categoria;
    
    // Getters e Setters
    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isCliqueButtonConfirmar() {
        return clickConfirmar;
    }

    public void setCliqueButtonConfirmar(boolean clickConfirmar) {
        this.clickConfirmar = clickConfirmar;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
        this.textFieldNome.setText(categoria.getNome());
    }
    
    void Initialize() {
        
    }
    
    public void clickButtonConfirmar() {
        this.categoria.setNome(textFieldNome.getText());
        this.clickConfirmar = true;
        this.dialogStage.close();
    }
    
    public void clickButtonCancelar() {
        this.dialogStage.close();
    }
    
}
