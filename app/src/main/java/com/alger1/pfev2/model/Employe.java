package com.alger1.pfev2.model;

/**
 * Created by ISLEM-PC on 4/30/2018.
 */

public class Employe {
    private int id_employe;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private int mobile_nubmer;
    private String service;
    private String gender;
    private String birth_date;
    private String start_date;

    public Employe(){

    }

    public Employe(int id_employe, String username, String password, String firstname, String lastname, String email, int mobile_nubmer, String service, String gender, String birth_date, String start_date) {
        this.id_employe = id_employe;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.mobile_nubmer = mobile_nubmer;
        this.service = service;
        this.gender = gender;
        this.birth_date = birth_date;
        this.start_date = start_date;
    }

    /**
     * @return the id_employe
     */
    public int getId_employe() {
        return id_employe;
    }

    /**
     * @param id_employe the id_employe to set
     */
    public void setId_employe(int id_employe) {
        this.id_employe = id_employe;
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

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the mobile_nubmer
     */
    public int getMobile_nubmer() {
        return mobile_nubmer;
    }

    /**
     * @param mobile_nubmer the mobile_nubmer to set
     */
    public void setMobile_nubmer(int mobile_nubmer) {
        this.mobile_nubmer = mobile_nubmer;
    }

    /**
     * @return the service
     */
    public String getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(String service) {
        this.service = service;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the birth_date
     */
    public String getBirth_date() {
        return birth_date;
    }

    /**
     * @param birth_date the birth_date to set
     */
    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    /**
     * @return the start_date
     */
    public String getStart_date() {
        return start_date;
    }

    /**
     * @param start_date the start_date to set
     */
    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }
}

