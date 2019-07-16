package io.pearlmaknun.mypharmacist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Apotek {

    @SerializedName("apotik_id")
    @Expose
    private String apotikId;
    @SerializedName("apotik_name")
    @Expose
    private String apotikName;
    @SerializedName("apotik_telp")
    @Expose
    private String apotikTelp;
    @SerializedName("apotik_address")
    @Expose
    private String apotikAddress;
    @SerializedName("apotik_latitude")
    @Expose
    private String apotikLatitude;
    @SerializedName("apotik_longitude")
    @Expose
    private String apotikLongitude;
    @SerializedName("apotik_ni")
    @Expose
    private String apotikNi;
    @SerializedName("jarak")
    @Expose
    private String jarak;

    public String getApotikId() {
        return apotikId;
    }

    public void setApotikId(String apotikId) {
        this.apotikId = apotikId;
    }

    public String getApotikName() {
        return apotikName;
    }

    public void setApotikName(String apotikName) {
        this.apotikName = apotikName;
    }

    public String getApotikTelp() {
        return apotikTelp;
    }

    public void setApotikTelp(String apotikTelp) {
        this.apotikTelp = apotikTelp;
    }

    public String getApotikAddress() {
        return apotikAddress;
    }

    public void setApotikAddress(String apotikAddress) {
        this.apotikAddress = apotikAddress;
    }

    public String getApotikLatitude() {
        return apotikLatitude;
    }

    public void setApotikLatitude(String apotikLatitude) {
        this.apotikLatitude = apotikLatitude;
    }

    public String getApotikLongitude() {
        return apotikLongitude;
    }

    public void setApotikLongitude(String apotikLongitude) {
        this.apotikLongitude = apotikLongitude;
    }

    public String getApotikNi() {
        return apotikNi;
    }

    public void setApotikNi(String apotikNi) {
        this.apotikNi = apotikNi;
    }

    public String getJarak() {
        return jarak;
    }

    public void setJarak(String jarak) {
        this.jarak = jarak;
    }

}
