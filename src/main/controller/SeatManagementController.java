package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import main.Main;
import main.model.AdminEditBookingModel;
import main.model.SeatManagementModel;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

/*
 * Class:		SeatManagementController
 * Description:	A class that handles seat condition during COVID
 * Author:		Anson Go Guang Ping
 */
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
    public void initialize(URL location, ResourceBundle resources) {

        addItemToChoiceBox();
    }

    public void addItemToChoiceBox() {

        ObservableList cdlist = FXCollections.observableArrayList();
        ArrayList<String> cd = new ArrayList<String>();
        // admin has two options: Restriction(partially locked) and Lockdown(all locked), can unlock the while state by pressing reset button
        cd.add("Restriction");
        cd.add("Lockdown");
        cdlist.addAll(cd);
        condition.getItems().addAll(cdlist);
    }


    public void Apply(ActionEvent event) throws Exception {

        if (condition.getValue() != null) {
            if (startDate.getValue() != null) {
                if (endDate.getValue() != null) {
                    if (startDate.getValue().isAfter(LocalDate.now())) { // start duration must be in the future
                        if (startDate.getValue().isEqual(endDate.getValue()) || startDate.getValue().isBefore(endDate.getValue())) { // duration strat date is always before end date
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you confirm to set the seat condition?", ButtonType.YES, ButtonType.NO);
                            alert.showAndWait();
                            if (alert.getResult() == ButtonType.YES) {
                                if (condition.getValue().equals("Restriction")) {
                                    seatManagementModel.updateSeat(condition.getValue(), startDate.getValue(), endDate.getValue()); // update seat details in database
                                    ArrayList<String> bookingIds = seatManagementModel.getBookingIdAffectedByCondition("Restriction", startDate.getValue(), endDate.getValue());
                                    // reject all bookings during the duration
                                    for (String id : bookingIds) {
                                        adminEditBookingModel.editBookingStatus(id, "Rejected");
                                    }
                                    main.setSeatColorInSeatManagement("ui/SeatManagement.fxml", seatManagementModel.getAllSeats()); // change seat visual colours
                                }
                                if (condition.getValue().equals("Lockdown")) {
                                    seatManagementModel.updateSeat(condition.getValue(), startDate.getValue(), endDate.getValue());
                                    ArrayList<String> bookingIds = seatManagementModel.getBookingIdAffectedByCondition("Lockdown", startDate.getValue(), endDate.getValue());
                                    for (String id : bookingIds) {
                                        adminEditBookingModel.editBookingStatus(id, "Rejected");
                                    }
                                    main.setSeatColorInSeatManagement("ui/SeatManagement.fxml", seatManagementModel.getAllSeats());
                                }
                                alert.close();
                            } else {
                                alert.close();
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Start date cannot be after end date!!", ButtonType.CLOSE);
                            alert.showAndWait();
                            if (alert.getResult() == ButtonType.CLOSE)
                                alert.close();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Start date cannot be today or past!", ButtonType.CLOSE);
                        alert.showAndWait();
                        if (alert.getResult() == ButtonType.CLOSE)
                            alert.close();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Pick end date!", ButtonType.CLOSE);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.CLOSE)
                        alert.close();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Pick start date!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Pick condition first!", ButtonType.CLOSE);
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
        main.setSeatColorInSeatManagement("ui/SeatManagement.fxml", seatManagementModel.getAllSeats());
    }


}
