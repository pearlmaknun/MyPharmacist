package io.pearlmaknun.mypharmacist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Apoteker {

    @SerializedName("apoteker_id")
    @Expose
    private String apotekerId;
    @SerializedName("apoteker_stra")
    @Expose
    private String apotekerStra;
    @SerializedName("apoteker_sipa1")
    @Expose
    private String apotekerSipa1;
    @SerializedName("apotek_sipa1")
    @Expose
    private String apotekSipa1;
    @SerializedName("apoteker_sipa2")
    @Expose
    private String apotekerSipa2;
    @SerializedName("apotek_sipa2")
    @Expose
    private String apotekSipa2;
    @SerializedName("apoteker_sipa3")
    @Expose
    private String apotekerSipa3;
    @SerializedName("apotek_sipa3")
    @Expose
    private String apotekSipa3;
    @SerializedName("apoteker_name")
    @Expose
    private String apotekerName;
    @SerializedName("apoteker_email")
    @Expose
    private String apotekerEmail;
    @SerializedName("apoteker_number")
    @Expose
    private String apotekerNumber;
    @SerializedName("apoteker_address")
    @Expose
    private String apotekerAddress;
    @SerializedName("apoteker_photo")
    @Expose
    private String apotekerPhoto;
    @SerializedName("apoteker_password")
    @Expose
    private String apotekerPassword;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public String getApotekerId() {
        return apotekerId;
    }

    public void setApotekerId(String apotekerId) {
        this.apotekerId = apotekerId;
    }

    public String getApotekerStra() {
        return apotekerStra;
    }

    public void setApotekerStra(String apotekerStra) {
        this.apotekerStra = apotekerStra;
    }

    public String getApotekerSipa1() {
        return apotekerSipa1;
    }

    public void setApotekerSipa1(String apotekerSipa1) {
        this.apotekerSipa1 = apotekerSipa1;
    }

    public String getApotekSipa1() {
        return apotekSipa1;
    }

    public void setApotekSipa1(String apotekSipa1) {
        this.apotekSipa1 = apotekSipa1;
    }

    public String getApotekerSipa2() {
        return apotekerSipa2;
    }

    public void setApotekerSipa2(String apotekerSipa2) {
        this.apotekerSipa2 = apotekerSipa2;
    }

    public String getApotekSipa2() {
        return apotekSipa2;
    }

    public void setApotekSipa2(String apotekSipa2) {
        this.apotekSipa2 = apotekSipa2;
    }

    public String getApotekerSipa3() {
        return apotekerSipa3;
    }

    public void setApotekerSipa3(String apotekerSipa3) {
        this.apotekerSipa3 = apotekerSipa3;
    }

    public String getApotekSipa3() {
        return apotekSipa3;
    }

    public void setApotekSipa3(String apotekSipa3) {
        this.apotekSipa3 = apotekSipa3;
    }

    public String getApotekerName() {
        return apotekerName;
    }

    public void setApotekerName(String apotekerName) {
        this.apotekerName = apotekerName;
    }

    public String getApotekerEmail() {
        return apotekerEmail;
    }

    public void setApotekerEmail(String apotekerEmail) {
        this.apotekerEmail = apotekerEmail;
    }

    public String getApotekerNumber() {
        return apotekerNumber;
    }

    public void setApotekerNumber(String apotekerNumber) {
        this.apotekerNumber = apotekerNumber;
    }

    public String getApotekerAddress() {
        return apotekerAddress;
    }

    public void setApotekerAddress(String apotekerAddress) {
        this.apotekerAddress = apotekerAddress;
    }

    public String getApotekerPhoto() {
        return apotekerPhoto;
    }

    public void setApotekerPhoto(String apotekerPhoto) {
        this.apotekerPhoto = apotekerPhoto;
    }

    public String getApotekerPassword() {
        return apotekerPassword;
    }

    public void setApotekerPassword(String apotekerPassword) {
        this.apotekerPassword = apotekerPassword;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
