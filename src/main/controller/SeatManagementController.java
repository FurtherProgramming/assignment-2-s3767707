package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.input.MouseEvent;
import main.Main;
import main.model.AdminEditBookingModel;
import main.model.LoginModel;
import main.model.SeatManagementModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SeatManagementController implements Initializable {

    private Main main = new Main();
    private SeatManagementModel seatManagementModel = new SeatManagementModel();
    private AdminEditBookingModel adminEditBookingModel = new AdminEditBookingModel();
    @FXML
    private ChoiceBox<String> condition;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;

    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){

        addItemToChoiceBox();
    }

    public void addItemToChoiceBox() {

        ObservableList cdlist = FXCollections.observableArrayList();
        ArrayList<String> cd = new ArrayList<String>();
        cd.add("Restriction");
        cd.add("Lockdown");
        cdlist.addAll(cd);
        condition.getItems().addAll(cdlist);
    }

    public void Apply(ActionEvent event) throws Exception {

        if(startDate.getValue().isAfter(LocalDate.now())) {
            if(condition.getValue().equals("Restriction")) {
                ArrayList<String> allSeatIds = seatManagementModel.getSeatId("Lockdown");
                ArrayList<String> seatIds = seatManagementModel.getSeatId(condition.getValue());
                main.setSeatColor("ui/SeatManagement.fxml", allSeatIds, seatIds);
            }
            else {
                ArrayList<String> seatIds = seatManagementModel.getSeatId(condition.getValue());
                main.setSeatColor("ui/SeatManagement.fxml", seatIds, seatIds);
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you confirm to apply this condition?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                alert.close();
                main.change("ui/BookingManagement.fxml");
                seatManagementModel.updateSeat(condition.getValue(), startDate.getValue(), endDate.getValue());
                if(condition.getValue().equals("Restriction")) {
                    ArrayList<String> seatId = seatManagementModel.getBookingIdAffectedByCondition(condition.getValue(), startDate.getValue(), endDate.getValue());
                    for(String id : seatId) {
                        adminEditBookingModel.editBookingStatus(id,"Rejected");
                    }
                }
                else {
                    ArrayList<String> seatId = seatManagementModel.getBookingIdAffectedByCondition(condition.getValue(), startDate.getValue(), endDate.getValue());
                    for(String id : seatId) {
                        adminEditBookingModel.editBookingStatus(id,"Rejected");
                    }
                }
            }
            else {
                alert.close();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid start date. Start date should be in the future!", ButtonType.CLOSE);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.CLOSE)
                alert.close();
        }
    }

    public void Back(ActionEvent event) throws Exception {

        main.change("ui/BookingManagement.fxml");
    }

    public void Reset(ActionEvent event) throws Exception {

        seatManagementModel.resetCondition();
        main.change("ui/SeatManagement.fxml");
    }





}
