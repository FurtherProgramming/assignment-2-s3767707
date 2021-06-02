package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.Main;
import main.model.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminEditBookingController implements Initializable {

    private Main main = new Main();
    private AdminEditBookingModel adminEditBookingModel = new AdminEditBookingModel();
    private UserBookingModel userBookingModel = new UserBookingModel();
    private ArrayList<String> seats = new ArrayList<String>();
    @FXML
    public ObservableList<Booking> populateTableList;
    @FXML
    private TableColumn<Booking, String> bookingId;
    @FXML
    private TableColumn<Booking, String> username;
    @FXML
    private TableColumn<Booking, String> seatId;
    @FXML
    private TableColumn<Booking, LocalDate> bookingDate;
    @FXML
    private TableColumn<Booking, String> status;
    @FXML
    private TableColumn<Booking, String> bookingTime;
    @FXML
    private TableView<Booking> table;
    @FXML
    private String selectedRowId;

    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setTableColumns();
        User user = (User) Main.stage.getUserData();


        try {
            populateTableList = FXCollections.observableArrayList(adminEditBookingModel.getUserBookings("Pending", LocalDate.now()));
            table.getItems().addAll(populateTableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Home(ActionEvent event) throws Exception {

        main.change("ui/Home.fxml");
    }

    public void Profile(ActionEvent event) throws Exception {

        main.change("ui/UserProfile.fxml");
    }

    public void Logout(ActionEvent event) throws Exception {

        main.change("ui/Login.fxml");
    }


    private void setTableColumns() {

        // specifying how to populate the columns of the table
        bookingId.setCellValueFactory(new PropertyValueFactory<Booking, String>("bookingId"));
        username.setCellValueFactory(new PropertyValueFactory<Booking, String>("username"));
        seatId.setCellValueFactory(new PropertyValueFactory<Booking, String>("seatId"));
        bookingDate.setCellValueFactory(new PropertyValueFactory<Booking, LocalDate>("bookingDate"));
        bookingTime.setCellValueFactory(new PropertyValueFactory<Booking, String>("bookingTime"));
        status.setCellValueFactory(new PropertyValueFactory<Booking, String>("status"));
    }


    public boolean isSelectedRowValid(String selectedRowId) {
        return selectedRowId != null ? true : false;
    }


    public void getRowId(MouseEvent e) {

        // storing the id number of the selected row in a String
        if (table.getSelectionModel().getSelectedItem() != null)
            this.selectedRowId = table.getSelectionModel().getSelectedItem().getBookingId();
    }


    public void RejectBooking(ActionEvent event) throws Exception {

        if (table.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you wish to reject this booking?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            // if the user clicks OK

            if (alert.getResult() == ButtonType.YES) {
                if (isSelectedRowValid(selectedRowId)) {
                    String bookingId = table.getSelectionModel().getSelectedItem().getBookingId();
                    adminEditBookingModel.editBookingStatus(bookingId, "Rejected");
                    main.change("ui/AdminEditBooking.fxml");
                    alert.close();
                }
            } else if (alert.getResult() == ButtonType.NO) {
                alert.close();
            }
        } else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR, "Please pick a booking!", ButtonType.CLOSE);
            alert2.showAndWait();
            if (alert2.getResult() == ButtonType.CLOSE)
                alert2.close();
        }
    }

    public void AcceptBooking(ActionEvent event) throws Exception {

        if (table.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you wish to accept this booking?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            // if the user clicks OK

            if (alert.getResult() == ButtonType.YES) {
                if (isSelectedRowValid(selectedRowId)) {
                    String bookingId = table.getSelectionModel().getSelectedItem().getBookingId();
                    adminEditBookingModel.editBookingStatus(bookingId, "Accepted");
                    main.change("ui/AdminEditBooking.fxml");
                    alert.close();
                }
            } else if (alert.getResult() == ButtonType.NO) {
                alert.close();
            }
        } else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR, "Please pick a booking!", ButtonType.CLOSE);
            alert2.showAndWait();
            if (alert2.getResult() == ButtonType.CLOSE)
                alert2.close();
        }
    }

}
