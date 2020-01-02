package io.pearlmaknun.mypharmacist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Konsultasi implements Serializable {

    @SerializedName("chat_id")
    @Expose
    private String chatId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("apoteker_id")
    @Expose
    private String apotekerId;
    @SerializedName("waktu_pengajuan")
    @Expose
    private String waktuPengajuan;
    @SerializedName("start_chat")
    @Expose
    private String startChat;
    @SerializedName("update_chat")
    @Expose
    private String updateChat;
    @SerializedName("end_chat")
    @Expose
    private String endChat;
    @SerializedName("chat_duration")
    @Expose
    private String chatDuration;
    @SerializedName("status_chat")
    @Expose
    private String statusChat;
    @SerializedName("rating_star")
    @Expose
    private String ratingStar;
    @SerializedName("rating_comment")
    @Expose
    private String ratingComment;
    @SerializedName("rating_time")
    @Expose
    private String ratingTime;
    @SerializedName("apoteker_name")
    @Expose
    private String apotekerName;
    @SerializedName("user_name")
    @Expose
    private String userName;

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApotekerId() {
        return apotekerId;
    }

    public void setApotekerId(String apotekerId) {
        this.apotekerId = apotekerId;
    }

    public String getWaktuPengajuan() {
        return waktuPengajuan;
    }

    public void setWaktuPengajuan(String waktuPengajuan) {
        this.waktuPengajuan = waktuPengajuan;
    }

    public String getStartChat() {
        return startChat;
    }

    public void setStartChat(String startChat) {
        this.startChat = startChat;
    }

    public String getUpdateChat() {
        return updateChat;
    }

    public void setUpdateChat(String updateChat) {
        this.updateChat = updateChat;
    }

    public String getEndChat() {
        return endChat;
    }

    public void setEndChat(String endChat) {
        this.endChat = endChat;
    }

    public String getChatDuration() {
        return chatDuration;
    }

    public void setChatDuration(String chatDuration) {
        this.chatDuration = chatDuration;
    }

    public String getStatusChat() {
        return statusChat;
    }

    public void setStatusChat(String statusChat) {
        this.statusChat = statusChat;
    }

    public String getRatingStar() {
        return ratingStar;
    }

    public void setRatingStar(String ratingStar) {
        this.ratingStar = ratingStar;
    }

    public String getRatingComment() {
        return ratingComment;
    }

    public void setRatingComment(String ratingComment) {
        this.ratingComment = ratingComment;
    }

    public String getRatingTime() {
        return ratingTime;
    }

    public void setRatingTime(String ratingTime) {
        this.ratingTime = ratingTime;
    }

    public String getApotekerName() {
        return apotekerName;
    }

    public void setApotekerName(String apotekerName) {
        this.apotekerName = apotekerName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
