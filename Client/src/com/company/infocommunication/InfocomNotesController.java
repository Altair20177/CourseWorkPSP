package com.company.infocommunication;

import com.company.common.AddUpdNote;
import com.company.entities.*;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class InfocomNotesController {
    private Socket clientSocket = null;
    private ObjectOutputStream writerObj = null;
    private ObjectInputStream readerObj = null;
    private int role = 0;
    private Stage stage = null;
    private String curTable = "tourism";

    private static final String SHOW_QUERY = "show";
    private static final String DELETE_QUERY = "delete";
    private static final String UPDATE_QUERY = "update";
    private static final String SEARCH_QUERY = "search";

    @FXML
    private TableColumn<Tourism, Integer> IDColumn;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnChange;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnSearch;

    @FXML
    private Label labelInf;

    @FXML
    private DatePicker searchDate;

    @FXML
    private TextField searchFourth;

    @FXML
    private TextField searchFifth;

    @FXML
    private TextField searchFreePlaces;

    @FXML
    private TextField searchNumber;

    @FXML
    private TextField searchSixth;

    @FXML
    private TableColumn<Tourism, Date> dateColumn;

    @FXML
    private TableColumn<Tourism, String> priceColumn;

    @FXML
    private TableColumn<Tourism, String> fifthColumn;

    @FXML
    private TableColumn<Tourism, Integer> freePlacesColumn;

    @FXML
    private TableColumn<Tourism, String> sixthColumn;

    @FXML
    private TableView<Object> tableAllTourism;

    @FXML
    void initialize(){
        btnRefresh.setOnAction(actionEvent -> {
            tableAllTourism.setItems(getNotes());
            labelInf.setText("Записи обновлены!");
        });

        btnChange.setOnAction(actionEvent -> {
            if(Objects.equals(curTable, "tourism")){
                Tourism selectedItem = (Tourism) tableAllTourism.getSelectionModel().getSelectedItem();

                if(selectedItem != null) {
                    setWindowForUpdOrAdd("upd", selectedItem.getId());
                } else labelInf.setText("Запись не выбрана!");
            }
            if(Objects.equals(curTable, "sport")){
                Sport selectedItem = (Sport) tableAllTourism.getSelectionModel().getSelectedItem();

                if(selectedItem != null) {
                    setWindowForUpdOrAdd("upd", selectedItem.getId());
                } else labelInf.setText("Запись не выбрана!");
            }
            if(Objects.equals(curTable, "corporate")){
                Corporate selectedItem = (Corporate) tableAllTourism.getSelectionModel().getSelectedItem();

                if(selectedItem != null) {
                    setWindowForUpdOrAdd("upd", selectedItem.getId());
                } else labelInf.setText("Запись не выбрана!");
            }
        });

        btnAdd.setOnAction(actionEvent -> {
            setWindowForUpdOrAdd("add", 0);
        });

        btnDelete.setOnAction(actionEvent -> {
            int id = 0;
            if(Objects.equals(curTable, "tourism")){
                Tourism selectedItem = (Tourism) tableAllTourism.getSelectionModel().getSelectedItem();

                if(selectedItem != null) id = selectedItem.getId();
            }
            if(Objects.equals(curTable, "sport")){
                Sport selectedItem = (Sport) tableAllTourism.getSelectionModel().getSelectedItem();

                if(selectedItem != null) id = selectedItem.getId();
            }
            if(Objects.equals(curTable, "corporate")){
                Corporate selectedItem = (Corporate) tableAllTourism.getSelectionModel().getSelectedItem();

                if(selectedItem != null) id = selectedItem.getId();
            }


            if(id != 0){
                try {
                    writerObj.writeObject(DELETE_QUERY);
                    writerObj.writeObject(id + ";" + curTable);

                    tableAllTourism.setItems(getNotes());
                    labelInf.setText("Запись успешно удалена!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                labelInf.setText("Запись не выбрана!");
            }
        });

        btnSearch.setOnAction(actionEvent -> {
            try {
                if(Objects.equals(searchNumber.getText(), "") && Objects.equals(searchFourth.getText(), "") &&
                        Objects.equals(searchFifth.getText(), "") && searchDate.getValue() == null &&
                        Objects.equals(searchSixth.getText(), "") && Objects.equals(searchFreePlaces.getText(), "")) {
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
                    if(!Objects.equals(searchFourth.getText(), "")){
                        textFromF4 = searchFourth.getText();
                        Integer.parseInt(searchFourth.getText());
                    }
                    if(!Objects.equals(searchFifth.getText(), "")){
                        textFromF5 = searchFifth.getText();
                        Integer.parseInt(searchFifth.getText());
                    }
                    if(!Objects.equals(searchSixth.getText(), "")) textFromF6 = searchSixth.getText();

                    Universal universal = new Universal(Integer.parseInt(textFromF1), textFromF2, textFromF3, textFromF4, textFromF5, textFromF6);

                    writerObj.writeObject(SEARCH_QUERY);
                    writerObj.writeObject(universal);
                    writerObj.writeObject(curTable);

                    ArrayList list = (ArrayList) readerObj.readObject();
                    if(list.size() != 0) tableAllTourism.setItems(FXCollections.observableArrayList(list));
                    else labelInf.setText("Поиск не дал результатов.");

                    clearFields();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NumberFormatException e){
                labelInf.setText("Неверный тип данных!");
            }
        });
    }

    void setWindowForUpdOrAdd (String action, int id){
        try {
            String[] labels = {"Дата", "Св. места", "Цена", "Продолж.", "Достоприм."};
            String label = "Туристические мероприятия";

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/addUpdNote.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            String actionFunc = "Изменить запись";
            if(Objects.equals(action, "add")) actionFunc = "Добавить запись";

            if(Objects.equals(curTable, "sport")){
                labels = new String[]{"Дата", "Св. места", "Цена", "Спорт", "Наз. ком."};
                label = "Спортивные мероприятия";
            }
            if(Objects.equals(curTable, "corporate")){
                labels = new String[]{"Дата", "Св. места", "Цена", "Ресторан", "Праздник"};
                label = "Корпоративы";
            }

            AddUpdNote controller = loader.getController();
            controller.getConnectAndSettings(clientSocket, writerObj, readerObj, actionFunc, label, labels, id, curTable);
            stage.show();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ObservableList getNotes(){
        ArrayList tourism = new ArrayList<>();
        try {
            writerObj.writeObject(SHOW_QUERY);
            writerObj.writeObject(curTable);

            tourism = (ArrayList) readerObj.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        ObservableList list = FXCollections.observableArrayList(tourism);
        return list;
    }

    public void getConnectAndUser(Socket clientSocket, ObjectOutputStream writerObj, ObjectInputStream readerObj,
                                  Stage stage, int role, String curTable){
        this.clientSocket = clientSocket;
        this.writerObj = writerObj;
        this.readerObj = readerObj;
        this.stage = stage;
        this.role = role;
        this.curTable = curTable;

        labelInf.setText("Поле для вывода информации");

        IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("fullDate"));
        freePlacesColumn.setCellValueFactory(new PropertyValueFactory<>("freePlaces"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        if(role == 0){
            btnAdd.setVisible(false);
            btnChange.setVisible(false);
            btnDelete.setVisible(false);
        }

        if(Objects.equals(curTable, "tourism")){
            fifthColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
            sixthColumn.setCellValueFactory(new PropertyValueFactory<>("attraction"));

            fifthColumn.setText("Продолжительность");
            sixthColumn.setText("Достопримечательность");

            searchFourth.setPromptText("Цена");
            searchFifth.setPromptText("Продолж.");
            searchSixth.setPromptText("Достоприм.");
        }

        if(Objects.equals(curTable, "sport")){
            fifthColumn.setCellValueFactory(new PropertyValueFactory<>("kindOfSport"));
            sixthColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            fifthColumn.setText("Вид спорта");
            sixthColumn.setText("Название команда/игрока");

            searchFourth.setPromptText("Цена");
            searchFifth.setPromptText("Спорт");
            searchSixth.setPromptText("Название");
        }

        if(Objects.equals(curTable, "corporate")){
            fifthColumn.setCellValueFactory(new PropertyValueFactory<>("restaurant"));
            sixthColumn.setCellValueFactory(new PropertyValueFactory<>("party"));

            fifthColumn.setText("Название ресторана");
            sixthColumn.setText("Праздник");

            searchFourth.setPromptText("Цена");
            searchFifth.setPromptText("Ресторан");
            searchSixth.setPromptText("Праздник");
        }

        tableAllTourism.setItems(getNotes());
    }

    public void clearFields(){
        searchNumber.clear();
        searchDate.setValue(null);
        searchFreePlaces.clear();
        searchFourth.clear();
        searchFifth.clear();
        searchSixth.clear();
    }
}
