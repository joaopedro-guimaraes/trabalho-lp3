package br.ifsp.visao;

import br.ifsp.controle.dao.DaoLote;
import br.ifsp.controle.dao.DaoProduto;
import br.ifsp.controle.dao.DaoRegistroProduto;
import br.ifsp.controle.dao.DaoProdutoVendido;
import br.ifsp.modelo.infoprod.Lote;
import br.ifsp.modelo.infoprod.Produto;
import br.ifsp.modelo.infoprod.ProdutoVendido;
import br.ifsp.modelo.infoprod.RegistroProduto;
import br.ifsp.utils.FerramentasForms;
import br.ifsp.utils.QueryLoteRegistroEmProduto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.List;

public class CadastroProdutoDialogController {

    @FXML
    private Button btnSalvar;

    @FXML
    private ComboBox<RegistroProduto> cbxRegistro;

    @FXML
    private TextField txfId;

    @FXML
    private TextField txfQuantidade;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txfStatus;

    @FXML
    private TextField txfValor;

    @FXML
    private ComboBox<Lote> cbxLote;
    
    private Produto produto = null;
    private ObservableList<RegistroProduto> observableRegistros;
    private ObservableList<Lote> observableLotes;
    private Stage myStage;
    
    @FXML
    void initialize(){
        txfId.setDisable(true);
        txfStatus.setDisable(true);
        preencheComboBox();
        
        btnSalvar.setOnAction((ActionEvent) -> {
            salvar();
        });
        
        btnCancelar.setOnAction((ActionEvent) -> {
            cancelar();
        });
    }
    
    public void setStage(Stage stage){
        myStage = stage;
    }
    
    public void setProduto(Produto aux){
        produto = aux;
        System.out.println(aux.getId()+" "+aux.getOrigem().getData()+" "+aux.getQuantidade());
        System.out.println(aux.getQuantidadeatual()+" "+aux.getRegistro().getNome()+" "+aux.getValor());
        setDto();        
    }
    
    private void casoNovoProduto(){
        produto = new Produto();
    }
    
    private void setDto(){
        if(produto == null)            
            return;
        
        txfId.setText(Integer.toString(produto.getId()));
        txfStatus.setText(produto.isStatus()?"Ativo":"Inativo");
        txfQuantidade.setText(Integer.toString(produto.getQuantidade()));
        txfValor.setText(Double.toString(produto.getValor()));
        cbxLote.setValue(produto.getOrigem());
        cbxRegistro.setValue(produto.getRegistro());
    }
    
    private void getDto(){
        if(produto == null)            
            casoNovoProduto();
        
        produto.setStatus(true);
        
       try{
           produto.setQuantidade(Integer.parseInt(txfQuantidade.getText()));
           produto.setValor(Double.parseDouble(txfValor.getText()));
           produto.setQuantidadeatual(produto.getQuantidade());
       }catch(Exception e){
           FerramentasForms.showDialogInformation("Não foi possível ler o valor ou a quantidade.", "Atenção.");
       }
        produto.setOrigem(cbxLote.getSelectionModel().getSelectedItem());
        produto.setRegistro(cbxRegistro.getSelectionModel().getSelectedItem());        
    }
    
    private void preencheComboBox(){
        
        DaoRegistroProduto daoRegistroProduto = new DaoRegistroProduto();
        DaoLote daoLote = new DaoLote();
        observableRegistros = FXCollections.observableArrayList(daoRegistroProduto.readAll(true, ""));
        observableLotes = FXCollections.observableArrayList(daoLote.readAll(true, ""));
        cbxLote.setItems(observableLotes);
        cbxRegistro.setItems(observableRegistros);
    }
    
    private void salvar(){
        DaoProduto banco = new DaoProduto();
        DaoProdutoVendido daoProdutoVendido = new DaoProdutoVendido();
        List<ProdutoVendido> listaProdutoVendido;
        getDto();
        
        //verificação de campos
        if (produto.getOrigem() != null && produto.getRegistro() != null && produto.getValor() >= 0) {
            if(produto.getId() == 0 && produto.getQuantidade() >= 0) {
                QueryLoteRegistroEmProduto loteRegistro = new QueryLoteRegistroEmProduto(produto.getOrigem().getId(), produto.getRegistro().getId());
                Produto aux = banco.read(loteRegistro);
                if (aux == null){
                   create(); 
                }else{
                    produto.setQuantidade(produto.getQuantidade() + aux.getQuantidade());
                    produto.setQuantidadeatual(produto.getQuantidadeatual() + aux.getQuantidadeatual());
                    produto.setId(aux.getId());
                    update();
                }                    
            }else{
                    listaProdutoVendido = daoProdutoVendido.readAll(produto);
                    int cont=0;
                    for(ProdutoVendido t : listaProdutoVendido)
                        cont+= t.getQuantidade();
                if(produto.getQuantidade() - cont >= 0){
                    produto.setQuantidadeatual(produto.getQuantidade() - cont);
                    update();
                }
            }
        }else 
            FerramentasForms.showDialogInformation("Erro, algum campo inválido ou incompleto!", "Atenção");
             
}
    
    private void cancelar(){
        myStage.close();
    }
    
    private void update(){
        DaoProduto banco = new DaoProduto();
        if (banco.update(produto)) {
            FerramentasForms.showDialogInformation("Cadastro atualizado!", "Atenção");
            myStage.close();
        }
    }
    
    private void create(){
        DaoProduto banco = new DaoProduto();
        if (banco.create(produto)) {
            FerramentasForms.showDialogInformation("Cadastro concluído!", "Atenção");
            myStage.close();
        }
    }
}
