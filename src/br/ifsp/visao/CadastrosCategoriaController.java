package br.ifsp.visao;

import br.ifsp.controle.dao.DaoCategoria;
import br.ifsp.controle.dao.DaoRegistroProduto;
import br.ifsp.modelo.infoprod.Categoria;
import br.ifsp.utils.FerramentasForms;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CadastrosCategoriaController {

    @FXML
    private TableView<Categoria> tableView;

    @FXML
    private TableColumn<Categoria, String> tableViewColumnID;

    @FXML
    private TableColumn<Categoria, String> tableViewColumnNome;

    @FXML
    private Label gridPaneLabelID;

    @FXML
    private Label gridPaneLabelNome;
    
    @FXML
    private TextField textFieldFiltro;
    
    @FXML
    private Button buttonFechar;
    
    @FXML
    private Button buttonAlterar;
    
    @FXML
    private Button buttonInserir;
    
    @FXML
    private Button buttonRemover;
    
    @FXML
    private CheckBox checkBoxAtivos;
    
    @FXML
    private CheckBox checkBoxNaoAtivos;
    
    private AnchorPane anchorPanePai;
    private List<Categoria> listCategoria;
    private ObservableList<Categoria> observableListCategoria;
    private final DaoCategoria banco = new DaoCategoria();

    public AnchorPane getAnchorPanePai() {
        return anchorPanePai;
    }

    public void setAnchorPanePai(AnchorPane anchorPanePai) {
        this.anchorPanePai = anchorPanePai;
    }

    void Initialize() {
        preencheLista();
        
        // Adiciono um Listener para pegar o objeto selecionado no TableView
        // e adicionar suas informações nos Labels.
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableView(newValue));
        
        textFieldFiltro.textProperty().addListener(
                (observable, oldValue, newValue) -> preencheLista());
        
        
    }

    private void carregarTableView() {
        tableViewColumnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableViewColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
       
        observableListCategoria = FXCollections.observableArrayList(listCategoria);
        tableView.setItems(observableListCategoria);
    }
    
    @FXML
    private void preencheLista(){
        
        if((checkBoxAtivos.isSelected() && checkBoxNaoAtivos.isSelected()) || (!checkBoxAtivos.isSelected() && !checkBoxNaoAtivos.isSelected()))
            listCategoria = banco.readAll(textFieldFiltro.getText());
        else
            if(checkBoxAtivos.isSelected() && !checkBoxNaoAtivos.isSelected())
                listCategoria = banco.readAll(true, textFieldFiltro.getText());
            else  
                listCategoria = banco.readAll(false, textFieldFiltro.getText());
        carregarTableView();
    }
    
    private void selecionarItemTableView(Categoria categoria) {
        if(categoria == null)
            return;
        gridPaneLabelID.setText(String.valueOf( categoria.getId() ));
        gridPaneLabelNome.setText( categoria.getNome() );
    }

    public void clickButtonInserir() throws IOException {
        Categoria categoria = new Categoria();
        // passo um objeto para o dialogController, caso a operação de complete
        // (clique do botão Confirmar da janela DialogController) o método retorna TRUE.
        boolean clickConfirmar = showCadastrosCategoriaDialog(categoria);

        if (clickConfirmar) {
            banco.create(categoria);
            preencheLista();
        }
    }

    public void clickButtonAlterar() throws IOException {
        Categoria categoria = tableView.getSelectionModel().getSelectedItem();

        if (categoria != null) {
            // passo um objeto para o dialogController, caso a operação de complete
            // (clique do botão Confirmar da janela DialogController) o método retorna TRUE.
            boolean clickConfirmar = showCadastrosCategoriaDialog(categoria);
            
            if (clickConfirmar) {
                banco.update(categoria);
                preencheLista();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, selecione um item da tabela.");
            alert.show();
        }
    }
    
    public void clickButtonRemover() {
        Categoria categoria = tableView.getSelectionModel().getSelectedItem();

        if (categoria != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Tem certeza que deseja excluir essa categoria?");
            Optional <ButtonType> result = alert.showAndWait();
            
            // Exibe a janela de alerta e espera o usuário clicar em um botão,
            // verifica se o botão clicado é o "OK", se sim exclui o objeto do banco.
            if ( result.isPresent() && (result.get() == ButtonType.OK) ) {
                if(banco.delete(categoria.getId())){
                    DaoRegistroProduto dao = new DaoRegistroProduto();
                        if(dao.desativaPorCategoria(categoria.getId())){
                            FerramentasForms.showDialogInformation("Categoria e seus registros desativados.", "Atenção!");
                        }
                }
                preencheLista();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, selecione um item da tabela.");
            alert.show();
        }
    }
    
    public void clickButtonFechar() {
        anchorPanePai.getChildren().remove(0);
    }

    private boolean showCadastrosCategoriaDialog(Categoria categoria) throws IOException {
        // Cria um objeto com a tela escolhida
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/br/ifsp/visao/CadastrosCategoriaDialog.fxml") );
        
        // Carrega a tela para o pane
        AnchorPane page = (AnchorPane) loader.load();
        
        // Criando um stage para o dialog
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Categoria");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        
        // Setando a categoria no controller
        CadastrosCategoriaDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setCategoria(categoria);
        
        // Mostra o dialog e espera até fechar
        dialogStage.showAndWait();
        
        return controller.isCliqueButtonConfirmar();
    }
    
}
