package com.company.education;

import com.company.common.AddUpdNote;
import com.company.entities.MasterClass;
import com.company.entities.SituationsFromPractise;
import com.company.entities.Training;
import com.company.entities.Universal;
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

import java.io.*;
import java.net.Socket;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class EducationNotesController {
    private Socket clientSocket = null;
    private ObjectOutputStream writerObj = null;
    private ObjectInputStream readerObj = null;
    private String userLogin = null;
    private int role;
    private int IDForDel;
    private Stage stage = null;
    private String curTable;

    private static final String SHOW_QUERY = "show";
    private static final String DELETE_QUERY = "delete";
    private static final String SEARCH_QUERY = "search";

    @FXML
    private TableColumn<Training, Integer> IDColumn;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnChange;

    @FXML
    private Label labelMain;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSignUp;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnRefresh;

    @FXML
    private TableView<Object> tableAllTrainings;

    @FXML
    private TableColumn<Training, String> curQualificColumn;

    @FXML
    private TableColumn<Training, String> dateColumn;

    @FXML
    private TableColumn<Training, String> directionColumn;

    @FXML
    private TableColumn<Training, Integer> freePlacesColumn;

    @FXML
    private TableColumn<Training, String> organizerColumn;

    @FXML
    private Label labelFIO;

    @FXML
    private Label labelLogin;

    @FXML
    private Label labelRole;

    @FXML
    private Label labelInf;

    @FXML
    private TextField searchCurQualific;

    @FXML
    private TextField searchDirection;

    @FXML
    private TextField searchFreePlaces;

    @FXML
    private TextField searchNumber;

    @FXML
    private TextField searchOrganizer;

    @FXML
    private DatePicker searchDate;

    @FXML
    void initialize(){
        btnBack.setOnAction(actionEvent -> {
            setWindow();
        });

        btnAdd.setOnAction(actionEvent -> {
            setWindowForUpdOrAdd("add", 0);
        });

        btnChange.setOnAction(actionEvent -> {
            if(Objects.equals(curTable, "training")){
                Training selectedItem = (Training) tableAllTrainings.getSelectionModel().getSelectedItem();

                if(selectedItem != null) {
                    setWindowForUpdOrAdd("upd", selectedItem.getId());
                } else labelInf.setText("Запись не выбрана!");
            }
            if(Objects.equals(curTable, "masterclass")){
                MasterClass selectedItem = (MasterClass) tableAllTrainings.getSelectionModel().getSelectedItem();

                if(selectedItem != null) {
                    setWindowForUpdOrAdd("upd", selectedItem.getId());
                } else labelInf.setText("Запись не выбрана!");
            }
            if(Objects.equals(curTable, "situation")){
                SituationsFromPractise selectedItem = (SituationsFromPractise) tableAllTrainings.getSelectionModel().getSelectedItem();

                if(selectedItem != null) {
                    setWindowForUpdOrAdd("upd", selectedItem.getId());
                } else labelInf.setText("Запись не выбрана!");
            }
        });

        btnSearch.setOnAction(actionEvent -> {
            try {
                if(Objects.equals(searchNumber.getText(), "") && Objects.equals(searchOrganizer.getText(), "") &&
                        Objects.equals(searchDirection.getText(), "") && searchDate.getValue() == null &&
                        Objects.equals(searchCurQualific.getText(), "") && Objects.equals(searchFreePlaces.getText(), "")) {
                    labelInf.setText("Хотя бы одно поле должно быть заполнено!");
                } else{
                    String textFromF1 = "=";
                    String textFromF2 = "=";
                    String textFromF3 = "=";
                    String textFromF4 = "=";
                    String textFromF5 = "=";
                    String textFromF6 = "=";

                    if(!Objects.equals(searchNumber.getText(), "")) textFromF1 = searchNumber.getText();
                    else textFromF1 = "0";
                    if(searchDate.getValue() != null){
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        textFromF2 = searchDate.getValue().format(formatter);
                    }
                    if(!Objects.equals(searchFreePlaces.getText(), "")){
                        textFromF3 = searchFreePlaces.getText();
                        Integer.parseInt(searchFreePlaces.getText());
                    }
                    if(!Objects.equals(searchDirection.getText(), "")) textFromF4 = searchDirection.getText();
                    if(!Objects.equals(searchOrganizer.getText(), "")) textFromF5 = searchOrganizer.getText();
                    if(!Objects.equals(searchCurQualific.getText(), "")) textFromF6 = searchCurQualific.getText();

                    Universal universal = new Universal(Integer.parseInt(textFromF1), textFromF2, textFromF3, textFromF4, textFromF5, textFromF6);

                    writerObj.writeObject(SEARCH_QUERY);
                    writerObj.writeObject(universal);
                    writerObj.writeObject(curTable);

                    ArrayList list = (ArrayList) readerObj.readObject();
                    if(list.size() != 0) tableAllTrainings.setItems(FXCollections.observableArrayList(list));
                    else labelInf.setText("Поиск не дал результатов.");

                    clearFields();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NumberFormatException e){
                labelInf.setText("Неверный тип данных!");
            }
        });

        btnSignUp.setOnAction(actionEvent -> {
            int id = 0;
            try {
                if(Objects.equals(curTable, "training")){
                    Training selectedItem = (Training) tableAllTrainings.getSelectionModel().getSelectedItem();

                    if(selectedItem != null) id = selectedItem.getId();
                }
                if(Objects.equals(curTable, "masterclass")){
                    MasterClass selectedItem = (MasterClass) tableAllTrainings.getSelectionModel().getSelectedItem();

                    if(selectedItem != null) id = selectedItem.getId();
                }
                if(Objects.equals(curTable, "situation")){
                    SituationsFromPractise selectedItem = (SituationsFromPractise) tableAllTrainings.getSelectionModel().getSelectedItem();

                    if(selectedItem != null) id = selectedItem.getId();
                }

                if(id != 0){
                    String curUser = this.userLogin;
                    writerObj.writeObject("add note to user");
                    writerObj.writeObject(curTable + ";" + id + ";" + curUser);
                    labelInf.setText("Вы записались на курс");
                } else labelInf.setText("Запись не выбрана!");
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        btnDelete.setOnAction(actionEvent -> {
            int id = 0;
            if(Objects.equals(curTable, "training")){
                Training selectedItem = (Training) tableAllTrainings.getSelectionModel().getSelectedItem();

                if(selectedItem != null) id = selectedItem.getId();
            }
            if(Objects.equals(curTable, "masterclass")){
                MasterClass selectedItem = (MasterClass) tableAllTrainings.getSelectionModel().getSelectedItem();

                if(selectedItem != null) id = selectedItem.getId();
            }
            if(Objects.equals(curTable, "situation")){
                SituationsFromPractise selectedItem = (SituationsFromPractise) tableAllTrainings.getSelectionModel().getSelectedItem();

                if(selectedItem != null) id = selectedItem.getId();
            }


            if(id != 0){
                try {
                    IDForDel = id;

                    writerObj.writeObject(DELETE_QUERY);
                    writerObj.writeObject(IDForDel + ";" + curTable);

                    tableAllTrainings.setItems(getEducationNotes());
                    labelInf.setText("Запись успешно удалена!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                labelInf.setText("Запись не выбрана!");
            }
        });

        btnRefresh.setOnAction(actionEvent -> {
            tableAllTrainings.setItems(getEducationNotes());
            labelInf.setText("Записи обновлены!");
        });
    }

    public ObservableList getEducationNotes(){
        ArrayList<Training> trainings = new ArrayList<>();
        try {
            writerObj.writeObject(SHOW_QUERY);
            writerObj.writeObject(curTable);

            trainings = (ArrayList) readerObj.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        ObservableList list = FXCollections.observableArrayList(trainings);
        return list;
    }

    void setWindow (){
        String windowName;
        if(this.role == 1) windowName = "adminMenu";
        else windowName = "userMenu";

        Stage stage1 = (Stage) btnBack.getScene().getWindow();
        stage1.close();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/" + windowName + ".fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            switch (windowName) {
                case "userMenu" -> {
                    UserMenuController controller = loader.getController();
                    controller.getConnectAndUser(clientSocket, writerObj, readerObj, stage1,
                            this.labelFIO.getText(), this.labelLogin.getText());
                }
                case "adminMenu" -> {
                    AdminMenuController controller = loader.getController();
                    controller.getConnectAndUser(clientSocket, writerObj, readerObj, stage1,
                            this.labelFIO.getText(), this.labelLogin.getText());
                }
            }

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setWindowForUpdOrAdd (String action, int id){
        try {
            String[] labels = {"Дата", "Св. места", "Направление", "Организатор", "Текущая квал."};
            String label = "Повышение квалификации";

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/addUpdNote.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            String actionFunc = "Изменить запись";
            if(Objects.equals(action, "add")) actionFunc = "Добавить запись";

            if(Objects.equals(curTable, "situation")){
                labels = new String[]{"Дата", "Св. места", "Направление", "Организатор", "Ситуация"};
                label = "Ситуация из практики";
            }
            if(Objects.equals(curTable, "masterclass")){
                labels = new String[]{"Дата", "Св. места", "Направление", "Организатор", "Мастер"};
                label = "Мастер-класс";
            }

            AddUpdNote controller = loader.getController();
            controller.getConnectAndSettings(clientSocket, writerObj, readerObj, actionFunc, label, labels, id, curTable);
            stage.show();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getConnectAndUser(Socket clientSocket, ObjectOutputStream writerObj, ObjectInputStream readerObj, Stage stage,
                                  String userFIO, String userLogin, int role, String curTable) throws IOException, ClassNotFoundException {
        this.clientSocket = clientSocket;
        this.writerObj = writerObj;
        this.readerObj = readerObj;
        this.stage = stage;
        this.role = role;
        this.userLogin = userLogin;
        this.curTable = curTable;

        if(role == 1) labelRole.setText("Текущий администратор: ");
        else {
            labelRole.setText("Текущий пользователь: ");
            btnAdd.setVisible(false);
            btnChange.setVisible(false);
            btnDelete.setVisible(false);
        }

        labelFIO.setText(userFIO);
        labelLogin.setText(userLogin);

        IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("fullDate"));
        freePlacesColumn.setCellValueFactory(new PropertyValueFactory<>("freePlaces"));
        directionColumn.setCellValueFactory(new PropertyValueFactory<>("direction"));
        organizerColumn.setCellValueFactory(new PropertyValueFactory<>("organizer"));

        if(Objects.equals(curTable, "training")){
            curQualificColumn.setText("Текущая квалификация");
            curQualificColumn.setCellValueFactory(new PropertyValueFactory<>("curQualific"));
        }
        if(Objects.equals(curTable, "masterclass")){
            labelMain.setText("Список всех мастер-классов для пользователей");
            curQualificColumn.setText("ФИО мастера");
            curQualificColumn.setCellValueFactory(new PropertyValueFactory<>("master"));
        }
        if(Objects.equals(curTable, "situation")){
            labelMain.setText("Список ситуаций из практики для разбора персоналом");
            curQualificColumn.setText("Ситуация из практики");
            curQualificColumn.setCellValueFactory(new PropertyValueFactory<>("situation"));
        }

        tableAllTrainings.setItems(getEducationNotes());
    }

    public void clearFields(){
        searchNumber.clear();
        searchDate.setValue(null);
        searchFreePlaces.clear();
        searchDirection.clear();
        searchOrganizer.clear();
        searchCurQualific.clear();
    }
}
