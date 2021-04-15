/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Yefri
 */
public class Booking {
     private SimpleStringProperty activity, periodofday, duration, time, date, cust_id;

    public Booking(String activity, String periodofday, String duration, String time, String date) {
        this.activity = new SimpleStringProperty(activity);
        this.periodofday = new SimpleStringProperty(periodofday);
        this.duration = new SimpleStringProperty(duration);
        this.time = new SimpleStringProperty(time);
        this.date = new SimpleStringProperty(date);
    }
    
    public Booking(String activity, String periodofday, String duration, String time, String date, String cust_id) {
        this.activity = new SimpleStringProperty(activity);
        this.periodofday = new SimpleStringProperty(periodofday);
        this.duration = new SimpleStringProperty(duration);
        this.time = new SimpleStringProperty(time);
        this.date = new SimpleStringProperty(date);
        this.cust_id = new SimpleStringProperty(cust_id);
    }
    
    public Booking(String periodofday, String time, String date){
        this.periodofday = new SimpleStringProperty(periodofday);
        this.time = new SimpleStringProperty(time);
        this.date = new SimpleStringProperty(date);
    }    

    public String getActivity() {
        return activity.get();
    }

    public void setActivity(SimpleStringProperty activity) {
        this.activity = activity;
    }

    public String getPeriodofday() {
        return periodofday.get();
    }

    public void setPeriodofday(SimpleStringProperty periodofday) {
        this.periodofday = periodofday;
    }

    public String getDuration() {
        return duration.get();
    }

    public void setDuration(SimpleStringProperty duration) {
        this.duration = duration;
    }

    public String getTime() {
        return time.get();
    }

    public void setTime(SimpleStringProperty time) {
        this.time = time;
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(SimpleStringProperty date) {
        this.date = date;
    }

    public String getCust_id() {
        return cust_id.get();
    }

    public void setCust_id(SimpleStringProperty cust_id) {
        this.cust_id = cust_id;
    }    
    
    
}
