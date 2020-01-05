package io.pearlmaknun.mypharmacist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApotekerResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Apoteker data;
    @SerializedName("apotik")
    @Expose
    private List<Apotek> apotik = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Apoteker getData() {
        return data;
    }

    public void setData(Apoteker data) {
        this.data = data;
    }

    public List<Apotek> getApotik() {
        return apotik;
    }

    public void setApotik(List<Apotek> apotik) {
        this.apotik = apotik;
    }
}
