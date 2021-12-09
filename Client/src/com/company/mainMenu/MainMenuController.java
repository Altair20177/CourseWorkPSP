package com.company.mainMenu;

import com.company.authorizRegistr.AuthorizController;
import com.company.authorizRegistr.RegistrController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class MainMenuController {
    private Socket clientSocket = null;
    private ObjectOutputStream writerObj = null;
    private ObjectInputStream readerObj = null;

    @FXML
    private Button btnAuthoriz;

    @FXML
    private Button btnRegistr;

    @FXML
    void initialize() {
        try {
            this.clientSocket = new Socket("127.0.0.1", 8000);

            writerObj = new ObjectOutputStream(clientSocket.getOutputStream());
            readerObj = new ObjectInputStream(clientSocket.getInputStream());

            btnAuthoriz.setOnAction(actionEvent -> {
                setWindow("authoriz");
            });

            btnRegistr.setOnAction(actionEvent -> {
                setWindow("registr");
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setWindow (String windowName){
        Stage stage1 = (Stage) btnAuthoriz.getScene().getWindow();
        stage1.hide();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/" + windowName + ".fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            if(Objects.equals(windowName, "registr")) {
                RegistrController controller = loader.getController();
                controller.getConnect(clientSocket, writerObj, readerObj, stage1);
            } else {
                AuthorizController controller = loader.getController();
                controller.getConnect(clientSocket, writerObj, readerObj, stage1);
            }

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
