package com.example.silviu086.licenta;

/**
 * Created by Silviu086 on 25.05.2016.
 */
public class CalatorieBuilder implements IBuilder {

    private Calatorie calatorie;

    public CalatorieBuilder(){
        this.calatorie = new Calatorie();
    }


    public CalatorieBuilder setId(int id){
        this.calatorie.setId(id);
        return this;
    }

    public CalatorieBuilder setDataCreare(String data){
        this.calatorie.setDataCreare(data);
        return this;
    }

    public CalatorieBuilder setPunctPlecare(String punctPlecare) {
        this.calatorie.setPunctPlecare(punctPlecare);
        return this;
    }

    public CalatorieBuilder setPunctSosire(String punctSosire) {
        this.calatorie.setPunctSosire(punctSosire);
        return this;
    }

    public CalatorieBuilder setPret(int pret) {
        this.calatorie.setPret(pret);
        return this;
    }

    public CalatorieBuilder setDataPlecare(String dataPlecare) {
        this.calatorie.setDataPlecare(dataPlecare);
        return this;
    }

    public CalatorieBuilder setOraPlecare(String oraPlecare) {
        this.calatorie.setOraPlecare(oraPlecare);
        return this;
    }

    public CalatorieBuilder setLocuriDisponibile(int locuriDisponibile) {
        this.calatorie.setLocuriDisponibile(locuriDisponibile);
        return this;
    }

    public CalatorieBuilder setMarcaMasina(String marcaMasina) {
        this.calatorie.setMarcaMasina(marcaMasina);
        return this;
    }

    public CalatorieBuilder setModelMasina(String modelMasina) {
        this.calatorie.setModelMasina(modelMasina);
        return this;
    }

    public CalatorieBuilder setAnFabricatie(int anFabricatie) {
        this.calatorie.setAnFabricatie(anFabricatie);
        return this;
    }


    public CalatorieBuilder setExperientaAuto(String experientaAuto) {
        this.calatorie.setExperientaAuto(experientaAuto);
        return this;
    }

    public CalatorieBuilder setNivelConfort(String nivelConfort) {
        this.calatorie.setNivelConfort(nivelConfort);
        return this;
    }


    public CalatorieBuilder setMarimeBagaj(String marimeBagaj) {
        this.calatorie.setMarimeBagaj(marimeBagaj);
        return this;
    }

    public CalatorieBuilder setDurataCalatorie(String durataCalatorie) {
        this.calatorie.setDurataCalatorie(durataCalatorie);
        return this;
    }

    public CalatorieBuilder setDistantaCalatorie(String distantaCalatorie) {
        this.calatorie.setDistantaCalatorie(distantaCalatorie);
        return this;
    }


    public CalatorieBuilder setPasageriInAsteptare(String pasageriInAsteptare){
        this.calatorie.setPasageriInAsteptare(pasageriInAsteptare);
        return this;
    }

    public CalatorieBuilder setPasageriConfirmati(String pasageriConfirmati){
        this.calatorie.setPasageriConfirmati(pasageriConfirmati);
        return this;
    }

    @Override
    public Calatorie build() {
        return calatorie;
    }
}
