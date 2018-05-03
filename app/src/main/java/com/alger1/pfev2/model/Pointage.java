package com.alger1.pfev2.model;

/**
 * Created by ISLEM-PC on 4/30/2018.
 */

public class Pointage {

    private int id_pointage;
    private double longitude;
    private double latitude;
    private String time;
    private String date;
    private String type;
    private String username;

    public Pointage(){

    }

    public Pointage(double longitude, double latitude, String time, String date, String type, String username) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.time = time;
        this.date = date;
        this.type = type;
        this.username = username;
    }

    /**
     * @return the id_pointage
     */
    public int getId_pointage() {
        return id_pointage;
    }

    /**
     * @param id_pointage the id_pointage to set
     */
    public void setId_pointage(int id_pointage) {
        this.id_pointage = id_pointage;
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

}
