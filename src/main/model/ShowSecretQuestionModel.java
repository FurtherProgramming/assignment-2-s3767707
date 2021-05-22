package main.model;

import main.Main;
import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowSecretQuestionModel {

    Connection connection;

    public ShowSecretQuestionModel(){

        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);

    }

    public Boolean isDbConnected(){
        try {
            return !connection.isClosed();
        }
        catch(Exception e){
            return false;
        }
    }

    public Boolean validateAnswer(String answer) {

        User user = (User) Main.stage.getUserData();
        if(user.getAnswer().equals(answer)) {
            return true;
        }
        else {
            return false;
        }
    }

}
