package com.app.trekking;

public class ItemListView {
    private String linkImage;
    private String chuDe;
    private String noiDung;

    public ItemListView(String linkImage, String chuDe, String noiDung) {
        this.linkImage = linkImage;
        this.chuDe = chuDe;
        this.noiDung = noiDung;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public String getChuDe() {
        return chuDe;
    }

    public void setChuDe(String chuDe) {
        this.chuDe = chuDe;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
}
