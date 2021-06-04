package main.model;

/*
 * Class:		BookingHolder
 * Description:	A singleton class that is used to pass booking information between scenes
 * Author:		Anson Go Guang Ping
 */
public class BookingHolder {

    private Booking booking;
    private final static BookingHolder INSTANCE = new BookingHolder();

    private BookingHolder() {}

    public static BookingHolder getInstance() {
        return INSTANCE;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Booking getBooking() {
        return this.booking;
    }


}
