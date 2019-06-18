package com.kfstudio.www.oym;

public class Booking {
    private Boolean booking_done;
    private String booking_time;

    public String getBooker_phone() {
        return booker_phone;
    }

    public void setBooker_phone(String booker_phone) {
        this.booker_phone = booker_phone;
    }

    private String booker_phone;
    private String booking_for;
    private String booking_rate;

    public Booking(){

    }
    public Booking(Boolean booking_done, String booking_time, String booking_for, String booking_rate,String booker_phone) {
        this.booking_done = booking_done;
        this.booker_phone=booker_phone;
        this.booking_time = booking_time;
        this.booking_for = booking_for;
        this.booking_rate = booking_rate;
    }

    public Boolean getBooking_done() {
        return booking_done;
    }

    public void setBooking_done(Boolean booking_done) {
        this.booking_done = booking_done;
    }

    public String getBooking_time() {
        return booking_time;
    }

    public void setBooking_time(String booking_time) {
        this.booking_time = booking_time;
    }

    public String getBooking_for() {
        return booking_for;
    }

    public void setBooking_for(String booking_for) {
        this.booking_for = booking_for;
    }

    public String getBooking_rate() {
        return booking_rate;
    }

    public void setBooking_rate(String booking_rate) {
        this.booking_rate = booking_rate;
    }

    public Booking(Boolean booking_done) {
        this.booking_done = booking_done;
    }
}
