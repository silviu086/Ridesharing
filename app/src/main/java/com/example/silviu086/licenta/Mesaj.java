package com.example.silviu086.licenta;

import java.io.Serializable;

/**
 * Created by Silviu086 on 08.07.2016.
 */
public class Mesaj implements Serializable{
    private int id;
    private String mesaj;
    private int idAccount;
    private String nume;
    private String data;
    private int citit;

    public Mesaj(int id, String mesaj, int idAccount, String nume, String data, int citit) {
        this.id = id;
        this.mesaj = mesaj;
        this.idAccount = idAccount;
        this.nume = nume;
        this.data = data;
        this.citit = citit;
    }

    public int getId() {
        return id;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getCitit() {
        return citit;
    }

    public void setCitit(int citit) {
        this.citit = citit;
    }
}
