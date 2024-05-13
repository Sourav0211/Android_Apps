package com.example.assignment11.models;

import com.google.firebase.Timestamp;

import java.io.Serializable;

public class MessageItem implements Serializable {

    String messageTitle;
    String messageText;
    String senderName;
    String receiverName;
    String seen;

    String replyTo;
    String SenderId;
    String ReceiverId;
    String DocId;
    String RecDocId;
    String SendDocId;
Timestamp createdAt;


    public MessageItem() {
    }

    public MessageItem(String messageTitle, String messageText, String senderName, String receiverName,
                       String seen, String replyTo, String senderId, String receiverId,
                       String docId, Timestamp createdAt) {
        this.messageTitle = messageTitle;
        this.messageText = messageText;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.seen = seen;
        this.replyTo = replyTo;
        SenderId = senderId;
        ReceiverId = receiverId;
        DocId = docId;
        this.createdAt = createdAt;
    }

    public String getRecDocId() {
        return RecDocId;
    }

    public void setRecDocId(String recDocId) {
        RecDocId = recDocId;
    }

    public String getSendDocId() {
        return SendDocId;
    }

    public void setSendDocId(String sendDocId) {
        SendDocId = sendDocId;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getSenderId() {
        return SenderId;
    }

    public void setSenderId(String senderId) {
        SenderId = senderId;
    }

    public String getReceiverId() {
        return ReceiverId;
    }

    public void setReceiverId(String receiverId) {
        ReceiverId = receiverId;
    }

    public String getDocId() {
        return DocId;
    }

    public void setDocId(String docId) {
        DocId = docId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "MessageItem{" +
                "messageTitle='" + messageTitle + '\'' +
                ", messageText='" + messageText + '\'' +
                ", senderName='" + senderName + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", seen='" + seen + '\'' +
                ", replyTo='" + replyTo + '\'' +
                ", SenderId='" + SenderId + '\'' +
                ", ReceiverId='" + ReceiverId + '\'' +
                ", DocId='" + DocId + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
