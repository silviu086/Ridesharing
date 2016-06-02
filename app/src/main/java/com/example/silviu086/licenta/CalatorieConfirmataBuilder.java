package com.example.silviu086.licenta;

/**
 * Created by Silviu086 on 02.06.2016.
 */
public class CalatorieConfirmataBuilder implements IBuilder {
    private CalatorieConfirmata calatorie;

    public CalatorieConfirmataBuilder(){
        calatorie = new CalatorieConfirmata();
    }


    public CalatorieConfirmataBuilder setDataCreare(String data){
        this.calatorie.setDataCreare(data);
        return this;
    }

    public CalatorieConfirmataBuilder setId(int id){
        this.calatorie.setId(id);
        return this;
    }

    public CalatorieConfirmataBuilder setPunctPlecare(String punctPlecare) {
        this.calatorie.setPunctPlecare(punctPlecare);
        return this;
    }

    public CalatorieConfirmataBuilder setPunctSosire(String punctSosire) {
        this.calatorie.setPunctSosire(punctSosire);
        return this;
    }

    public CalatorieConfirmataBuilder setPret(int pret) {
        this.calatorie.setPret(pret);
        return this;
    }

    public CalatorieConfirmataBuilder setDataPlecare(String dataPlecare) {
        this.calatorie.setDataPlecare(dataPlecare);
        return this;
    }

    public CalatorieConfirmataBuilder setOraPlecare(String oraPlecare) {
        this.calatorie.setOraPlecare(oraPlecare);
        return this;
    }

    public CalatorieConfirmataBuilder setLocuriDisponibile(int locuriDisponibile) {
        this.calatorie.setLocuriDisponibile(locuriDisponibile);
        return this;
    }


    public CalatorieConfirmataBuilder setMarcaMasina(String marcaMasina) {
        this.calatorie.setMarcaMasina(marcaMasina);
        return this;
    }

    public CalatorieConfirmataBuilder setModelMasina(String modelMasina) {
        this.calatorie.setModelMasina(modelMasina);
        return this;
    }

    public CalatorieConfirmataBuilder setAnFabricatie(int anFabricatie) {
        this.calatorie.setAnFabricatie(anFabricatie);
        return this;
    }

    public CalatorieConfirmataBuilder setExperientaAuto(String experientaAuto) {
        this.calatorie.setExperientaAuto(experientaAuto);
        return this;
    }

    public CalatorieConfirmataBuilder setNivelConfort(String nivelConfort) {
        this.calatorie.setNivelConfort(nivelConfort);
        return this;
    }

    public CalatorieConfirmataBuilder setMarimeBagaj(String marimeBagaj) {
        this.calatorie.setMarimeBagaj(marimeBagaj);
        return this;
    }

    public CalatorieConfirmataBuilder setDurataCalatorie(String durataCalatorie) {
        this.calatorie.setDurataCalatorie(durataCalatorie);
        return this;
    }

    public CalatorieConfirmataBuilder setDistantaCalatorie(String distantaCalatorie) {
        this.calatorie.setDistantaCalatorie(distantaCalatorie);
        return this;
    }

    public CalatorieConfirmataBuilder setPasageriInAsteptare(String pasageriInAsteptare) {
        this.calatorie.setPasageriInAsteptare(pasageriInAsteptare);
        return this;
    }

    public CalatorieConfirmataBuilder setPasageriConfirmati(String pasageriConfirmati) {
        this.calatorie.setPasageriConfirmati(pasageriConfirmati);
        return this;
    }

    @Override
    public CalatorieConfirmata build() {
        return calatorie;
    }
}
