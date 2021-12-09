package com.company.menu;

import com.company.authorizRegistr.AuthorizController;
import com.company.common.UserNotesController;
import com.company.education.EducationNotesController;
import com.company.infocommunication.ChatController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class UserMenuController {
    private Socket clientSocket = null;
    private ObjectOutputStream writerObj = null;
    private ObjectInputStream readerObj = null;
    private int role = 0;
    private Stage stage = null;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnCorporatives;

    @FXML
    private Button btnMasterClasses;

    @FXML
    private Button btnSituations;

    @FXML
    private Button btnSport;

    @FXML
    private Button btnOwnNotes;

    @FXML
    private Button btnTourism;

    @FXML
    private Button btnTraining;

    @FXML
    private Label labelFIO;

    @FXML
    private Label labelLogin;

    @FXML
    void initialize(){
        btnBack.setOnAction(actionEvent -> {
            setWindow("../resources/authoriz.fxml", "");
        });

        btnOwnNotes.setOnAction(actionEvent -> {
            setWindowOwnNotes();
        });

        btnTraining.setOnAction(actionEvent -> {
            setWindow("../resources/training.fxml", "training");
        });

        btnSituations.setOnAction(actionEvent -> {
            setWindow("../resources/training.fxml", "situation");
        });

        btnMasterClasses.setOnAction(actionEvent -> {
            setWindow("../resources/training.fxml", "masterclass");
        });

        btnTourism.setOnAction(actionEvent -> {
            setWindow("../resources/tourism.fxml", "tourismmessages");
        });

        btnSport.setOnAction(actionEvent -> {
            setWindow("../resources/tourism.fxml", "sportmessages");
        });

        btnCorporatives.setOnAction(actionEvent -> {
            setWindow("../resources/tourism.fxml", "corporatemessages");
        });
    }

    void setWindow (String windowName, String table){
        Stage stage1 = (Stage) btnBack.getScene().getWindow();
        stage1.close();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(windowName));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            switch (windowName) {
                case "../resources/authoriz.fxml" -> {
                    AuthorizController controller = loader.getController();
                    controller.getConnect(clientSocket, writerObj, readerObj, stage1);
                }
                case "../resources/training.fxml" -> {
                    EducationNotesController controller = loader.getController();
                    controller.getConnectAndUser(clientSocket, writerObj, readerObj, stage1,
                            this.labelFIO.getText(), this.labelLogin.getText(), role, table);
                }
                case "../resources/tourism.fxml" -> {
                    ChatController controller = loader.getController();
                    controller.getConnectAndUser(clientSocket, writerObj, readerObj, stage1,
                            this.labelFIO.getText(), this.labelLogin.getText(), role, table);
                }
            }

            stage.show();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setWindowOwnNotes(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/userNotes.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            UserNotesController controller = loader.getController();
            controller.getConnect(clientSocket, writerObj, readerObj, stage, labelLogin.getText());

            stage.show();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getConnectAndUser(Socket clientSocket, ObjectOutputStream writerObj, ObjectInputStream readerObj,
                                  Stage stage, String userFIO, String userLogin){
        this.clientSocket = clientSocket;
        this.writerObj = writerObj;
        this.readerObj = readerObj;
        this.stage = stage;

        labelFIO.setText(userFIO);
        labelLogin.setText(userLogin);
    }
}
