package main.model;

import java.time.LocalDate;

/*
 * Class:		Booking
 * Description:	A class that represents a booking item. A booking is created when user books a seat.
 * Author:		Anson Go Guang Ping
 */
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

    public String getSeatId() {

        return seatId;
    }

    public void setSeatId(String seatId) {

        this.seatId = seatId;
    }

    public LocalDate getBookingDate() {

        return bookingDate;
    }

    public String getBookingId() {

        return bookingId;
    }

    public void setBookingId(String bookingId) {

        this.bookingId = bookingId;
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
