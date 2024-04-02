package edu.uncc.giftlistapp;

import java.io.Serializable;
import java.util.ArrayList;

public class GiftList implements Serializable {

    String name, gid;
    ArrayList<Product> products;



    public GiftList() {
    }

    public GiftList(String name, String gid, ArrayList<Product> products)
    {
        this.name = name;
        this.gid = gid;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }


    public int getTotalCount(){
        int totalCount=0;
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            totalCount = totalCount + product.getCount();

        }
        return totalCount;
    }

    public double getTotalCost()
    {
        double totalCost=0.0;
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            totalCost = totalCost + product.getCount() * Double.parseDouble(product.getPrice_per_item());
        }
        return totalCost;
    }

    @Override
    public String toString() {
        return "GiftList{" +
                "name='" + name + '\'' +
                ", gid='" + gid + '\'' +
                ", products=" + products +
                '}';
    }
}
