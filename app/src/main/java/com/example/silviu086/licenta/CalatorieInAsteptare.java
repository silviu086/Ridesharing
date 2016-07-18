package com.example.silviu086.licenta;

import java.util.List;

/**
 * Created by Silviu086 on 02.06.2016.
 */
public class CalatorieInAsteptare {
    private Calatorie calatorie;
    private String dataCerere;
    private int idUtilizator;
    private String nume;
    private String email;
    private String telefon;
    private String varsta;

    public CalatorieInAsteptare(){
        calatorie = new Calatorie();

    }

    public String getDataCreare(){
        return calatorie.getDataCreare();
    }

    public void setDataCreare(String data){
        this.calatorie.setDataCreare(data);
    }

    public int getId(){
        return this.calatorie.getId();
    }

    public void setId(int id){
        this.calatorie.setId(id);
    }

    public String getPunctPlecare() {
        return this.calatorie.getPunctPlecare();
    }

    public void setPunctPlecare(String punctPlecare) {
        this.calatorie.setPunctPlecare(punctPlecare);
    }

    public String getPunctSosire() {
        return this.calatorie.getPunctSosire();
    }

    public void setPunctSosire(String punctSosire) {
        this.calatorie.setPunctSosire(punctSosire);
    }

    public int getPret() {
        return this.calatorie.getPret();
    }

    public void setPret(int pret) {
        this.calatorie.setPret(pret);
    }

    public String getDataPlecare() {
        return this.calatorie.getDataPlecare();
    }

    public void setDataPlecare(String dataPlecare) {
        this.calatorie.setDataPlecare(dataPlecare);
    }

    public String getOraPlecare() {
        return calatorie.getOraPlecare();
    }

    public void setOraPlecare(String oraPlecare) {
        this.calatorie.setOraPlecare(oraPlecare);
    }

    public int getLocuriDisponibile() {
        return calatorie.getLocuriDisponibile();
    }

    public void setLocuriDisponibile(int locuriDisponibile) {
        this.calatorie.setLocuriDisponibile(locuriDisponibile);
    }

    public String getMarcaMasina() {
        return calatorie.getMarcaMasina();
    }

    public void setMarcaMasina(String marcaMasina) {
        this.calatorie.setMarcaMasina(marcaMasina);
    }

    public String getModelMasina() {
        return calatorie.getModelMasina();
    }

    public void setModelMasina(String modelMasina) {
        this.calatorie.setModelMasina(modelMasina);
    }

    public int getAnFabricatie() {
        return calatorie.getAnFabricatie();
    }

    public void setAnFabricatie(int anFabricatie) {
        this.calatorie.setAnFabricatie(anFabricatie);
    }

    public String getExperientaAuto() {
        return calatorie.getExperientaAuto();
    }

    public void setExperientaAuto(String experientaAuto) {
        this.calatorie.setExperientaAuto(experientaAuto);
    }

    public String getNivelConfort() {
        return calatorie.getNivelConfort();
    }

    public void setNivelConfort(String nivelConfort) {
        this.calatorie.setNivelConfort(nivelConfort);
    }

    public String getMarimeBagaj() {
        return calatorie.getMarimeBagaj();
    }

    public void setMarimeBagaj(String marimeBagaj) {
        this.calatorie.setMarimeBagaj(marimeBagaj);
    }

    public String getDurataCalatorie() {
        return calatorie.getDurataCalatorie();
    }

    public void setDurataCalatorie(String durataCalatorie) {
        this.calatorie.setDurataCalatorie(durataCalatorie);
    }

    public String getDistantaCalatorie() {
        return calatorie.getDistantaCalatorie();
    }

    public void setDistantaCalatorie(String distantaCalatorie) {
        this.calatorie.setDistantaCalatorie(distantaCalatorie);
    }


    public String getPasageriInAsteptare() {
        return calatorie.getPasageriInAsteptare();
    }

    public String getPasageriConfirmati() {
        return calatorie.getPasageriConfirmati();
    }

    public void setPasageriInAsteptare(String pasageriInAsteptare) {
        this.calatorie.setPasageriInAsteptare(pasageriInAsteptare);
    }

    public void setPasageriConfirmati(String pasageriConfirmati) {
        this.calatorie.setPasageriConfirmati(pasageriConfirmati);
    }

    public String getDataCerere(){
        return this.dataCerere;
    }

    public void setDataCerere(String dataCerere){
        this.dataCerere = dataCerere;
    }

    public String getNume(){
        return this.nume;
    }

    public void setNume(String nume){
        this.nume = nume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public void setVarsta(String varsta) {
        this.varsta = varsta;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getVarsta() {
        return varsta;
    }

    public Calatorie getCalatorie(){
        return this.calatorie;
    }

    public int getIdUtilizator() {
        return idUtilizator;
    }

    public void setIdUtilizator(int idUtilizator) {
        this.idUtilizator = idUtilizator;
    }
}
