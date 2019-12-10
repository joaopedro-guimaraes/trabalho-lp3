package br.ifsp.visao;

import br.ifsp.controle.dao.DaoVendedor;
import br.ifsp.modelo.Pessoa.Vendedor;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

public class InicioController {

    @FXML
    private MenuItem menuItemCadastrosCategoria;
    
    @FXML
    private MenuItem menuItemCadastrosCliente;
    
    @FXML
    private MenuItem menuItemCadastrosFuncionario;
    
    @FXML
    private MenuItem menuItemCadastrosProdutos;
    
    @FXML
    private MenuItem menuItemCadastroLote;
    
    @FXML
    private MenuItem menuItemCadastroEstoque;
    
    @FXML
    private MenuItem menuItemCadastroFornecedor;
    
    @FXML
    private MenuItem menuItemProcessosVenda;
    
    @FXML
    private MenuItem menuItemConsultaVenda;
    
    @FXML
    private AnchorPane anchorPane;
    
    private Vendedor user;

    public void setUser(Vendedor user) {
        this.user = user;
        if(!user.isCargo())
            menuItemCadastrosFuncionario.setDisable(true);
    }
    
    @FXML
    void initialize() {
//        System.out.println(this.user.getNome());
    }
    
    public void clickMenuItemCadastrosCategoria() {
        //cria um objeto com a tela escolhida
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ifsp/visao/CadastrosCategoria.fxml"));
        
        //carrega a tela para o pane
        AnchorPane a; 
        try {
            a = (AnchorPane) loader.load();
            anchorPane.getChildren().setAll(a);
        
            //obtem um ponteiro para o controller da tela criada para pode acessar seus métodos
            CadastrosCategoriaController controle = (CadastrosCategoriaController) loader.getController();
            controle.setAnchorPanePai(anchorPane);
            controle.Initialize();
        } catch (IOException ex) {
            Logger.getLogger(InicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(RuntimeException e){
            Logger.getLogger(InicioController.class.getName()).log(Level.SEVERE, null, e);
        }
        
    }
    
    public void menuItemProcessosVenda() {
        //cria um objeto com a tela escolhida
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ifsp/visao/Vendas.fxml"));
        
        //carrega a tela para o pane
        AnchorPane a; 
        try {
            a = (AnchorPane) loader.load();
            anchorPane.getChildren().setAll(a);
            /*DaoVendedor dao = new DaoVendedor();
            Vendedor aux = dao.read(5);*/
            //obtem um ponteiro para o controller da tela criada para pode acessar seus métodos
            VendasController controle = (VendasController) loader.getController();
            controle.setVendedor(user);
            controle.setPanePai(anchorPane);
            
        } catch (IOException ex) {
            Logger.getLogger(InicioController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void clickMenuItemCadastrosCliente()  {
        //cria um objeto com a tela escolhida
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ifsp/visao/CadastrosCliente.fxml"));
        
        //carrega a tela para o pane
        AnchorPane a; 
        try {
            a = (AnchorPane) loader.load();
            anchorPane.getChildren().setAll(a);
        } catch (IOException ex) {
            Logger.getLogger(InicioController.class.getName()).log(Level.SEVERE, null, ex);
        }        
        //obtem um ponteiro para o controller da tela criada para pode acessar seus métodos
        CadastrosClienteController controle = (CadastrosClienteController) loader.getController();
        controle.setAnchorPanePai(anchorPane);
        controle.Initialize();
    }
    
    public void clickMenuItemCadastrosFuncionario() throws IOException {
        //cria um objeto com a tela escolhida
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ifsp/visao/CadastrosFuncionario.fxml"));
        
        //carrega a tela para o pane
        AnchorPane a = (AnchorPane) loader.load(); 
        anchorPane.getChildren().setAll(a);        
        
        //obtem um ponteiro para o controller da tela criada para pode acessar seus métodos
        CadastrosFuncionarioController controle = (CadastrosFuncionarioController) loader.getController();
        controle.setAnchorPane(a);
        controle.initialize();        
    }
    
    public void clickMenuItemCadastrosProdutos() throws IOException {
        //cria um objeto com a tela escolhida
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ifsp/visao/CadastrosRegistroProduto.fxml"));
        
        //carrega a tela para o pane
        AnchorPane a = (AnchorPane) loader.load(); 
        anchorPane.getChildren().setAll(a);        
        
        //obtem um ponteiro para o controller da tela criada para pode acessar seus métodos
        CadastrosRegistroProdutoController controle = (CadastrosRegistroProdutoController) loader.getController();
        controle.setAnchorPane(a);
        controle.initialize();        
    }
    
    public void clickMenuItemCadastrosFornecedor() throws IOException {
        //cria um objeto com a tela escolhida
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ifsp/visao/CadastroFornecedor.fxml"));
        
        //carrega a tela para o pane
        AnchorPane a = (AnchorPane) loader.load(); 
        anchorPane.getChildren().setAll(a);        
        
        //obtem um ponteiro para o controller da tela criada para pode acessar seus métodos
        CadastroFornecedorController controle = (CadastroFornecedorController) loader.getController();
        controle.setPanePai(a);
        controle.initialize();        
    }
    
    public void clickMenuItemCadastroLote() throws IOException {
        //cria um objeto com a tela escolhida
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ifsp/visao/CadastroLote.fxml"));
        
        //carrega a tela para o pane
        AnchorPane a = (AnchorPane) loader.load(); 
        anchorPane.getChildren().setAll(a);        
        
        //obtem um ponteiro para o controller da tela criada para pode acessar seus métodos
        CadastroLoteController controle = (CadastroLoteController) loader.getController();
        controle.setPanePai(a);
        controle.initialize();        
    }
    
    public void clickMenuItemCadastroEstoque()  {
        //cria um objeto com a tela escolhida
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ifsp/visao/CadastroProduto.fxml"));
        
        //carrega a tela para o pane
        AnchorPane a; 
        try {
            a = (AnchorPane) loader.load();
            anchorPane.getChildren().setAll(a);        
        
            //obtem um ponteiro para o controller da tela criada para pode acessar seus métodos
            CadastroProdutoController controller = loader.getController();
            controller.setPane(a);
            controller.initialize();    
        } catch (IOException ex) {
            System.out.println("Erro" + ex);
        }            
    }
    
    public void clickMenuItemConsultaVenda()  {
        //cria um objeto com a tela escolhida
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ifsp/visao/GerenciaVendas.fxml"));
        
        //carrega a tela para o pane
        AnchorPane a; 
        try {
            a = (AnchorPane) loader.load();
            anchorPane.getChildren().setAll(a);        
        
            //obtem um ponteiro para o controller da tela criada para pode acessar seus métodos
            GerenciaVendasController controller = loader.getController();
            controller.setPane(a);
            controller.initialize();    
        } catch (IOException ex) {
            System.out.println("Erro" + ex);
        }            
    }
}
