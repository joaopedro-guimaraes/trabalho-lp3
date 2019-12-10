package br.ifsp.visao;

import br.ifsp.controle.dao.DaoCategoria;
import br.ifsp.controle.dao.DaoFornecedor;
import br.ifsp.controle.dao.DaoLote;
import br.ifsp.modelo.infoprod.Categoria;
import br.ifsp.modelo.infoprod.Fornecedor;
import br.ifsp.modelo.infoprod.Lote;
import br.ifsp.modelo.infoprod.Produto;
import br.ifsp.utils.FerramentasForms;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastroLoteDialogController {

    @FXML
    private TextField txtFieldLucro;

    @FXML
    private Button btnConfirmar;

    @FXML
    private TableView<Produto> tbViewProduto;

    @FXML
    private TableColumn<Produto, String> columnNomeProduto;

    @FXML
    private Button btnCancelar;

    @FXML
    private DatePicker pickerData;

    @FXML
    private TextField txtFieldValor;

    @FXML
    private TextField txtFieldId;

    @FXML
    private TableColumn<Produto, String> columnIDProduto;
    
    @FXML
    private ComboBox<Fornecedor> cbxFornecedor;
    
    private Stage dialogStage;    
    private Lote lote = null;
    private List<Fornecedor> listFornecedor;
    private DaoFornecedor daoFornecedor = new DaoFornecedor();
    private ObservableList<Fornecedor> observableListFornecedor;
         
    @FXML
    void initialize() {
        txtFieldId.setEditable(false);
        carregaFornecedores();
        
        btnConfirmar.setOnAction((ActionEvent event) -> {
            clickButtonConfirmar();
        });
        
        btnCancelar.setOnAction((ActionEvent event) -> {
            clickButtonCancelar();
        });
    }
    
    private void caseLoteNovo(){
        lote = new Lote();
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setCategoria(Lote lote) {
        this.lote = lote;
        setDto();
    }
        
    private void carregaFornecedores(){
       
        listFornecedor = daoFornecedor.readAll(true, "");
        observableListFornecedor = FXCollections.observableArrayList(listFornecedor);
        cbxFornecedor.setItems(observableListFornecedor);                
    }
    
    private void setDto(){        
        txtFieldId.setText(lote.getId()==0? "":Integer.toString(lote.getId()));
        txtFieldLucro.setText(String.valueOf(this.lote.getLucroPerct()));
        txtFieldValor.setText(String.valueOf(this.lote.getValor()));
        
        Instant instant = Instant.ofEpochMilli(lote.getData().getTime());
        LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        pickerData.setValue(localDate);
        
        cbxFornecedor.setValue(lote.getOrigem());
    }
    
    private void getDto(){
        if(lote == null){
            caseLoteNovo();
        }
        
        Instant x = pickerData.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant();
        lote.setData(Date.from(x));
        try{
        lote.setLucroPerct(Double.parseDouble(txtFieldLucro.getText()));
        lote.setValor(Double.parseDouble(txtFieldValor.getText()));
        }catch(Exception e){
            FerramentasForms.showDialogInformation("Entrada inválida", "Atenção");
            lote.setLucroPerct(-1);
            lote.setValor(-1);
        }
        lote.setOrigem(cbxFornecedor.getSelectionModel().getSelectedItem());
        lote.setStatus(true);
    }
    
    private void clickButtonConfirmar() {
        DaoLote banco = new DaoLote();
        getDto();
        if(lote.getLucroPerct()<0 || lote.getValor()<0 || lote.getOrigem()==null){
           FerramentasForms.showDialogInformation("É preciso corrigir campos para concluir operação", "Atenção");
           return;
        }            
        
        if(lote.getId()==0){
            if(banco.create(lote)){
                FerramentasForms.showDialogInformation("Cadastro concuído!", "Atenção");
                this.dialogStage.close();
                return;
            }
            
        }else
            if(banco.update(lote)){
                FerramentasForms.showDialogInformation("Atualização de cadastro concuída!", "Atenção");
                this.dialogStage.close();
                return;
            }
            FerramentasForms.showDialogInformation("Erro!", "Atenção");
    }
    
    private void clickButtonCancelar() {
        this.dialogStage.close();
    }
    
}
