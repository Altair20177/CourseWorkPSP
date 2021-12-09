package com.company.infocommunication;

import com.company.entities.Messages;
import com.company.menu.AdminMenuController;
import com.company.menu.UserMenuController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class ChatController {
    private Socket clientSocket = null;
    private ObjectOutputStream writerObj = null;
    private ObjectInputStream readerObj = null;
    private int role = 0;
    private Stage stage = null;
    private String curTable = "tourism";
    private String curTableChat = "tourismmessages";

    private static final String SHOW_QUERY = "show";
    private static final String SEND_MESS = "send mess";

    @FXML
    private Button btnBack;

    @FXML
    private Button btnSendMess;

    @FXML
    private Button btnShowTourismNotes;

    @FXML
    private TextArea inputMess;

    @FXML
    private Label labelFIO;

    @FXML
    private Label labelInf;

    @FXML
    private Label labelLogin;

    @FXML
    private Label labelRole;

    @FXML
    private Label labelForBtn;

    @FXML
    private Label labelMain;

    @FXML
    private TableColumn<Messages, String> loginColumn;

    @FXML
    private TableColumn<Messages, String> messColumn;

    @FXML
    private TableView<Messages> tableAllMessagesTourism;

    @FXML
    void initialize(){
        btnBack.setOnAction(actionEvent -> {
            Stage stage1 = (Stage) btnBack.getScene().getWindow();
            stage1.close();
            try {
                FXMLLoader loader;

                if(role == 1) loader = new FXMLLoader(getClass().getResource("../resources/adminMenu.fxml"));
                else loader = new FXMLLoader(getClass().getResource("../resources/userMenu.fxml"));

                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));

                if(role == 1){
                    AdminMenuController controller = loader.getController();
                    controller.getConnectAndUser(clientSocket, writerObj, readerObj, stage1,
                            this.labelFIO.getText(), this.labelLogin.getText());
                } else {
                    UserMenuController controller = loader.getController();
                    controller.getConnectAndUser(clientSocket, writerObj, readerObj, stage1,
                            this.labelFIO.getText(), this.labelLogin.getText());
                }

                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnSendMess.setOnAction(actionEvent -> {
            try {
                if(!Objects.equals(inputMess.getText(), "")){
                    writerObj.writeObject(SEND_MESS);
                    writerObj.writeObject(labelLogin.getText() + "=" + inputMess.getText() + "=" + curTableChat);

                    tableAllMessagesTourism.setItems(getMessages());
                    labelInf.setText("Сообщение отправлено!");

                    inputMess.clear();
                } else labelInf.setText("Сообщение не может быть пустым!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnShowTourismNotes.setOnAction(actionEvent -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/infocomNotes.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));

                InfocomNotesController controller = loader.getController();
                controller.getConnectAndUser(clientSocket, writerObj, readerObj, stage, this.role, curTable);

                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public ObservableList<Messages> getMessages(){
        ArrayList<Messages> messages = new ArrayList<>();
        try {
            writerObj.writeObject(SHOW_QUERY);
            writerObj.writeObject(curTableChat);

            messages = (ArrayList<Messages>) readerObj.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        ObservableList<Messages> list = FXCollections.observableArrayList(messages);
        return list;
    }

    public void getConnectAndUser(Socket clientSocket, ObjectOutputStream writerObj, ObjectInputStream readerObj,
                                  Stage stage, String userFIO, String userLogin, int role, String tableChat){
        this.clientSocket = clientSocket;
        this.writerObj = writerObj;
        this.readerObj = readerObj;
        this.stage = stage;
        this.role = role;
        this.curTableChat = tableChat;

        if(role == 1) labelRole.setText("Текущий администратор: ");
        else labelRole.setText("Текущий пользователь: ");
        labelFIO.setText(userFIO);
        labelLogin.setText(userLogin);
        labelInf.setText("Поле для вывода информации");

        if(Objects.equals(curTableChat, "tourismmessages")) {
            curTableChat = "tourismmessages";
            labelForBtn.setText("Нажмите чтобы просмотреть туристические мероприятия");
            labelMain.setText("Чат для обсуждения туристических мероприятий");
            curTable = "tourism";
        }
        if(Objects.equals(curTableChat, "sportmessages")) {
            curTableChat = "sportmessages";
            labelForBtn.setText("Нажмите чтобы просмотреть спортивные мероприятия");
            labelMain.setText("Чат для обсуждения спортивных мероприятий");
            curTable = "sport";
        }
        if(Objects.equals(curTableChat, "corporatemessages")) {
            curTableChat = "corporatemessages";
            labelForBtn.setText("Нажмите чтобы просмотреть корпоративы");
            labelMain.setText("Чат для обсуждения корпоративов");
            curTable = "corporate";
        }

        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        messColumn.setCellValueFactory(new PropertyValueFactory<>("message"));

        tableAllMessagesTourism.setItems(getMessages());
    }
}
