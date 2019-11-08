package io.pearlmaknun.mypharmacist.data;

public class Constan {

    //public static final String BASE_URL = "http://localhost:8080";
    public static final String BASE_URL = "http://anaksulung.id/public";
    public static final String BASE_URL_USER = BASE_URL + "/api";

    //auth
    public static final String REGISTER = BASE_URL + "/register";
    public static final String LOGIN = BASE_URL + "/login";
    public static final String LOGOUT = BASE_URL_USER + "/logout";

    //base
    public static final String PROFIL = BASE_URL_USER + "/profile";

    //nearest apotek
    public static final String NEAREST_APOTEK = BASE_URL_USER + "/apotek";

    //nearest apoteker
    public static final String NEAREST_APOTEKER = BASE_URL_USER + "/apoteker";

    //konsul to apoteker
    public static final String CHECK_HAS_CONSULTATION = BASE_URL_USER + "/statuscon";
    public static final String REQUEST_CONSULTATION = BASE_URL_USER + "/request";
    public static final String END_CHAT = BASE_URL_USER + "/endchat/";
    public static final String REPORT = BASE_URL_USER + "/report/";
    public static final String RATE_CHAT = BASE_URL_USER + "/ratechat/";

    //status chat
    public static final String DIPROSES = "0";
    public static final String BERLANGSUNG = "1";
    public static final String DITOLAK = "2";
    public static final String EXPIRED = "3";
    public static final String ENDED = "4";
}

