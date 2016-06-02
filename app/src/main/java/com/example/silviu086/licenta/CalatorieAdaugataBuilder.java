package com.example.silviu086.licenta;

import java.util.List;

/**
 * Created by Silviu086 on 02.06.2016.
 */
public class CalatorieAdaugataBuilder implements IBuilder {
    private CalatorieAdaugata calatorie;

    public CalatorieAdaugataBuilder(){
        calatorie = new CalatorieAdaugata();
    }


    public CalatorieAdaugataBuilder setDataCreare(String data){
        this.calatorie.setDataCreare(data);
        return this;
    }

    public CalatorieAdaugataBuilder setId(int id){
        this.calatorie.setId(id);
        return this;
    }

    public CalatorieAdaugataBuilder setPunctPlecare(String punctPlecare) {
        this.calatorie.setPunctPlecare(punctPlecare);
        return this;
    }

    public CalatorieAdaugataBuilder setPunctSosire(String punctSosire) {
        this.calatorie.setPunctSosire(punctSosire);
        return this;
    }

    public CalatorieAdaugataBuilder setPret(int pret) {
        this.calatorie.setPret(pret);
        return this;
    }

    public CalatorieAdaugataBuilder setDataPlecare(String dataPlecare) {
        this.calatorie.setDataPlecare(dataPlecare);
        return this;
    }

    public CalatorieAdaugataBuilder setOraPlecare(String oraPlecare) {
        this.calatorie.setOraPlecare(oraPlecare);
        return this;
    }

    public CalatorieAdaugataBuilder setLocuriDisponibile(int locuriDisponibile) {
        this.calatorie.setLocuriDisponibile(locuriDisponibile);
        return this;
    }


    public CalatorieAdaugataBuilder setMarcaMasina(String marcaMasina) {
        this.calatorie.setMarcaMasina(marcaMasina);
        return this;
    }

    public CalatorieAdaugataBuilder setModelMasina(String modelMasina) {
        this.calatorie.setModelMasina(modelMasina);
        return this;
    }

    public CalatorieAdaugataBuilder setAnFabricatie(int anFabricatie) {
        this.calatorie.setAnFabricatie(anFabricatie);
        return this;
    }

    public CalatorieAdaugataBuilder setExperientaAuto(String experientaAuto) {
        this.calatorie.setExperientaAuto(experientaAuto);
        return this;
    }

    public CalatorieAdaugataBuilder setNivelConfort(String nivelConfort) {
        this.calatorie.setNivelConfort(nivelConfort);
        return this;
    }

    public CalatorieAdaugataBuilder setMarimeBagaj(String marimeBagaj) {
        this.calatorie.setMarimeBagaj(marimeBagaj);
        return this;
    }

    public CalatorieAdaugataBuilder setDurataCalatorie(String durataCalatorie) {
        this.calatorie.setDurataCalatorie(durataCalatorie);
        return this;
    }

    public CalatorieAdaugataBuilder setDistantaCalatorie(String distantaCalatorie) {
        this.calatorie.setDistantaCalatorie(distantaCalatorie);
        return this;
    }

    public CalatorieAdaugataBuilder setPasageriInAsteptare(String pasageriInAsteptare) {
        this.calatorie.setPasageriInAsteptare(pasageriInAsteptare);
        return this;
    }

    public CalatorieAdaugataBuilder setPasageriConfirmati(String pasageriConfirmati) {
        this.calatorie.setPasageriConfirmati(pasageriConfirmati);
        return this;
    }

    @Override
    public CalatorieAdaugata build() {
        return calatorie;
    }
}
