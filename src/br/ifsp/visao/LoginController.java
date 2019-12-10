package br.ifsp.visao;

import br.ifsp.controle.dao.DaoVendedor;
import br.ifsp.modelo.Pessoa.Vendedor;
import br.ifsp.utils.CarregaLogin;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    TextField txfLogin;

    @FXML
    PasswordField txfSenha;

    @FXML
    Button btnEntra;
    
    private Vendedor user;
    private Stage stage;
    
    public Vendedor getUser() {
        return user;
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    void initialize() {
        DaoVendedor daoVendedor = new DaoVendedor();
        if(daoVendedor.ContFuncionario() == 0)
            primeiroFuncionario();
    }

    private void primeiroFuncionario() {
        
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/br/ifsp/visao/CadastroFuncionarioDialog.fxml"));
        AnchorPane pane;
        try {
            pane = (AnchorPane) loader.load();            
            Scene cena = new Scene(pane);
            
            Stage palco = new Stage();
            palco.setTitle("Cadastro de Funcionário");
            palco.setResizable(false);
            palco.setScene(cena);
            
            CadastroFuncionarioDialogController controller; 
            controller = loader.getController();  
            controller.setStage(palco);
            
            palco.showAndWait();
            
        } catch (IOException ex) {
            System.out.println(ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro ao tentar abrir form para um novo funcionário.");
            alert.show();
        }     
    }
    
    public void ClickEntrar(ActionEvent e) throws IOException {
        user = verificaLogin( txfLogin.getText(), txfSenha.getText() );
        
        if (user != null) {            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ifsp/visao/Inicio.fxml"));
            Parent loginParent = loader.load();
            InicioController controle = loader.getController();
            controle.setUser(user);
            Scene loginScene = new Scene(loginParent);
            Stage stage = new Stage();
            stage.setScene(loginScene);
            stage.setResizable(false);

//            InicioController controller = loader.getController();
//            controller.setUser(user);
//            controller.initialize();
            stage.show();
            
        } else {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Atenção");
            alerta.setHeaderText(null);
            alerta.setContentText("Login ou Senha incorretos, por favor verifique.");
            alerta.show();
        }
    }
    
    private Vendedor verificaLogin(String login, String senha) {
        DaoVendedor dao = new DaoVendedor();
        CarregaLogin checkLogin = new CarregaLogin();
        checkLogin.login = login;
        checkLogin.senha = senha;
        
        return dao.login(checkLogin);
    }

}
