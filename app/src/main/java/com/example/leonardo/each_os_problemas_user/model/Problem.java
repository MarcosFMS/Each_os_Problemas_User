package com.example.leonardo.each_os_problemas_user.model;

/**
 * Created by Marcos on 09/11/2017.
 */

public class Problem {

    private String id;
    private String description;
    private String user;
    private int type;
    private int status;
    private String place;
    private String startDate;
    private String imgLink;
    private String endDate;

    public Problem(String id, String description, String user, int type, int status, String place, String startDate, String imgLink) {
        this.id = id;
        this.description = description;
        this.user = user;
        this.type = type;
        this.status = status;
        this.place = place;
        this.startDate = startDate;
        this.imgLink = imgLink;
    }

    public Problem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString(){
        return this.description;
    }
}
