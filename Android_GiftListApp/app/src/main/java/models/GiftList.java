package models;

import java.util.ArrayList;

public class GiftList{
    public String docId;
    public String userName;
    public String userId;
    public String giftListName;
    public String progress;
    public double totalAmount;
    public double amountPledged;
    public int numberOfItem;

    public ArrayList<String> selectedTags = new ArrayList<>();

    public GiftList(String docId, String userName, String userId, String giftListName,
                    String progress, double totalAmount, int numberOfItem, double amountPledged,
                    ArrayList<String> selectedTags) {
        this.docId = docId;
        this.userName = userName;
        this.userId = userId;
        this.giftListName = giftListName;
        this.progress = progress;
        this.totalAmount = totalAmount;
        this.numberOfItem = numberOfItem;
        this.selectedTags = selectedTags;
        this.amountPledged = amountPledged;

    }

    public double getAmountPledged() {
        return amountPledged;
    }

    public void setAmountPledged(double amountPledged) {
        this.amountPledged = amountPledged;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGiftListName() {
        return giftListName;
    }

    public void setGiftListName(String giftListName) {
        this.giftListName = giftListName;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getNumberOfItem() {
        return numberOfItem;
    }

    public void setNumberOfItem(int numberOfItem) {
        this.numberOfItem = numberOfItem;
    }

    public ArrayList<String> getSelectedTags() {
        return selectedTags;
    }

    public void setSelectedTags(ArrayList<String> tags) {
        this.selectedTags = tags;
    }

    public GiftList() {
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    @Override
    public String toString() {
        return "GiftList{" +
                "docId='" + docId + '\'' +
                ", userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                ", giftListName='" + giftListName + '\'' +
                ", progress='" + progress + '\'' +
                ", totalAmount=" + totalAmount +
                ", amountPledged=" + amountPledged +
                ", numberOfItem=" + numberOfItem +
                ", selectedTags=" + selectedTags +
                '}';
    }
}
