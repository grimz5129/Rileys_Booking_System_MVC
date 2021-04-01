/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Time;
import java.time.LocalDate;

/**
 *
 * @author Yefri
 */
public class Booking {
    private int booking_id;
    private String activity;
    private LocalDate date;
    private String periodofday;
    private int duration;
    private Time time;    
    private int cust_id;
    
    public Booking(){
        booking_id = -1;
        activity = "";
        date = null;
        periodofday = "";
        duration = -1;
        time = null;
        cust_id = -1;
    }    
    
    public void setBooking_ID(int booking_id) {
        this.booking_id = booking_id;
    }
    
    public int getBooking_ID(){
        return booking_id;
    }
    
    public void setActivity(String activity) {
        this.activity = activity;
    }
    
    public String getActivity(){
        return activity;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public LocalDate getDate(){
        return date;
    }
    
    public void setPeriodOfDay(String periodofday) {
        this.periodofday = periodofday;
    }
    
    public String getPeriodOfDay(){
        return periodofday;
    }
    
    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    public int getDuration(){
        return duration;
    }
    
    public void setTime(Time time) {
        this.time = time;
    }
    
    public Time getTime(){
        return time;
    }
    
    public void setCust_ID(int cust_id) {
        this.cust_id = cust_id;
    }

    public int getCust_ID() {
        return cust_id;
    }
    
    
    
}
