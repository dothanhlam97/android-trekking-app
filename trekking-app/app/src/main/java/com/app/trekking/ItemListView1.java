package com.app.trekking;


public class ItemListView1 {
    private String linkImage;
    private String noiDung;
    private String id;
    private int idImage;

    public ItemListView1(String id, String linkImage, String noiDung, int idImage) {
        this.linkImage = linkImage;
        this.noiDung = noiDung;
        this.id = id;
        this.idImage = idImage;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public String getId() {return id;}

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public int getIdImage() {return idImage;}
}
