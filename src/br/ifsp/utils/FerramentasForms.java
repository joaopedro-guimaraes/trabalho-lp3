/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsp.utils;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Thon
 */
public class FerramentasForms {
    
    public static void showDialogInformation(String mensagem, String titulo){
        
        Stage palco;
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Panda Cub");
        alerta.setHeaderText("Atenção");
        alerta.setContentText(mensagem);
        palco = (Stage) alerta.getDialogPane().getScene().getWindow();
        palco.getIcons().add(new Image("br/ifsp/images/Panda.png"));        
        alerta.show();
    }
}
