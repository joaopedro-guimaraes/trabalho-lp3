package br.ifsp.visao;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        
        launch();
        //exit(0);
    }

    @Override
    public void start(Stage frame) throws Exception {
        // TODO Auto-generated method stub
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Scene cena = new Scene(root);
            frame.getIcons().add(new Image("br/ifsp/images/Panda.png"));
            frame.setTitle("Panda Technology Solutions");
            frame.setScene(cena);            
            frame.setResizable(false);
            
            LoginController controller = loader.getController();
            controller.setStage(frame);            
            
            frame.show();
            controller.initialize();
        } catch (IOException e) {
            System.out.println("Erro meus amigos " + e);
        }

    }

}
