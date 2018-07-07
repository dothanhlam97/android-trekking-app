package com.app.trekking.controller;

public class ItemTour {
    private String dateCreated;
    private String tourName;
    private int id;
    private int numLocation;

    public ItemTour(String tourName, String dateCreated, int id) {
        this.id = id;
        this.tourName = tourName;
        this.dateCreated = dateCreated;
        this.numLocation = 0;
    }

    public String getTourName() {
        return this.tourName;
    }

    public String getDateCreated() {
        return this.dateCreated;
    }

    public int getId() {
//        Integer id = new Integer(this.id);
//        return id.toString();
        return this.id;
    }

}
