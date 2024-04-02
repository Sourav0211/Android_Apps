package edu.uncc.giftlistapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {

    String pid,name,price_per_item,img_url;
    int count;



    public Product() {
    }

    public Product(String pid, int count, String name, String price_per_item, String img_url)
    {
        this.pid = pid;
        this.count = count;
        this.name = name;
        this.price_per_item = price_per_item;
        this.img_url = img_url;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice_per_item() {
        return price_per_item;
    }

    public void setPrice_per_item(String price_per_item) {
        this.price_per_item = price_per_item;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public double getTotalPrice()
    {
        if(count != 0)
        {
        double totalPrice = (double) count * (Double.parseDouble(price_per_item));
           return totalPrice;

        }

        return  Double.parseDouble(price_per_item);
    }
    

    @Override
    public String toString() {
        return "Product{" +
                "pid='" + pid + '\'' +
                ", name='" + name + '\'' +
                ", price_per_item='" + price_per_item + '\'' +
                ", img_url='" + img_url + '\'' +
                ", count=" + count +
                '}';
    }
}
