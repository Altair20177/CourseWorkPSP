package com.company.common;

import com.company.entities.Universal;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class AddUpdNote {
    private Socket clientSocket = null;
    private ObjectOutputStream writerObj = null;
    private ObjectInputStream readerObj = null;
    private Stage stage = null;
    private String action = null;
    private String curTable = null;
    private int id;

    private static final String ADD = "add";
    private static final String UPDATE_QUERY = "update";
    private static final String ADD_TOURISM = "add tourism";

    @FXML
    private Button btnAction;

    @FXML
    private DatePicker inputField1;

    @FXML
    private TextField inputField2;

    @FXML
    private TextField inputField3;

    @FXML
    private TextField inputField4;

    @FXML
    private TextField inputField5;

    @FXML
    private Label labelInf;

    @FXML
    private Label labelAction;

    @FXML
    void initialize(){
        btnAction.setOnAction(actionEvent -> {
            if(Objects.equals(this.action, ADD)) addNote();
            else updNote();
        });
    }

    public void addNote(){
        try {
            if(inputField1.getValue() == null || inputField2.getText() == null || inputField3.getText() == null
                    || inputField4.getText() == null || inputField5.getText() == null) {
                labelInf.setText("Поле(поля) пусты");
            } else {
                if(Objects.equals(curTable, "training") || Objects.equals(curTable, "masterclass")
                        || Objects.equals(curTable, "situation")){
                    Integer.parseInt(inputField2.getText());

                    writerObj.writeObject("add " + curTable);
                }

                if(Objects.equals(curTable, "tourism")){
                    Integer.parseInt(inputField2.getText());
                    Integer.parseInt(inputField3.getText());
                    Integer.parseInt(inputField4.getText());

                    writerObj.writeObject(ADD_TOURISM);
                }

                if(Objects.equals(curTable, "sport") || Objects.equals(curTable, "corporate")){
                    Integer.parseInt(inputField2.getText());
                    Integer.parseInt(inputField3.getText());

                    writerObj.writeObject("add " + curTable);
                }

                String request = inputField1.getValue() + "_" + inputField2.getText() + "_" + inputField3.getText()
                        + "_" + inputField4.getText() + "_" + inputField5.getText();

                writerObj.writeObject(request);

                Stage stage1 = (Stage) btnAction.getScene().getWindow();
                stage1.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e){
            labelInf.setText("Неверный тип данных!");
        }
    }

    public void updNote(){
        try {
            String textFromF1 = "=";
            String textFromF2 = "=";
            String textFromF3 = "=";
            String textFromF4 = "=";
            String textFromF5 = "=";

            if(inputField1.getValue() == null && Objects.equals(inputField2.getText(), "") && Objects.equals(inputField3.getText(), "")
                    && Objects.equals(inputField4.getText(), "") && Objects.equals(inputField5.getText(), "")) throw new Exception();

            if(inputField1.getValue() != null){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                textFromF1 = inputField1.getValue().format(formatter);
            }
            if(!Objects.equals(inputField2.getText(), "")) textFromF2 = inputField2.getText();
            if(!Objects.equals(inputField3.getText(), "")) textFromF3 = inputField3.getText();
            if(!Objects.equals(inputField4.getText(), "")) textFromF4 = inputField4.getText();
            if(!Objects.equals(inputField5.getText(), "")) textFromF5 = inputField5.getText();

            if((Objects.equals(curTable, "training") || Objects.equals(curTable, "masterclass")
                || Objects.equals(curTable, "situation")) && !Objects.equals(inputField2.getText(), "")) Integer.parseInt(inputField2.getText());

            if((Objects.equals(curTable, "tourism") || Objects.equals(curTable, "corporate") || Objects.equals(curTable, "sport"))
                    && !Objects.equals(inputField2.getText(), "")) Integer.parseInt(inputField2.getText());
            if((Objects.equals(curTable, "tourism")  || Objects.equals(curTable, "corporate") || Objects.equals(curTable, "sport"))
                    && !Objects.equals(inputField3.getText(), "")) Integer.parseInt(inputField3.getText());
            if(Objects.equals(curTable, "tourism") && !Objects.equals(inputField4.getText(), "")) Integer.parseInt(inputField4.getText());

            Universal universal = new Universal(id, textFromF1, textFromF2, textFromF3, textFromF4, textFromF5);

            writerObj.writeObject(UPDATE_QUERY);
            writerObj.writeObject(universal);
            writerObj.writeObject(this.curTable);

            Stage stage1 = (Stage) btnAction.getScene().getWindow();
            stage1.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e){
            labelInf.setText("Неверный тип данных!");
        } catch (Exception e) {
            labelInf.setText("Хотя бы одно поле должно быть заполено!");
        }
    }

    public void getConnectAndSettings(Socket clientSocket, ObjectOutputStream writerObj, ObjectInputStream readerObj, String action,
                                      String label, String[] placeholders, int id, String curTable) throws IOException, ClassNotFoundException {
        this.clientSocket = clientSocket;
        this.writerObj = writerObj;
        this.readerObj = readerObj;
        this.id = id;
        this.curTable = curTable;

        if(Objects.equals(action, "Добавить запись")) this.action = ADD;
        else this.action = UPDATE_QUERY;

        labelAction.setText(label);
        btnAction.setText(action);

        inputField1.setPromptText(placeholders[0]);
        inputField2.setPromptText(placeholders[1]);
        inputField3.setPromptText(placeholders[2]);
        inputField4.setPromptText(placeholders[3]);
        inputField5.setPromptText(placeholders[4]);

        inputField1.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
    }
}
