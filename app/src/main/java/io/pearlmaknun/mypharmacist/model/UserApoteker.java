package io.pearlmaknun.mypharmacist.model;

public class UserApoteker {

    String id;
    String username;
    String apoteker_id;

    public UserApoteker(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getApoteker_id() {
        return apoteker_id;
    }

    public void setApoteker_id(String apoteker_id) {
        this.apoteker_id = apoteker_id;
    }

    public UserApoteker() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
