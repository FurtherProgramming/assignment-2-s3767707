package main.model;

import java.time.LocalDate;

/*
 * Class:		Seat
 * Description:	A class that represents a seat object. A seat represents a desk in the booking scene.
 * Author:		Anson Go Guang Ping
 */
public class Seat {

    private String seatId;
    private String status;
    private String condition;
    private LocalDate startDate;
    private LocalDate endDate;


    public Seat(String seatId, String status, String condition, LocalDate startDate, LocalDate endDate) {

        this.seatId = seatId;
        this.status = status;
        this.condition = condition;
        this.startDate = startDate;
        this.endDate = endDate;

    }

    public String getSeatId() {

        return seatId;
    }

    public String getStatus() {

        return status;
    }

    public String getCondition() {

        return condition;
    }

    public LocalDate getStartDate() {

        return startDate;
    }

    public LocalDate getEndDate() {

        return endDate;
    }
}
