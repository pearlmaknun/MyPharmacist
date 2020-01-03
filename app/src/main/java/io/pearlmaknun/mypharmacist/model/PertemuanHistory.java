package io.pearlmaknun.mypharmacist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PertemuanHistory {

    @SerializedName("pertemuan_id")
    @Expose
    private String pertemuanId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("apoteker_id")
    @Expose
    private String apotekerId;
    @SerializedName("konsultasi_id")
    @Expose
    private String konsultasiId;
    @SerializedName("pertemuan_waktu")
    @Expose
    private String pertemuanWaktu;
    @SerializedName("pertemuan_alamat")
    @Expose
    private String pertemuanAlamat;
    @SerializedName("pertemuan_lokasi")
    @Expose
    private String pertemuanLokasi;
    @SerializedName("pertemuan_detail")
    @Expose
    private String pertemuanDetail;
    @SerializedName("pertemuan_status")
    @Expose
    private String pertemuanStatus;
    @SerializedName("ttd_apoteker")
    @Expose
    private String ttdApoteker;
    @SerializedName("ttd_konseli")
    @Expose
    private String ttdKonseli;
    @SerializedName("apoteker_stra")
    @Expose
    private String apotekerStra;
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
    @SerializedName("user_name")
    @Expose
    private String userName;

    public String getPertemuanId() {
        return pertemuanId;
    }

    public void setPertemuanId(String pertemuanId) {
        this.pertemuanId = pertemuanId;
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

    public String getKonsultasiId() {
        return konsultasiId;
    }

    public void setKonsultasiId(String konsultasiId) {
        this.konsultasiId = konsultasiId;
    }

    public String getPertemuanWaktu() {
        return pertemuanWaktu;
    }

    public void setPertemuanWaktu(String pertemuanWaktu) {
        this.pertemuanWaktu = pertemuanWaktu;
    }

    public String getPertemuanAlamat() {
        return pertemuanAlamat;
    }

    public void setPertemuanAlamat(String pertemuanAlamat) {
        this.pertemuanAlamat = pertemuanAlamat;
    }

    public String getPertemuanLokasi() {
        return pertemuanLokasi;
    }

    public void setPertemuanLokasi(String pertemuanLokasi) {
        this.pertemuanLokasi = pertemuanLokasi;
    }

    public String getPertemuanDetail() {
        return pertemuanDetail;
    }

    public void setPertemuanDetail(String pertemuanDetail) {
        this.pertemuanDetail = pertemuanDetail;
    }

    public String getPertemuanStatus() {
        return pertemuanStatus;
    }

    public void setPertemuanStatus(String pertemuanStatus) {
        this.pertemuanStatus = pertemuanStatus;
    }

    public String getTtdApoteker() {
        return ttdApoteker;
    }

    public void setTtdApoteker(String ttdApoteker) {
        this.ttdApoteker = ttdApoteker;
    }

    public String getTtdKonseli() {
        return ttdKonseli;
    }

    public void setTtdKonseli(String ttdKonseli) {
        this.ttdKonseli = ttdKonseli;
    }

    public String getApotekerStra() {
        return apotekerStra;
    }

    public void setApotekerStra(String apotekerStra) {
        this.apotekerStra = apotekerStra;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
