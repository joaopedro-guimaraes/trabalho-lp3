/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsp.visao;

import br.ifsp.controle.dao.DaoVendedor;
import br.ifsp.modelo.Pessoa.Contato;
import br.ifsp.modelo.Pessoa.Endereco;
import br.ifsp.modelo.Pessoa.Vendedor;
import br.ifsp.utils.FerramentasForms;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Thon
 */
public class CadastroFuncionarioDialogController implements Initializable {

    @FXML
    private TextField txfNome;

    @FXML
    private DatePicker dtpNasc;

    @FXML
    private ComboBox<String> cbxSexo;

    @FXML
    private Button btnFecha;

    @FXML
    private Button btnEndereco;

    @FXML
    private TextField txfId;

    @FXML
    private Button btnSalva;

    @FXML
    private TextField txfStatus;

    @FXML
    private TextField txfCargo;

    @FXML
    private TextField txfCpf;

    @FXML
    private TextField txfRg;
    
    @FXML
    private TextField txfLogin;
    
    @FXML
    private TextField txfSenha;

    @FXML
    private Button btnContato;   
    
    ObservableList<String> listSexo = FXCollections.observableArrayList("MASCULINO", "FEMININO");
        
    private Stage myStage;
    private Vendedor funcionario=null;
    private int contPergunta=0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        DaoVendedor banco = new DaoVendedor();
        if(banco.ContFuncionario() == 0)
            txfCargo.setText("ADMINISTRADOR");
        else
            txfCargo.setText("Vendedor");
        cbxSexo.setItems(listSexo);
        txfStatus.setEditable(false);
        txfId.setEditable(false);
        txfCargo.setEditable(false);
                         
        btnFecha.setOnAction((ActionEvent event) -> {
            fecha();
        });
        
        btnSalva.setOnAction((ActionEvent event) -> {
            salva();
        });
        
        btnEndereco.setOnAction((ActionEvent event) -> {
           chamaEndereco();
        });
        
        btnContato.setOnAction((ActionEvent event) -> {
            chamaContato();
        });
        
        txfCpf.setOnKeyReleased((KeyEvent event) -> {
           verificaCpf();
        });
        
    }    
    
    public void setStage(Stage palco){
        myStage = palco;
    }
    
    public void setFuncionario(Vendedor aux){
        funcionario = aux;
        setDto();
    }
        
    private void casoNovoFunc(){
        Endereco end = new Endereco();
        Contato cont = new Contato();
        funcionario = new Vendedor();
        funcionario.setEndereco(end);
        funcionario.setContatos(cont);
    }
    
    private void fecha(){
        myStage.close();
    }
    
    private void salva(){
        DaoVendedor banco = new DaoVendedor();
        getDto();        
        try{
        if(funcionario.getNome().length()>6 && funcionario.getCpf().length() == 11){
            if(funcionario.getEndereco().getCidade().length()>4 && funcionario.getEndereco().getRua().length()>6 && funcionario.getEndereco().getNumero().length()>0){
                if(funcionario.getId() == 0 && banco.read(funcionario.getCpf()) == null){
                    banco.create(funcionario);
                    FerramentasForms.showDialogInformation("Funcionario cadastrado com sucesso!", "Atenção");
                    myStage.close();
                }else
                    if(banco.read(funcionario.getId()).getId() == funcionario.getId()){
                        banco.update(funcionario);
                        FerramentasForms.showDialogInformation("Cadastro atualizado!", "Atenção");
                        myStage.close();
                    }
            }
        }else
            FerramentasForms.showDialogInformation("Informações incompletas.", "Atenção");  
        }
        catch(NullPointerException e){
            System.out.println("merda"+e);
        }
    }
    
    private void verificaCpf(){
        
        DaoVendedor banco = new DaoVendedor();
        Stage stage;        
        Vendedor aux = banco.read(txfCpf.getText());
        
        if(aux != null && contPergunta == 0){
            contPergunta++;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("CPF já registrado, deseja carregar funcionário?");
            stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("br/ifsp/images/Panda.png"));
            Optional <ButtonType> result = alert.showAndWait();
            
            
            if ( result.isPresent() && (result.get() == ButtonType.OK) ) {                
                funcionario = aux;
                setDto();
            }          
        }       
    }
    
    private void chamaEndereco(){
       if(funcionario == null)
           casoNovoFunc();
       
        try {
            FXMLLoader loader = new FXMLLoader( getClass().getResource("/br/ifsp/visao/CadastrosEndereco.fxml"));
            AnchorPane root = (AnchorPane) loader.load(); 
            
            Scene cena = new Scene(root);
            Stage stage = new Stage();
            
            stage.setScene(cena);            
            stage.setTitle("Cadastro de Endereço");
            stage.setResizable(false);
            stage.getIcons().add(new Image("br/ifsp/images/Panda.png"));
            
            CadastrosEnderecoController controller;
            controller = loader.getController();
            controller.setDialogStage(stage);
            controller.setEndereco(funcionario.getEndereco());
            controller.Initialize();
            
            stage.showAndWait();
            
        } catch (IOException ex) {
            System.out.println(ex);
            FerramentasForms.showDialogInformation("Erro ao tentar abrir form de endereço.", "Atenção");
            /*Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro ao tentar abrir form de endereço.");
            alert.show();*/
        }
    }
    
    private void chamaContato(){
        if(funcionario == null)
           casoNovoFunc();
       
        try {
            FXMLLoader loader = new FXMLLoader( getClass().getResource("/br/ifsp/visao/CadastrosContato.fxml"));
            AnchorPane root = (AnchorPane) loader.load(); 
            
            Scene cena = new Scene(root);
            Stage stage = new Stage();
            
            stage.setScene(cena);            
            stage.setTitle("Cadastro de Endereço");
            stage.setResizable(false);
            stage.getIcons().add(new Image("br/ifsp/images/Panda.png"));
            
            CadastrosContatoController controller;
            controller = loader.getController();
            controller.setDialogStage(stage);
            controller.start(funcionario.getContatos());
            
            stage.showAndWait();
            
        } catch (IOException ex) {
            System.out.println(ex);
            FerramentasForms.showDialogInformation("Erro ao tentar abrir form de endereço.", "Atenção");
            /*Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro ao tentar abrir form de endereço.");
            alert.show();*/
        }
    }
    private void setDto(){
        
        txfId.setText(Integer.toString(funcionario.getId()));
        txfNome.setText(funcionario.getNome() + "");
        txfCpf.setText(funcionario.getCpf() + "");
        txfRg.setText(funcionario.getRg() + "");        
        Instant instant = Instant.ofEpochMilli(funcionario.getNascimento().getTime());
        LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        dtpNasc.setValue(localDate);
        cbxSexo.setValue(funcionario.isSexo()?"MASCULINO":"FEMININO");
        txfCargo.setText(funcionario.isCargo()?"Administrador":"Vendedor");
        txfStatus.setText("Ativo");
    }
    private void getDto(){
       
        if(funcionario == null)
            casoNovoFunc();
       try{ 
        funcionario.setId((txfId.getText().equals(""))? 0 : Integer.parseInt(txfId.getText()));
        funcionario.setNome(txfNome.getText());
        funcionario.setCpf(txfCpf.getText());
        funcionario.setRg(txfRg.getText());
        funcionario.setCargo( txfCargo.getText().equals("ADMINISTRADOR")? true:false);
        Instant x = dtpNasc.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant();
        funcionario.setNascimento(Date.from(x));        
        if(cbxSexo.getValue().equals("MASCULINO"))
            funcionario.setSexo(true);
        else
            funcionario.setSexo(false);
        funcionario.setStatus(true);
        
        funcionario.setSenha(txfSenha.getText()  + "");
        funcionario.setLogin(txfLogin.getText() + "");
       }catch(NullPointerException e){
           System.out.println(e);
       }
       
    }
}
