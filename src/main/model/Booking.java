package main.model;


import java.time.LocalDate;


public class Booking {

    private String bookingId;
    private String username;
    private String seatId;
    private LocalDate bookingDate;
    private String status;
    private String bookingTime;
    private String checkIn;


    public Booking(String bookingId, String username, String seatId, LocalDate bookingDate, String status, String bookingTime, String checkIn) {

        this.username = username;
        this.bookingId = bookingId;
        this.seatId = seatId;
        this.bookingDate = bookingDate;
        this.status = status;
        this.bookingTime = bookingTime;
        this.checkIn = checkIn;
    }

    public void setBookingId(String bookingId) {

        this.bookingId = bookingId;
    }

    public void setSeatId(String seatId) {

        this.seatId = seatId;
    }

    public void setBookingTime(String time) {

        this.bookingTime = time;
    }

    public String getSeatId() {

        return seatId;
    }

    public LocalDate getBookingDate() {

        return bookingDate;
    }

    public String getBookingId() {

        return bookingId;
    }

    public String getStatus() {

        return status;
    }

    public String getUsername() {

        return username;
    }

    public String getBookingTime() {

        return bookingTime;
    }

    public String getCheckIn() {

        return checkIn;
    }
}
