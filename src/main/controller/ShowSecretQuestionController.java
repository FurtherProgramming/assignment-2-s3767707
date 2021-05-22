package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.Main;
import main.model.ResetPasswordModel;
import main.model.ShowSecretQuestionModel;
import main.model.User;

import javax.xml.soap.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowSecretQuestionController implements Initializable {
    private ShowSecretQuestionModel secretQuestionModel = new ShowSecretQuestionModel();
    private ResetPasswordModel resetPasswordModel = new ResetPasswordModel();
    private Main main = new Main();
    @FXML
    private Label isConnected;
    @FXML
    private TextArea txtSecretQuestion;
    @FXML
    private TextField txtAnswer;



    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){
        if (secretQuestionModel.isDbConnected()){
            isConnected.setText("Connected to database");
        }else{
            isConnected.setText("Not Connected to database");
        }
        User user = (User) Main.stage.getUserData();
        txtSecretQuestion.setText(user.getQuestion());
    }

    public void Submit(ActionEvent event){

        try {
            if (secretQuestionModel.validateAnswer(txtAnswer.getText())){
                main.change("ui/newPassword.fxml");
                isConnected.setText("Answer is correct");
            }else{
                isConnected.setText("Answer is incorrect");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Cancel(ActionEvent event) throws Exception {

        main.change("ui/login.fxml");
    }




}
