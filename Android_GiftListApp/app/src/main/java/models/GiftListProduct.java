package models;

import java.util.ArrayList;

public class GiftListProduct {
    public String docId;

    public ArrayList<String> pledgedBy = new ArrayList<>();

    public String pid, name, img_url;
    public double price;


    public GiftListProduct() {
    }

    public GiftListProduct(String docId, ArrayList<String> pledgedBy, String pid, String name, String img_url, double price) {
        this.docId = docId;
        this.pledgedBy = pledgedBy;
        this.pid = pid;
        this.name = name;
        this.img_url = img_url;
        this.price = price;
    }

    public ArrayList<String> getPledgedBy() {
        return pledgedBy;
    }

    public void setPledgedBy(ArrayList<String> pledgedBy) {
        this.pledgedBy = pledgedBy;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}
