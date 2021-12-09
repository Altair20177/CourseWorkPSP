package com.company;

import com.company.entities.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.company.Handler.findUser;

public class Server extends Thread {
    private Handler handler = new Handler();
    private Socket clientSocket = null;
    public Server(Socket socket){
        this.clientSocket = socket;
    }

    private static final String SHOW_QUERY = "show";

    private static final String ADD_USER = "add user";
    private static final String ADD_TOURISM = "add tourism";
    private static final String ADD_CORPORATE = "add corporate";
    private static final String ADD_SPORT = "add sport";
    private static final String ADD_USER_NOTE = "add note to user";
    private static final String ADD_MASTER_CLASS = "add masterclass";
    private static final String ADD_SITUATION = "add situation";
    private static final String ADD_TRAINING = "add training";

    private static final String DELETE_QUERY = "delete";
    private static final String UPDATE_QUERY = "update";
    private static final String LOG_IN = "logIn";
    private static final String SEARCH_QUERY = "search";
    private static final String SEARCH_USER = "search user";
    private static final String MAKE_ADMIN = "make admin";
    private static final String SEND_MESS = "send mess";
    private static final String CHECK_USER = "check user";
    private static final String EDIT_USER = "edit user";


    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = new ServerSocket(8000);

        while(true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Клиент подключился!");
            Server server = new Server(clientSocket);
            server.start();
        }
    }

    public void run() {
        try{
            while (true){
                ObjectOutputStream writerObj = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream readerObj = new ObjectInputStream(clientSocket.getInputStream());

                while (!clientSocket.isClosed()){
                    String action = (String) readerObj.readObject();
                    String request = "";
                    String[] arr;
                    switch (action){
                        case ADD_USER -> {
                            User requestObj = (User) readerObj.readObject();
                            handler.insertUser(requestObj);
                            writerObj.writeObject("User added successfully!");
                        }

                        case ADD_TRAINING -> {
                            request = (String) readerObj.readObject();
                            arr = request.split("_");
                            Training training = new Training(arr[0], Integer.parseInt(arr[1]), arr[2], arr[3], arr[4]);
                            handler.insertTraining(training);
                        }

                        case ADD_TOURISM -> {
                            request = (String) readerObj.readObject();
                            arr = request.split("_");
                            Tourism tourism = new Tourism(arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2]), Integer.parseInt(arr[3]), arr[4]);
                            handler.insertTourism(tourism);
                        }

                        case ADD_CORPORATE -> {
                            request = (String) readerObj.readObject();
                            arr = request.split("_");
                            Corporate corporate = new Corporate(arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2]), arr[3], arr[4]);
                            handler.insertCorporate(corporate);
                        }

                        case ADD_SPORT -> {
                            request = (String) readerObj.readObject();
                            arr = request.split("_");
                            Sport sport = new Sport(arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2]), arr[3], arr[4]);
                            handler.insertSport(sport);
                        }

                        case ADD_MASTER_CLASS -> {
                            request = (String) readerObj.readObject();
                            arr = request.split("_");
                            MasterClass masterClass = new MasterClass(arr[0], Integer.parseInt(arr[1]), arr[2], arr[3], arr[4]);
                            handler.insertMasterClass(masterClass);
                        }

                        case ADD_SITUATION -> {
                            request = (String) readerObj.readObject();
                            arr = request.split("_");
                            SituationsFromPractise situation = new SituationsFromPractise(arr[0], Integer.parseInt(arr[1]), arr[2], arr[3], arr[4]);
                            handler.insertSituation(situation);
                        }

                        case DELETE_QUERY -> {
                            request = (String) readerObj.readObject();
                            arr = request.split(";");
                            handler.delete(Integer.parseInt(arr[0]), arr[1]);
                        }

                        case UPDATE_QUERY -> {
                            Universal universal = (Universal) readerObj.readObject();
                            request = (String) readerObj.readObject();
                            handler.update(universal, request);
                        }

                        case SHOW_QUERY -> {
                            request = (String) readerObj.readObject();
                            ArrayList list = handler.show(request);
                            writerObj.writeObject(list);
                        }

                        case SEARCH_QUERY -> {
                            Universal universal = (Universal) readerObj.readObject();
                            request = (String) readerObj.readObject();
                            writerObj.writeObject(handler.search(universal, request));
                        }

                        case SEARCH_USER -> {
                            User user = (User) readerObj.readObject();
                            writerObj.writeObject(handler.searchUser(user));
                        }

                        case LOG_IN -> {
                            request = (String) readerObj.readObject();
                            String res = findUser(request);
                            writerObj.writeObject(res);
                        }

                        case MAKE_ADMIN -> {
                            request = (String) readerObj.readObject();
                            handler.makeAdmin(Integer.parseInt(request));
                        }

                        case ADD_USER_NOTE -> {
                            request = (String) readerObj.readObject();
                            String[] tableAndID = request.split(";");
                            handler.addNoteToUser(tableAndID[0], tableAndID[1], tableAndID[2]);
                        }

                        case SEND_MESS -> {
                            request = (String) readerObj.readObject();
                            String[] loginMessAndTable = request.split("=");
                            handler.sendMess(loginMessAndTable[0], loginMessAndTable[1], loginMessAndTable[2]);
                        }

                        case CHECK_USER -> {
                            request = (String) readerObj.readObject();
                            writerObj.writeObject(handler.checkUser(request));
                        }

                        case EDIT_USER -> {
                            User user = (User) readerObj.readObject();
                            handler.updateUser(user);
                        }
                    }
                }

                readerObj.close();
                writerObj.close();
                clientSocket.close();
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
                e.printStackTrace();
        }
    }
}
