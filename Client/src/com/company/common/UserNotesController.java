package com.company.common;

import com.company.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class UserNotesController{
    private Socket clientSocket = null;
    private ObjectOutputStream writerObj = null;
    private ObjectInputStream readerObj = null;
    private Stage stage = null;
    private int id = 0, role = 0;

    private static final String SEARCH_USER = "search user";
    private static final String EDIT_USER = "edit user";

    @FXML
    private TextField FIO;

    @FXML
    private Button btnChange;

    @FXML
    private TextField login;

    @FXML
    private TextField notesMasterClass;

    @FXML
    private TextField notesSituation;

    @FXML
    private TextField notesTraining;

    @FXML
    private TextField pass;

    @FXML
    private TextField post;

    @FXML
    private Label labelInf;

    @FXML
    private PieChart pieChart;

    @FXML
    void initialize(){
        btnChange.setOnAction(actionEvent -> {
            actionChange();
        });
    }

    public void getConnect(Socket clientSocket, ObjectOutputStream writerObj, ObjectInputStream readerObj,
                           Stage stage, String login) throws IOException, ClassNotFoundException {
        this.clientSocket = clientSocket;
        this.writerObj = writerObj;
        this.readerObj = readerObj;
        this.stage = stage;

        User user = new User(0, login, -1, "=", "=");

        writerObj.writeObject(SEARCH_USER);
        writerObj.writeObject(user);

        ArrayList<User> oneUser = (ArrayList<User>) readerObj.readObject();

        for (User element : oneUser) {
            this.id = element.getId();
            this.role = element.getRole();
            this.login.setText(element.getLogin());
            this.pass.setText(element.getPassword());
            this.FIO.setText(element.getFIO());
            this.post.setText(element.getPost());
            this.notesTraining.setText(element.getTraining());
            this.notesMasterClass.setText(element.getMasterClass());
            this.notesSituation.setText(element.getSituations());
        }

        setPieChart();
    }

    public void setPieChart(){
        String[] arr1, arr2, arr3;
        int size1, size2, size3;

        if(!Objects.equals(notesTraining.getText(), null)){
            arr1 = notesTraining.getText().split(", ");
            size1 = arr1.length;
        } else size1 = 0;

        if(!Objects.equals(notesMasterClass.getText(), null)){
            arr2 = notesMasterClass.getText().split(", ");
            size2 = arr2.length;
        } else size2 = 0;

        if(!Objects.equals(notesSituation.getText(), null)){
            arr3 = notesSituation.getText().split(", ");
            size3 = arr3.length;
        } else size3 = 0;

        if(size1 == 0 && size2 == 0 && size3 == 0) labelInf.setText("Пользователь не записан на мероприятия!");

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                        new PieChart.Data("Повыш. квал.", size1),
                        new PieChart.Data("Мастер-классы", size2),
                        new PieChart.Data("Сит. из практ.", size3)
        );

        pieChart.getData().addAll(pieChartData);
    }

    public void actionChange(){
        String[] arrStr;
        String notes = "";
        try {
            if(Objects.equals(pass.getText(), "") || Objects.equals(FIO.getText(), "") || Objects.equals(post.getText(), "")){
                labelInf.setText("Поля должны быть заполнены!");
            } else {
                for(int j = 0; j < 3; j++){
                    if(j == 0 && !Objects.equals(notesTraining.getText(), "")) notes = notesTraining.getText();
                    if(j == 1 && !Objects.equals(notesMasterClass.getText(), "")) notes = notesMasterClass.getText();
                    if(j == 2 && !Objects.equals(notesSituation.getText(), "")) notes = notesSituation.getText();

                    if(notes != null){
                        arrStr = notes.split(", ");
                        int[] arrInt = new int[arrStr.length];

                        for(int i = 0; i < arrStr.length; i++){
                            arrInt[i] = Integer.parseInt(arrStr[i]);
                        }
                    }
                }

                User user = new User(this.id, login.getText(), pass.getText(), role, FIO.getText(), post.getText(),
                        notesTraining.getText(), notesMasterClass.getText(), notesSituation.getText());

                writerObj.writeObject(EDIT_USER);
                writerObj.writeObject(user);

                Stage stage1 = (Stage) btnChange.getScene().getWindow();
                stage1.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e){
            labelInf.setText("Неверный тип данных!");
        }
    }
}
