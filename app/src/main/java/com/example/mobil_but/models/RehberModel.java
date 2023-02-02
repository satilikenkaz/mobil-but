package com.example.mobil_but.models;

public class RehberModel {
    String isim, numara, resim;

    public RehberModel() {
    }

    public RehberModel(String isim, String numara, String resim) {
        this.isim = isim;
        this.numara = numara;
        this.resim = resim;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getNumara() {
        return numara;
    }

    public void setNumara(String numara) {
        this.numara = numara;
    }

    public String getResim() {
        return resim;
    }

    public void setResim(String resim) {
        this.resim = resim;
    }
}
