package com.example.mobil_but.models;

public class UserModel {

    String isim, eposta, parola;

    public UserModel() {
    }

    public UserModel(String isim, String eposta, String parola) {
        this.isim = isim;
        this.eposta = eposta;
        this.parola = parola;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getEposta() {
        return eposta;
    }

    public void setEposta(String eposta) {
        this.eposta = eposta;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }
}
