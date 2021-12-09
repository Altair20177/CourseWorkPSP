package com.company;

import com.company.entities.*;
import com.company.entities.Messages;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

import static com.company.DBConnector.getDbConnection;

public class Handler {
    public ArrayList show(String table) throws SQLException, IOException, ClassNotFoundException {
        ArrayList<Training> trainings = new ArrayList<Training>();
        ArrayList<MasterClass> masterClass = new ArrayList<MasterClass>();
        ArrayList<SituationsFromPractise> situations = new ArrayList<SituationsFromPractise>();

        ArrayList<User> users = new ArrayList<User>();

        ArrayList<Messages> messages = new ArrayList<Messages>();

        ArrayList<Tourism> tourism = new ArrayList<Tourism>();
        ArrayList<Sport> sport = new ArrayList<Sport>();
        ArrayList<Corporate> corporate = new ArrayList<Corporate>();

        Statement preparedSt = getDbConnection().createStatement();
        ResultSet set = preparedSt.executeQuery("SELECT * FROM " + table);

        switch (table){
            case "training" -> {
                System.out.println("Вывод всех записей повышения квалификации.");

                while (set.next()){
                    Training item = new Training(set.getInt(1), set.getString(2), set.getInt(3),
                            set.getString(4), set.getString(5), set.getString(6));
                    System.out.println(item);
                    trainings.add(item);
                }
                return trainings;
            }

            case "masterclass" -> {
                System.out.println("Вывод всех записей мастер-классов.");

                while (set.next()){
                    MasterClass item = new MasterClass(set.getInt(1), set.getString(2), set.getInt(3),
                            set.getString(4), set.getString(5), set.getString(6));
                    System.out.println(item);
                    masterClass.add(item);
                }
                return masterClass;
            }

            case "situation" -> {
                System.out.println("Вывод всех ситаций из практики для разбора.");

                while (set.next()){
                    SituationsFromPractise item = new SituationsFromPractise(set.getInt(1), set.getString(2), set.getInt(3),
                            set.getString(4), set.getString(5), set.getString(6));
                    System.out.println(item);
                    situations.add(item);
                }
                return situations;
            }

            case "users" -> {
                System.out.println("Вывод всех пользователей и администраторов.");

                while (set.next()){
                    User item = new User(set.getInt(1), set.getString(2),set.getString(3),
                            set.getInt(4), set.getString(5), set.getString(6), set.getString(7),
                            set.getString(8), set.getString(9));
                    System.out.println(item);
                    users.add(item);
                }
                return users;
            }

            case "tourismmessages" -> {
                System.out.println("Вывод всех сообщений из раздела \"туризм\".");

                while (set.next()){
                    Messages item = new Messages(set.getString(2), set.getString(3));
                    System.out.println(item);
                    messages.add(item);
                }
                return messages;
            }

            case "sportmessages" -> {
                System.out.println("Вывод всех сообщений из раздела \"спорт\".");

                while (set.next()){
                    Messages item = new Messages(set.getString(2), set.getString(3));
                    System.out.println(item);
                    messages.add(item);
                }
                return messages;
            }

            case "corporatemessages" -> {
                System.out.println("Вывод всех сообщений из раздела \"корпоративы\".");

                while (set.next()){
                    Messages item = new Messages(set.getString(2), set.getString(3));
                    System.out.println(item);
                    messages.add(item);
                }
                return messages;
            }

            case "tourism" -> {
                System.out.println("Вывод всех записей туристических мероприятий.");

                while (set.next()){
                    Tourism item = new Tourism(set.getInt(1), set.getString(2), set.getInt(3),
                            set.getInt(4), set.getInt(5), set.getString(6));
                    System.out.println(item);
                    tourism.add(item);
                }
                return tourism;
            }

            case "sport" -> {
                System.out.println("Вывод всех записей спортивных мероприятий.");

                while (set.next()){
                    Sport item = new Sport(set.getInt(1), set.getString(2), set.getInt(3),
                            set.getInt(4), set.getString(5), set.getString(6));
                    System.out.println(item);
                    sport.add(item);
                }
                return sport;
            }

            case "corporate" -> {
                System.out.println("Вывод всех записей корпоративов.");

                while (set.next()){
                    Corporate item = new Corporate(set.getInt(1), set.getString(2), set.getInt(3),
                            set.getInt(4), set.getString(5), set.getString(6));
                    System.out.println(item);
                    corporate.add(item);
                }
                return corporate;
            }
        }
        return null;
    }

    public Object showOneNote(int id, String table) throws SQLException, IOException, ClassNotFoundException {
        Statement preparedSt = getDbConnection().createStatement();

        ResultSet set = preparedSt.executeQuery("SELECT * FROM " + table + " WHERE id = " + id);

        switch (table){
            case "training" -> {
                System.out.println("Вывод записи повышения квалификации на изменение.\n");
                Training training = new Training();

                while (set.next()){
                    training = new Training(set.getInt(1), set.getString(2), set.getInt(3),
                            set.getString(4), set.getString(5), set.getString(6));
                    System.out.println(training);
                }
                return training;
            }

            case "masterclass" -> {
                System.out.println("Вывод мастер-класса на изменение.\n");
                MasterClass masterClass = new MasterClass();
                while (set.next()){
                    masterClass = new MasterClass(set.getInt(1), set.getString(2), set.getInt(3),
                            set.getString(4), set.getString(5), set.getString(6));
                    System.out.println(masterClass);
                }
                return masterClass;
            }

            case "situation" -> {
                System.out.println("Вывод ситуации из практики на изменение.\n");
                SituationsFromPractise situationFromPractise = new SituationsFromPractise();
                while (set.next()){
                    situationFromPractise = new SituationsFromPractise(set.getInt(1), set.getString(2), set.getInt(3),
                            set.getString(4), set.getString(5), set.getString(6));
                    System.out.println(situationFromPractise);
                }
                return situationFromPractise;
            }

            case "tourism" -> {
                System.out.println("Вывод записи туристического мероприятия на изменение.\n");
                Tourism tourism = new Tourism();
                while (set.next()){
                    tourism = new Tourism(set.getInt(1), set.getString(2), set.getInt(3),
                            set.getInt(4), set.getInt(5), set.getString(6));
                    System.out.println(tourism);
                }
                return tourism;
            }

            case "sport" -> {
                System.out.println("Вывод записи спортивного мероприятия на изменение.\n");
                Sport sport = new Sport();
                while (set.next()){
                    sport = new Sport(set.getInt(1), set.getString(2), set.getInt(3),
                            set.getInt(4), set.getString(5), set.getString(6));
                    System.out.println(sport);
                }
                return sport;
            }

            case "corporate" -> {
                System.out.println("Вывод записи корпоратива на изменение.\n");
                Corporate corporate = new Corporate();
                while (set.next()){
                    corporate = new Corporate(set.getInt(1), set.getString(2), set.getInt(3),
                            set.getInt(4), set.getString(5), set.getString(6));
                    System.out.println(corporate);
                }
                return corporate;
            }
        }

        return null;
    }

    public static String findUser(String request) throws SQLException, IOException, ClassNotFoundException {
        Statement preparedSt = getDbConnection().createStatement();
        ResultSet set = preparedSt.executeQuery("SELECT * FROM users");
        String[] arr = request.split("=");
        String res = "Неверный логин или пароль!";

        while (set.next()){
            if(Objects.equals(arr[0], set.getString(2))){
                if(Objects.equals(arr[1], set.getString(3))){
                    if(set.getInt(4) == 1){
                        res = "admin;" + set.getString(2) + ";" + set.getString(5);
                    } else {
                        res = "user;" + set.getString(2) + ";" + set.getString(5);
                    }
                }
            }
        }

        return res;
    }

    public void insertTraining(Training training) {
        try {
            Statement statement = getDbConnection().createStatement();
            statement.executeUpdate("INSERT INTO training(date, freePlaces, direction, organizer, curQualific)"
                    + " VALUES('" + training.getFullDate() + "', " + training.getFreePlaces() + ", '"
                    + training.getDirection() + "', '" + training.getOrganizer() + "', '" + training.getCurQualific() + "');");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void insertMasterClass(MasterClass masterClass) {
        try {
            Statement statement = getDbConnection().createStatement();
            statement.executeUpdate("INSERT INTO masterclass(date, freePlaces, direction, organizer, master)"
                    + " VALUES('" + masterClass.getFullDate() + "', " + masterClass.getFreePlaces() + ", '"
                    + masterClass.getDirection() + "', '" + masterClass.getOrganizer() + "', '" + masterClass.getMaster() + "');");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void insertSituation(SituationsFromPractise situation) {
        try {
            Statement statement = getDbConnection().createStatement();
            statement.executeUpdate("INSERT INTO situation(date, freePlaces, direction, organizer, situation)"
                    + " VALUES('" + situation.getFullDate() + "', " + situation.getFreePlaces() + ", '"
                    + situation.getDirection() + "', '" + situation.getOrganizer() + "', '" + situation.getSituation() + "');");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void insertTourism(Tourism tourism) {
        try {
            Statement statement = getDbConnection().createStatement();
            statement.executeUpdate("INSERT INTO tourism(date, freePlaces, price, duration, attraction)"
                    + " VALUES('" + tourism.getFullDate() + "', " + tourism.getFreePlaces() + ", '"
                    + tourism.getPrice() + "', '" + tourism.getDuration() + "', '" + tourism.getAttraction() + "');");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void insertCorporate(Corporate corporate) {
        try {
            Statement statement = getDbConnection().createStatement();
            statement.executeUpdate("INSERT INTO corporate(date, freePlaces, price, restaurant, party)"
                    + " VALUES('" + corporate.getFullDate() + "', " + corporate.getFreePlaces() + ", '"
                    + corporate.getPrice() + "', '" + corporate.getRestaurant() + "', '" + corporate.getParty() + "');");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void insertSport(Sport sport) {
        try {
            Statement statement = getDbConnection().createStatement();
            statement.executeUpdate("INSERT INTO sport(date, freePlaces, price, kindOfSport, name)"
                    + " VALUES('" + sport.getFullDate() + "', " + sport.getFreePlaces() + ", '"
                    + sport.getPrice() + "', '" + sport.getKindOfSport() + "', '" + sport.getName() + "');");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void insertUser(User user) {
        try {
            Statement statement = getDbConnection().createStatement();
            statement.executeUpdate("INSERT INTO users(login, password, role, FIO, post)" + " VALUES('" + user.getLogin() +
                    "', '" + user.getPassword() + "', '" + user.getRole() + "', '" + user.getFIO() + "', '" + user.getPost() + "');");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void delete(int idForDelete, String table) throws IOException {
        String delete = "DELETE FROM " + table + "\n" + "WHERE id = " + idForDelete;
        try {
            PreparedStatement preparedSt = getDbConnection().prepareStatement(delete);
            preparedSt.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void update(Universal universal, String table) throws SQLException, IOException, ClassNotFoundException {
        String updatePart1 = null;
        String updatePart2;
        String text1 = universal.getText1(), text2 = universal.getText2(), text3 = universal.getText3(),
                text4 = universal.getText4(), text5 = universal.getText5();

        switch (table){
            case "training" -> {
                Training training = (Training) showOneNote(universal.getId(), "training");

                if(Objects.equals(text1, "=")) text1 = training.getFullDate();
                if(Objects.equals(text2, "=")) text2 = Integer.toString(training.getFreePlaces());
                if(Objects.equals(text3, "=")) text3 = training.getDirection();
                if(Objects.equals(text4, "=")) text4 = training.getOrganizer();
                if(Objects.equals(text5, "=")) text5 = training.getCurQualific();

                updatePart2 = " SET " + "date = '" + text1 + "', freePlaces = " + Integer.parseInt(text2) +
                        ", direction = '" + text3 + "', organizer = '" + text4 + "', curQualific = '" +
                        text5 + "'";
                updatePart1 = "UPDATE " + table + updatePart2 + " WHERE id = " + universal.getId();

                System.out.println(updatePart1);
            }

            case "masterclass" -> {
                MasterClass masterClass = (MasterClass) showOneNote(universal.getId(), "masterclass");

                if(Objects.equals(text1, "=")) text1 = masterClass.getFullDate();
                if(Objects.equals(text2, "=")) text2 = Integer.toString(masterClass.getFreePlaces());
                if(Objects.equals(text3, "=")) text3 = masterClass.getDirection();
                if(Objects.equals(text4, "=")) text4 = masterClass.getOrganizer();
                if(Objects.equals(text5, "=")) text5 = masterClass.getMaster();

                updatePart2 = " SET " + "date = '" + text1 + "', freePlaces = " + Integer.parseInt(text2) +
                        ", direction = '" + text3 + "', organizer = '" + text4 + "', master = '" +
                        text5 + "'";
                updatePart1 = "UPDATE " + table + updatePart2 + " WHERE id = " + universal.getId();

                System.out.println(updatePart1);
            }

            case "situation" -> {
                SituationsFromPractise situation = (SituationsFromPractise) showOneNote(universal.getId(), "situation");

                if(Objects.equals(text1, "=")) text1 = situation.getFullDate();
                if(Objects.equals(text2, "=")) text2 = Integer.toString(situation.getFreePlaces());
                if(Objects.equals(text3, "=")) text3 = situation.getDirection();
                if(Objects.equals(text4, "=")) text4 = situation.getOrganizer();
                if(Objects.equals(text5, "=")) text5 = situation.getSituation();

                updatePart2 = " SET " + "date = '" + text1 + "', freePlaces = " + Integer.parseInt(text2) +
                        ", direction = '" + text3 + "', organizer = '" + text4 + "', situation = '" +
                        text5 + "'";
                updatePart1 = "UPDATE " + table + updatePart2 + " WHERE id = " + universal.getId();

                System.out.println(updatePart1);
            }

            case "tourism" -> {
                Tourism tourism = (Tourism) showOneNote(universal.getId(), "tourism");

                if(Objects.equals(text1, "=")) text1 = tourism.getFullDate();
                if(Objects.equals(text2, "=")) text2 = Integer.toString(tourism.getFreePlaces());
                if(Objects.equals(text3, "=")) text3 = Integer.toString(tourism.getPrice());
                if(Objects.equals(text4, "=")) text4 = Integer.toString(tourism.getDuration());
                if(Objects.equals(text5, "=")) text5 = tourism.getAttraction();

                updatePart2 = " SET " + "date = '" + text1 + "', freePlaces = " + Integer.parseInt(text2) +
                        ", price = " + Integer.parseInt(text3) + ", duration = " + Integer.parseInt(text4) + ", attraction = '" +
                        text5 + "'";
                updatePart1 = "UPDATE " + table + updatePart2 + " WHERE id = " + universal.getId();

                System.out.println(updatePart1);
            }

            case "sport" -> {
                Sport sport = (Sport) showOneNote(universal.getId(), "sport");

                if(Objects.equals(text1, "=")) text1 = sport.getFullDate();
                if(Objects.equals(text2, "=")) text2 = Integer.toString(sport.getFreePlaces());
                if(Objects.equals(text3, "=")) text3 = Integer.toString(sport.getPrice());
                if(Objects.equals(text4, "=")) text4 = sport.getKindOfSport();
                if(Objects.equals(text5, "=")) text5 = sport.getName();

                updatePart2 = " SET " + "date = '" + text1 + "', freePlaces = " + Integer.parseInt(text2) +
                        ", price = " + Integer.parseInt(text3) + ", kindOfSport = '" + text4 + "', name = '" +
                        text5 + "'";
                updatePart1 = "UPDATE " + table + updatePart2 + " WHERE id = " + universal.getId();

                System.out.println(updatePart1);
            }

            case "corporate" -> {
                Corporate corporate = (Corporate) showOneNote(universal.getId(), "corporate");

                if(Objects.equals(text1, "=")) text1 = corporate.getFullDate();
                if(Objects.equals(text2, "=")) text2 = Integer.toString(corporate.getFreePlaces());
                if(Objects.equals(text3, "=")) text3 = Integer.toString(corporate.getPrice());
                if(Objects.equals(text4, "=")) text4 = corporate.getRestaurant();
                if(Objects.equals(text5, "=")) text5 = corporate.getParty();

                updatePart2 = " SET " + "date = '" + text1 + "', freePlaces = " + Integer.parseInt(text2) +
                        ", price = " + Integer.parseInt(text3) + ", restaurant = '" + text4 + "', party = '" +
                        text5 + "'";
                updatePart1 = "UPDATE " + table + updatePart2 + " WHERE id = " + universal.getId();

                System.out.println(updatePart1);
            }
        }
        PreparedStatement preparedSt = getDbConnection().prepareStatement(updatePart1);
        preparedSt.executeUpdate();
    }

    public ArrayList search(Universal universal , String table) throws SQLException, IOException, ClassNotFoundException {
        ArrayList<Training> trainings = new ArrayList<>();
        ArrayList<MasterClass> masterClasses = new ArrayList<>();
        ArrayList<SituationsFromPractise> situations = new ArrayList<>();

        ArrayList<Tourism> tourism = new ArrayList<>();
        ArrayList<Sport> sport = new ArrayList<>();
        ArrayList<Corporate> corporate = new ArrayList<>();

        int id = universal.getId();
        String sqlQuery = null, text = "";
        Statement preparedSt = getDbConnection().createStatement();

        if(id != 0) text += "id = " + id + " AND ";
        if(!Objects.equals(universal.getText1(), "=")) text += "date = '" + universal.getText1() + "' AND ";
        if(!Objects.equals(universal.getText2(), "=")) text += "freePlaces = " + Integer.parseInt(universal.getText2()) + " AND ";

        switch (table){
            case "training" -> {
                if(!Objects.equals(universal.getText3(), "=")) text += "direction = '" + universal.getText3() + "' AND ";
                if(!Objects.equals(universal.getText4(), "=")) text += "organizer = '" + universal.getText4() + "' AND ";
                if(!Objects.equals(universal.getText5(), "=")) text += "curQualific = '" + universal.getText5() + "' AND ";

                text = text.substring(0, text.length() - 4);
                sqlQuery = "SELECT * FROM " + table + " WHERE " + text;
                System.out.println(sqlQuery);

                ResultSet set = preparedSt.executeQuery(sqlQuery);
                while (set.next()){
                    Training item = new Training(set.getInt(1), set.getString(2), set.getInt(3),
                            set.getString(4), set.getString(5), set.getString(6));
                    trainings.add(item);
                }

                return trainings;
            }
            case "masterclass" -> {
                if(!Objects.equals(universal.getText3(), "=")) text += "direction = '" + universal.getText3() + "' AND ";
                if(!Objects.equals(universal.getText4(), "=")) text += "organizer = '" + universal.getText4() + "' AND ";
                if(!Objects.equals(universal.getText5(), "=")) text += "master = '" + universal.getText5() + "' AND ";

                text = text.substring(0, text.length() - 4);
                sqlQuery = "SELECT * FROM " + table + " WHERE " + text;
                System.out.println(sqlQuery);

                ResultSet set = preparedSt.executeQuery(sqlQuery);
                while (set.next()){
                    MasterClass item = new MasterClass(set.getInt(1), set.getString(2), set.getInt(3),
                            set.getString(4), set.getString(5), set.getString(6));
                    masterClasses.add(item);
                }

                return masterClasses;
            }
            case "situation" -> {
                if(!Objects.equals(universal.getText3(), "=")) text += "direction = '" + universal.getText3() + "' AND ";
                if(!Objects.equals(universal.getText4(), "=")) text += "organizer = '" + universal.getText4() + "' AND ";
                if(!Objects.equals(universal.getText5(), "=")) text += "situation = '" + universal.getText5() + "' AND ";

                text = text.substring(0, text.length() - 4);
                sqlQuery = "SELECT * FROM " + table + " WHERE " + text;
                System.out.println(sqlQuery);

                ResultSet set = preparedSt.executeQuery(sqlQuery);
                while (set.next()){
                    SituationsFromPractise item = new SituationsFromPractise(set.getInt(1), set.getString(2), set.getInt(3),
                            set.getString(4), set.getString(5), set.getString(6));
                    situations.add(item);
                }

                return situations;
            }
            case "tourism" -> {
                if(!Objects.equals(universal.getText3(), "=")) text += "price = " + Integer.parseInt(universal.getText3()) + " AND ";
                if(!Objects.equals(universal.getText4(), "=")) text += "duration = " + Integer.parseInt(universal.getText4()) + " AND ";
                if(!Objects.equals(universal.getText5(), "=")) text += "attraction = '" + universal.getText5() + "' AND ";

                text = text.substring(0, text.length() - 4);
                sqlQuery = "SELECT * FROM " + table + " WHERE " + text;
                System.out.println(sqlQuery);

                ResultSet set = preparedSt.executeQuery(sqlQuery);
                while (set.next()){
                    Tourism item = new Tourism(set.getInt(1), set.getString(2), set.getInt(3),
                            set.getInt(4), set.getInt(5), set.getString(6));
                    tourism.add(item);
                }

                return tourism;
            }
            case "sport" -> {
                if(!Objects.equals(universal.getText3(), "=")) text += "price = " + Integer.parseInt(universal.getText3()) + " AND ";
                if(!Objects.equals(universal.getText4(), "=")) text += "kindOfSport = '" + universal.getText4() + "' AND ";
                if(!Objects.equals(universal.getText5(), "=")) text += "name = '" + universal.getText5() + "' AND ";

                text = text.substring(0, text.length() - 4);
                sqlQuery = "SELECT * FROM " + table + " WHERE " + text;
                System.out.println(sqlQuery);

                ResultSet set = preparedSt.executeQuery(sqlQuery);
                while (set.next()){
                    Sport item = new Sport(set.getInt(1), set.getString(2), set.getInt(3),
                            set.getInt(4), set.getString(5), set.getString(6));
                    sport.add(item);
                }

                return sport;
            }
            case "corporate" -> {
                if(!Objects.equals(universal.getText3(), "=")) text += "price = " + Integer.parseInt(universal.getText3()) + " AND ";
                if(!Objects.equals(universal.getText4(), "=")) text += "restaurant = '" + universal.getText4() + "' AND ";
                if(!Objects.equals(universal.getText5(), "=")) text += "attraction = '" + universal.getText5() + "' AND ";

                text = text.substring(0, text.length() - 4);
                sqlQuery = "SELECT * FROM " + table + " WHERE " + text;
                System.out.println(sqlQuery);

                ResultSet set = preparedSt.executeQuery(sqlQuery);
                while (set.next()){
                    Corporate item = new Corporate(set.getInt(1), set.getString(2), set.getInt(3),
                            set.getInt(4), set.getString(5), set.getString(6));
                    corporate.add(item);
                }

                return corporate;
            }
        }
        return null;
    }

    public ArrayList<User> searchUser(User user) throws SQLException, IOException, ClassNotFoundException {
        ArrayList<User> users = new ArrayList<>();

        String sqlQuery = null, text = "";
        Statement preparedSt = getDbConnection().createStatement();

        if(user.getId() != 0) text += "id = " + user.getId() + " AND ";
        if(!Objects.equals(user.getLogin(), "=")) text += "login = '" + user.getLogin() + "' AND ";
        if(user.getRole() != -1) text += "role = " + user.getRole() + " AND ";
        if(!Objects.equals(user.getFIO(), "=")) text += "FIO = '" + user.getFIO() + "' AND ";
        if(!Objects.equals(user.getPost(), "=")) text += "post = '" + user.getPost() + "' AND ";

        text = text.substring(0, text.length() - 4);
        sqlQuery = "SELECT * FROM users WHERE " + text;
        System.out.println(sqlQuery);

        ResultSet set = preparedSt.executeQuery(sqlQuery);

        while (set.next()){
            User item = new User(set.getInt(1), set.getString(2),set.getString(3),
                    set.getInt(4), set.getString(5), set.getString(6), set.getString(7),
                    set.getString(8), set.getString(9));
            users.add(item);
        }

        return users;
    }

    public void addNoteToUser(String table, String noteID, String curUser) throws SQLException, IOException, ClassNotFoundException {
        Statement preparedSt = getDbConnection().createStatement();
        ResultSet set = preparedSt.executeQuery("SELECT * FROM users");

        int curUserID = 0;
        String curUsersNotes = null, queryUpd;

        while (set.next()){
            if(Objects.equals(curUser, set.getString(2))){
                curUserID = set.getInt(1);

                if(Objects.equals(table, "training")) curUsersNotes = set.getString(7);
                if(Objects.equals(table, "masterclass")) curUsersNotes = set.getString(8);
                if(Objects.equals(table, "situation")) curUsersNotes = set.getString(9);
            }
        }
        if(curUsersNotes != null){
            noteID += ", " + curUsersNotes;
        }
        queryUpd = "UPDATE users SET " + table + " = '" + noteID + "' WHERE id = " + curUserID;
        System.out.println(queryUpd);
        preparedSt.executeUpdate(queryUpd);
    }

    public void makeAdmin(int id) throws SQLException, IOException, ClassNotFoundException {
        String query = "UPDATE users SET role = 1 WHERE id = " + id;
        PreparedStatement preparedSt = getDbConnection().prepareStatement(query);
        preparedSt.executeUpdate();
    }

    public void sendMess(String login, String mess, String table){
        try {
            String sqlRequest = "INSERT INTO " + table + " (login, message) VALUES('" + login + "', '" + mess + "');";
            Statement statement = getDbConnection().createStatement();
            statement.executeUpdate(sqlRequest);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Boolean checkUser(String login) throws SQLException, IOException, ClassNotFoundException {
        Statement preparedSt = getDbConnection().createStatement();
        ResultSet set = preparedSt.executeQuery("SELECT * FROM users");
        Boolean flag = true;

        while (set.next()){
            if(Objects.equals(login, set.getString(2))){
                flag = false;
            }
        }

        return flag;
    }

    public void updateUser(User user) throws SQLException, IOException, ClassNotFoundException {
        String trainingQuery, masterclassQuery, situationQuery;
        if(user.getTraining() == null) trainingQuery = "' , training = " + user.getTraining() + ",";
        else trainingQuery = "' , training = '" + user.getTraining() + "',";
        if(user.getMasterClass() == null) masterclassQuery = " masterclass = " + user.getMasterClass() + ",";
        else masterclassQuery = " masterclass = '" + user.getMasterClass() + "',";
        if(user.getSituations() == null) situationQuery = " situation = " + user.getSituations();
        else situationQuery = " situation = '" + user.getSituations() + "'";

        String updQuery = "UPDATE kursach.users SET password = '" + user.getPassword() + "', FIO = '" + user.getFIO() +
                "', post = '" + user.getPost() + trainingQuery + masterclassQuery +
                situationQuery + " WHERE id = " + user.getId();

        System.out.println(updQuery);

        PreparedStatement preparedSt = getDbConnection().prepareStatement(updQuery);
        preparedSt.executeUpdate();
    }
}
