package com.company.authorizRegistr;

import com.company.menu.AdminMenuController;
import com.company.menu.UserMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class AuthorizController {
    private Socket clientSocket = null;
    private ObjectOutputStream writerObj = null;
    private ObjectInputStream readerObj = null;
    private String trans = "userMenu";
    private String[] resArr;
    private Stage stage = null;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField infField;

    @FXML
    private TextField inputLogin;

    @FXML
    private PasswordField inputPassword;

    @FXML
    void initialize(){
        btnLogin.setOnAction(actionEvent -> {
            try {
                if(Objects.equals(inputLogin.getText(), "") || Objects.equals(inputPassword.getText(), "")){
                    infField.setText("Поля должны быть заполнены!");
                } else {
                    String request = inputLogin.getText() + "=" + inputPassword.getText();

                    writerObj.writeObject("logIn");
                    writerObj.writeObject(request);

                    String res = (String) readerObj.readObject();
                    resArr = res.split(";");

                    if(Objects.equals(resArr[0], "admin") || Objects.equals(resArr[0], "user")){
                        if(Objects.equals(resArr[0], "admin")) trans = "adminMenu";

                        setWindow();
                    } else infField.setText(resArr[0]);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public void setWindow(){
        Stage stage1 = (Stage) btnLogin.getScene().getWindow();
        stage1.hide();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/" + trans + ".fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            if(Objects.equals(resArr[0], "admin")) {
                AdminMenuController controller = loader.getController();
                controller.getConnectAndUser(clientSocket, writerObj, readerObj, stage1, resArr[2], resArr[1]);
            } else {
                UserMenuController controller = loader.getController();
                controller.getConnectAndUser(clientSocket, writerObj, readerObj, stage1, resArr[2], resArr[1]);
            }

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getConnect(Socket clientSocket, ObjectOutputStream writerObj, ObjectInputStream readerObj, Stage stage){
        this.clientSocket = clientSocket;
        this.writerObj = writerObj;
        this.readerObj = readerObj;
        this.stage = stage;
    }
}
