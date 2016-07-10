package com.example.silviu086.licenta;

import java.io.Serializable;

/**
 * Created by Silviu086 on 24.03.2016.
 */
public class Account implements Serializable {
    private int id;
    private String email;
    private String parola;
    private String dateCreated;
    private String nume;
    private String telefon;
    private int varsta;
    private String marcaMasina;
    private String modelMasina;
    private int anFabricatie;
    private String experientaAuto;
    private boolean haveProfilPhoto;

    public Account() {
        this.email = "";
        this.parola = "";
        this.nume = "";
        this.telefon = "";
        this.varsta = 0;
        this.marcaMasina = "";
        this.modelMasina = "";
        this.anFabricatie = 0;
        this.experientaAuto = "";
    }
    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getDateCreated() { return dateCreated; }

    public void setDateCreated(String dateCreated) { this.dateCreated = dateCreated; }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getTelefon() { return telefon; }

    public void setTelefon(String telefon) { this.telefon = telefon; }

    public int getVarsta() { return varsta; }

    public void setVarsta(int varsta) { this.varsta = varsta; }

    public String getMarcaMasina() { return marcaMasina; }

    public void setMarcaMasina(String marcaMasina) { this.marcaMasina = marcaMasina; }

    public String getModelMasina() {
        return modelMasina;
    }

    public void setModelMasina(String modelMasina) {
        this.modelMasina = modelMasina;
    }

    public int getAnFabricatie() { return anFabricatie; }

    public void setAnFabricatie(int anFabricatie) { this.anFabricatie = anFabricatie; }

    public String getExperientaAuto() {
        return experientaAuto;
    }

    public void setExperientaAuto(String experientaAuto) {
        this.experientaAuto = experientaAuto;
    }

    @Override
    public String toString() {
        return email + ", " + dateCreated + ", " + nume + ", " + telefon + ", " + marcaMasina;
    }

    public boolean isHaveProfilPhoto() {
        return haveProfilPhoto;
    }

    public void setHaveProfilPhoto(boolean haveProfilPhoto) {
        this.haveProfilPhoto = haveProfilPhoto;
    }
}
