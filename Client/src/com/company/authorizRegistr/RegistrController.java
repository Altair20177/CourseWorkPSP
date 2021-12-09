package com.company.authorizRegistr;

import com.company.entities.User;
import com.company.menu.AdminMenuController;
import com.company.menu.UserMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class RegistrController {
    private Socket clientSocket = null;
    private ObjectOutputStream writerObj = null;
    private ObjectInputStream readerObj = null;
    private Stage stage = null;

    private static final String IS_ADMIN = "Altair";
    private static final String ADD_USER = "add user";
    private static final String CHECK_USER = "check user";

    @FXML
    private Button btnCreate;

    @FXML
    private TextField inputFIO;

    @FXML
    private PasswordField inputIsAdmin;

    @FXML
    private TextField inputLogin;

    @FXML
    private PasswordField inputPass;

    @FXML
    private TextField inputPost;

    @FXML
    private PasswordField repeatPass;

    @FXML
    private Label labelInf;

    @FXML
    void initialize() {
        btnCreate.setOnAction(actionEvent -> {
            String FIO, login, pass, post, repPass, isAdmin, newWindow = "userMenu";
            int role = 0;

            FIO = inputFIO.getText();
            login = inputLogin.getText();
            pass = inputPass.getText();
            isAdmin = inputIsAdmin.getText();
            post = inputPost.getText();
            repPass = repeatPass.getText();

            if(Objects.equals(FIO, "") || Objects.equals(login, "") || Objects.equals(pass, "")
                || Objects.equals(post, "") || Objects.equals(repPass, "")){
                labelInf.setText("Все поля должны быть заполнены!");
            } else {
                try {
                    writerObj.writeObject(CHECK_USER);
                    writerObj.writeObject(login);
                    Boolean unique = (Boolean) readerObj.readObject();

                    if(unique){
                        if(Objects.equals(pass, repPass)){

                            if(Objects.equals(isAdmin, IS_ADMIN)) {
                                role = 1;
                                newWindow = "adminMenu";
                            }
                            User user = new User(login, pass, role, FIO, post);

                            writerObj.writeObject(ADD_USER);
                            writerObj.writeObject(user);

                            String res = (String) readerObj.readObject();
                            System.out.println(res);

                            Stage stage1 = (Stage) btnCreate.getScene().getWindow();
                            stage1.close();

                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/" + newWindow + ".fxml"));
                            Parent root = loader.load();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(root));

                            if(role == 1) {
                                AdminMenuController controller = loader.getController();
                                controller.getConnectAndUser(clientSocket, writerObj, readerObj, stage1,
                                        user.getFIO(), user.getLogin());
                            } else {
                                UserMenuController controller = loader.getController();
                                controller.getConnectAndUser(clientSocket, writerObj, readerObj, stage1,
                                        user.getFIO(), user.getLogin());
                            }

                            stage.show();
                        } else{
                            System.out.println("Пароли не совпадают!");
                        }
                    } else labelInf.setText("Такой логин уже существует!");
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getConnect(Socket clientSocket, ObjectOutputStream writerObj, ObjectInputStream readerObj, Stage stage){
        this.clientSocket = clientSocket;
        this.writerObj = writerObj;
        this.readerObj = readerObj;
        this.stage = stage;
    }
}
