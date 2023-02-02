package com.example.mobil_but.models;

import java.util.List;

public class groupModel {
    String grupadi;
    String grupaciklamasi;
    String gruplogo_url;
    String grupid;
    List<String> numbers;

    public groupModel() {
    }

    public groupModel(String grupadi, String grupaciklamasi, String gruplogo_url, List<String> numbers,String grupid) {
        this.grupadi = grupadi;
        this.grupaciklamasi = grupaciklamasi;
        this.gruplogo_url = gruplogo_url;
        this.grupid = grupid;
        this.numbers = numbers;
    }

    public String getGrupadi() {
        return grupadi;
    }

    public void setGrupadi(String grupadi) {
        this.grupadi = grupadi;
    }

    public String getGrupaciklamasi() {
        return grupaciklamasi;
    }

    public void setGrupaciklamasi(String grupaciklamasi) {
        this.grupaciklamasi = grupaciklamasi;
    }

    public String getGruplogo_url() {
        return gruplogo_url;
    }

    public void setGruplogo_url(String gruplogo_url) {
        this.gruplogo_url = gruplogo_url;
    }

    public String getGrupid() {
        return grupid;
    }

    public void setGrupid(String grupid) {
        this.grupid = grupid;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }
}
