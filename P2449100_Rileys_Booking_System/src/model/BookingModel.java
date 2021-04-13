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
public class BookingModel {
    private SimpleStringProperty activity, periodofday, duration, time, date;

    public BookingModel(String activity, String periodofday, String duration, String time, String date) {
        this.activity = new SimpleStringProperty(activity);
        this.periodofday = new SimpleStringProperty(periodofday);
        this.duration = new SimpleStringProperty(duration);
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

    
    
    
    
    
    
    
}
