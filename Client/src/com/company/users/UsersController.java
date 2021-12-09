package com.company.users;

import com.company.entities.User;
import com.company.menu.AdminMenuController;
import com.company.common.UserNotesController;
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

public class UsersController {
    private Socket clientSocket = null;
    private ObjectOutputStream writerObj = null;
    private ObjectInputStream readerObj = null;
    private Stage stage = null;

    private static final String SHOW_QUERY = "show";
    private static final String DELETE_QUERY = "delete";
    private static final String SEARCH_USER = "search user";
    private static final String MAKE_ADMIN = "make admin";

    @FXML
    private Button btnMakeAdmin;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnOwnNotes;

    @FXML
    private Label labelFIO;

    @FXML
    private Label labelLogin;

    @FXML
    private Label labelRole;

    @FXML
    private Label labelInf;

    @FXML
    private TableColumn<User, String> loginColumn;

    @FXML
    private TableColumn<User, String> masterClassColumn;

    @FXML
    private TableColumn<User, String> passColumn;

    @FXML
    private TableColumn<User, String> postColumn;

    @FXML
    private TableColumn<User, Integer> roleColumn;

    @FXML
    private TableColumn<User, String> situationsColumn;

    @FXML
    private TableColumn<User, String> trainingColumn;

    @FXML
    private TableColumn<User, String> FIOColumn;

    @FXML
    private TableColumn<User, Integer> IDColumn;

    @FXML
    private TableView<User> tableAllUsers;

    @FXML
    private TextField searchFIO;

    @FXML
    private TextField searchLogin;

    @FXML
    private TextField searchNumber;

    @FXML
    private TextField searchPost;

    @FXML
    private TextField searchRole;

    @FXML
    void initialize(){
        btnOwnNotes.setOnAction(actionEvent -> {
            setWindowOwnNotes();
        });

        btnBack.setOnAction(actionEvent -> {
            Stage stage1 = (Stage) btnBack.getScene().getWindow();
            stage1.close();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/adminMenu.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));

                AdminMenuController controller = loader.getController();
                controller.getConnectAndUser(clientSocket, writerObj, readerObj, stage1,
                        this.labelFIO.getText(), this.labelLogin.getText());

                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnDelete.setOnAction(actionEvent -> {
            try {
                User selectedUser = tableAllUsers.getSelectionModel().getSelectedItem();

                if(selectedUser != null){
                    if(!Objects.equals(selectedUser.getLogin(), labelLogin.getText())){
                        writerObj.writeObject(DELETE_QUERY);
                        writerObj.writeObject(selectedUser.getId() + ";" + "users");

                        tableAllUsers.setItems(getUsers());
                        labelInf.setText("Запись была удалена!");
                    } else labelInf.setText("Вы не можете удалить себя!");
                } else labelInf.setText("Запись не выбрана!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnRefresh.setOnAction(actionEvent -> {
            tableAllUsers.setItems(getUsers());
            labelInf.setText("Записи обновлены!");
        });

        btnMakeAdmin.setOnAction(actionEvent -> {
            try {
                User selectedUser = tableAllUsers.getSelectionModel().getSelectedItem();

                if(selectedUser != null){
                    writerObj.writeObject(MAKE_ADMIN);
                    writerObj.writeObject(Integer.toString(selectedUser.getId()));

                    tableAllUsers.setItems(getUsers());
                    labelInf.setText("Пользователь " + selectedUser.getLogin() + " теперь администратор");
                } else labelInf.setText("Запись не выбрана!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnSearch.setOnAction(actionEvent -> {
            if(Objects.equals(searchNumber.getText(), "") && Objects.equals(searchLogin.getText(), "") &&
                    Objects.equals(searchRole.getText(), "") && Objects.equals(searchFIO.getText(), "") &&
                    Objects.equals(searchPost.getText(), "")) {
                labelInf.setText("Хотя бы одно поле должно быть заполнено!");
            } else {
                try {
                    String textFromF1 = "0";
                    String textFromF2 = "=";
                    String textFromF3 = "-1";
                    String textFromF4 = "=";
                    String textFromF5 = "=";

                    if (!Objects.equals(searchNumber.getText(), "")) textFromF1 = searchNumber.getText();
                    if (!Objects.equals(searchLogin.getText(), "")) textFromF2 = searchLogin.getText();
                    if (!Objects.equals(searchRole.getText(), "")) textFromF3 = searchRole.getText();
                    if (!Objects.equals(searchFIO.getText(), "")) textFromF4 = searchFIO.getText();
                    if (!Objects.equals(searchPost.getText(), "")) textFromF5 = searchPost.getText();

                    if(Objects.equals(searchRole.getText(), "1") || Objects.equals(searchRole.getText(), "0")
                            || Objects.equals(searchRole.getText(), "")) {
                        User user = new User(Integer.parseInt(textFromF1), textFromF2, Integer.parseInt(textFromF3), textFromF4, textFromF5);

                        writerObj.writeObject(SEARCH_USER);
                        writerObj.writeObject(user);

                        ArrayList<User> list = (ArrayList<User>) readerObj.readObject();
                        if (list.size() != 0) tableAllUsers.setItems(FXCollections.observableArrayList(list));
                        else labelInf.setText("Поиск не дал результатов.");
                    } else labelInf.setText("Роль должа иметь значение 1 или 0!");
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NumberFormatException e){
                    labelInf.setText("Неверный тип данных!");
                }
            }
            clearFields();
        });
    }

    public ObservableList<User> getUsers(){
        ArrayList<User> users = new ArrayList<>();
        try {
            writerObj.writeObject(SHOW_QUERY);
            writerObj.writeObject("users");

            users = (ArrayList<User>) readerObj.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        ObservableList<User> list = FXCollections.observableArrayList(users);
        return list;
    }

    public void getConnectAndUser(Socket clientSocket, ObjectOutputStream writerObj, ObjectInputStream readerObj,
                                  Stage stage, String userFIO, String userLogin){
        this.clientSocket = clientSocket;
        this.writerObj = writerObj;
        this.readerObj = readerObj;
        this.stage = stage;

        labelRole.setText("Текущий администратор: ");
        labelFIO.setText(userFIO);
        labelLogin.setText(userLogin);
        labelInf.setText("Поле для вывода информации");

        IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        passColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        FIOColumn.setCellValueFactory(new PropertyValueFactory<>("FIO"));
        postColumn.setCellValueFactory(new PropertyValueFactory<>("post"));
        trainingColumn.setCellValueFactory(new PropertyValueFactory<>("training"));
        masterClassColumn.setCellValueFactory(new PropertyValueFactory<>("masterClass"));
        situationsColumn.setCellValueFactory(new PropertyValueFactory<>("situations"));

        tableAllUsers.setItems(getUsers());
    }

    public void clearFields(){
        searchNumber.clear();
        searchFIO.clear();
        searchLogin.clear();
        searchPost.clear();
        searchRole.clear();
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
}
