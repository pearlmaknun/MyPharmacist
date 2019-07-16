package io.pearlmaknun.mypharmacist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestConsultation {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status_konsultasi")
    @Expose
    private Integer statusKonsultasi;

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

    public Integer getStatusKonsultasi() {
        return statusKonsultasi;
    }

    public void setStatusKonsultasi(Integer statusKonsultasi) {
        this.statusKonsultasi = statusKonsultasi;
    }
}
