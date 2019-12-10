package br.ifsp.visao;
import br.ifsp.controle.dao.DaoCategoria;
import br.ifsp.controle.dao.DaoRegistroProduto;
import br.ifsp.modelo.infoprod.Categoria;
import br.ifsp.modelo.infoprod.RegistroProduto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastroRegistroProdutoDialogController {

    @FXML
    private TextField txfNome;

    @FXML
    private TextField txfCodigo;

    @FXML
    private Button btnSalvar;

    @FXML
    private TextField txfId;

    @FXML
    private TextField txfStatus;

    @FXML
    private ComboBox<Categoria> cbxCategoria;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextArea txaDescri;

    private Stage mystage;
    private RegistroProduto registro;
    
    @FXML
    void initialize() {
        
        txfId.setDisable(true);
        txfStatus.setDisable(true);
        
        btnSalvar.setOnAction((ActionEvent event) -> {
            salvar();
        });
        
        btnCancelar.setOnAction((ActionEvent event) -> {
            mystage.close();
        });
        
        carregaComboBox();
    }
    
    private void carregaComboBox(){
        
        DaoCategoria banco = new DaoCategoria();
        ObservableList<Categoria> observableListCat;
        observableListCat = FXCollections.observableArrayList(banco.readAll(true, ""));
        cbxCategoria.setItems(observableListCat);        
    }
    
    public void setRegistro(RegistroProduto aux){
        registro = aux;
        setDto();
    }
       
    public void viewMode(RegistroProduto aux){
        
        txfCodigo.setEditable(false);
        txfNome.setEditable(false);
        cbxCategoria.setEditable(false);        
        btnSalvar.setVisible(false);
        btnCancelar.setText("Ok");
        registro = aux;
        setDto();
    }
    
    public void setStage(Stage palco){
        mystage = palco;
    }
    private void caseNovoReg(){
        registro = new RegistroProduto();
    }
    
    private void salvar() {
        getDto();            
        DaoRegistroProduto banco = new DaoRegistroProduto();
        
        if(registro.getCategoria() != null && registro.getNome().length()!=0 && registro.getDescricao().length()!=0)
            if(registro.getId() == 0)
                banco.create(registro);
            else
                banco.update(registro);
        
        mystage.close();
    }
    
    private void setDto(){        
        
        txfId.setText(Integer.toString(registro.getId()));
        txfCodigo.setText(registro.getCodigo());
        txfNome.setText(registro.getNome());
        txfStatus.setText(registro.isStatus()?"Ativo":"Inativo");
        txaDescri.setText(registro.getDescricao());
        cbxCategoria.setValue(registro.getCategoria());
    }
    
    private void getDto(){
        
        if(registro == null)
            caseNovoReg();
        
        registro.setCategoria(cbxCategoria.getSelectionModel().getSelectedItem());
        registro.setCodigo(txfCodigo.getText() + "");
        registro.setDescricao(txaDescri.getText() + "");
        registro.setStatus(true);
        registro.setNome(txfNome.getText() + "");
    }
}
