package com.example.silviu086.licenta;

import java.io.Serializable;

/**
 * Created by Silviu086 on 25.05.2016.
 */
public class Calatorie implements Serializable {
    private int id;
    private String dataCreare;
    private String punctPlecare;
    private String punctSosire;
    private int pret;
    private String dataPlecare;
    private String oraPlecare;
    private int locuriDisponibile;
    private String marcaMasina;
    private String modelMasina;
    private int anFabricatie;
    private String experientaAuto;
    private String nivelConfort;
    private String marimeBagaj;
    private String durataCalatorie;
    private String distantaCalatorie;
    private String pasageriInAsteptare;
    private String pasageriConfirmati;

    public Calatorie(){

    }

    public String getDataCreare(){
        return this.dataCreare;
    }

    public void setDataCreare(String data){
        this.dataCreare = data;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getPunctPlecare() {
        return punctPlecare;
    }

    public void setPunctPlecare(String punctPlecare) {
        this.punctPlecare = punctPlecare;
    }

    public String getPunctSosire() {
        return punctSosire;
    }

    public void setPunctSosire(String punctSosire) {
        this.punctSosire = punctSosire;
    }

    public int getPret() {
        return pret;
    }

    public void setPret(int pret) {
        this.pret = pret;
    }

    public String getDataPlecare() {
        return dataPlecare;
    }

    public void setDataPlecare(String dataPlecare) {
        this.dataPlecare = dataPlecare;
    }

    public String getOraPlecare() {
        return oraPlecare;
    }

    public void setOraPlecare(String oraPlecare) {
        this.oraPlecare = oraPlecare;
    }

    public int getLocuriDisponibile() {
        return locuriDisponibile;
    }

    public void setLocuriDisponibile(int locuriDisponibile) {
        this.locuriDisponibile = locuriDisponibile;
    }

    public String getMarcaMasina() {
        return marcaMasina;
    }

    public void setMarcaMasina(String marcaMasina) {
        this.marcaMasina = marcaMasina;
    }

    public String getModelMasina() {
        return modelMasina;
    }

    public void setModelMasina(String modelMasina) {
        this.modelMasina = modelMasina;
    }

    public int getAnFabricatie() {
        return anFabricatie;
    }

    public void setAnFabricatie(int anFabricatie) {
        this.anFabricatie = anFabricatie;
    }

    public String getExperientaAuto() {
        return experientaAuto;
    }

    public void setExperientaAuto(String experientaAuto) {
        this.experientaAuto = experientaAuto;
    }

    public String getNivelConfort() {
        return nivelConfort;
    }

    public void setNivelConfort(String nivelConfort) {
        this.nivelConfort = nivelConfort;
    }

    public String getMarimeBagaj() {
        return marimeBagaj;
    }

    public void setMarimeBagaj(String marimeBagaj) {
        this.marimeBagaj = marimeBagaj;
    }

    public String getDurataCalatorie() {
        return durataCalatorie;
    }

    public void setDurataCalatorie(String durataCalatorie) {
        this.durataCalatorie = durataCalatorie;
    }

    public String getDistantaCalatorie() {
        return distantaCalatorie;
    }

    public void setDistantaCalatorie(String distantaCalatorie) {
        this.distantaCalatorie = distantaCalatorie;
    }


    public String getPasageriInAsteptare() {
        return pasageriInAsteptare;
    }

    public String getPasageriConfirmati() {
        return pasageriConfirmati;
    }

    public void setPasageriInAsteptare(String pasageriInAsteptare) {
        this.pasageriInAsteptare = pasageriInAsteptare;
    }

    public void setPasageriConfirmati(String pasageriConfirmati) {
        this.pasageriConfirmati = pasageriConfirmati;
    }
}
