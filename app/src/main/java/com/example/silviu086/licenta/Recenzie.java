package com.example.silviu086.licenta;

import java.io.Serializable;

/**
 * Created by Silviu086 on 11.07.2016.
 */
public class Recenzie implements Serializable {
    private int id;
    private String nume;
    private String data;
    private float scor;
    private String descriere;

    public Recenzie(int id, String nume, String data, float scor, String descriere) {
        this.id = id;
        this.nume = nume;
        this.data = data;
        this.scor = scor;
        this.descriere = descriere;
    }

    public int getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public float getScor() {
        return scor;
    }

    public String getDescriere() {
        return descriere;
    }

    public String getNume() {
        return nume;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setScor(float scor) {
        this.scor = scor;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
}
