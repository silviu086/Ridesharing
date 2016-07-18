package com.example.silviu086.licenta;

/**
 * Created by Silviu086 on 02.06.2016.
 */
public class CalatorieInAsteptareBuilder implements IBuilder {
    private CalatorieInAsteptare calatorie;

    public CalatorieInAsteptareBuilder(){
        calatorie = new CalatorieInAsteptare();
    }


    public CalatorieInAsteptareBuilder setDataCreare(String data){
        this.calatorie.setDataCreare(data);
        return this;
    }

    public CalatorieInAsteptareBuilder setId(int id){
        this.calatorie.setId(id);
        return this;
    }

    public CalatorieInAsteptareBuilder setPunctPlecare(String punctPlecare) {
        this.calatorie.setPunctPlecare(punctPlecare);
        return this;
    }

    public CalatorieInAsteptareBuilder setPunctSosire(String punctSosire) {
        this.calatorie.setPunctSosire(punctSosire);
        return this;
    }

    public CalatorieInAsteptareBuilder setPret(int pret) {
        this.calatorie.setPret(pret);
        return this;
    }

    public CalatorieInAsteptareBuilder setDataPlecare(String dataPlecare) {
        this.calatorie.setDataPlecare(dataPlecare);
        return this;
    }

    public CalatorieInAsteptareBuilder setOraPlecare(String oraPlecare) {
        this.calatorie.setOraPlecare(oraPlecare);
        return this;
    }

    public CalatorieInAsteptareBuilder setLocuriDisponibile(int locuriDisponibile) {
        this.calatorie.setLocuriDisponibile(locuriDisponibile);
        return this;
    }


    public CalatorieInAsteptareBuilder setMarcaMasina(String marcaMasina) {
        this.calatorie.setMarcaMasina(marcaMasina);
        return this;
    }

    public CalatorieInAsteptareBuilder setModelMasina(String modelMasina) {
        this.calatorie.setModelMasina(modelMasina);
        return this;
    }

    public CalatorieInAsteptareBuilder setAnFabricatie(int anFabricatie) {
        this.calatorie.setAnFabricatie(anFabricatie);
        return this;
    }

    public CalatorieInAsteptareBuilder setExperientaAuto(String experientaAuto) {
        this.calatorie.setExperientaAuto(experientaAuto);
        return this;
    }

    public CalatorieInAsteptareBuilder setNivelConfort(String nivelConfort) {
        this.calatorie.setNivelConfort(nivelConfort);
        return this;
    }

    public CalatorieInAsteptareBuilder setMarimeBagaj(String marimeBagaj) {
        this.calatorie.setMarimeBagaj(marimeBagaj);
        return this;
    }

    public CalatorieInAsteptareBuilder setDurataCalatorie(String durataCalatorie) {
        this.calatorie.setDurataCalatorie(durataCalatorie);
        return this;
    }

    public CalatorieInAsteptareBuilder setDistantaCalatorie(String distantaCalatorie) {
        this.calatorie.setDistantaCalatorie(distantaCalatorie);
        return this;
    }

    public CalatorieInAsteptareBuilder setDataCerere(String dataCerere){
        this.calatorie.setDataCerere(dataCerere);
        return this;
    }

    public CalatorieInAsteptareBuilder setNume(String nume){
        this.calatorie.setNume(nume);
        return this;
    }

    public CalatorieInAsteptareBuilder setEmail(String email){
        this.calatorie.setEmail(email);
        return this;
    }

    public CalatorieInAsteptareBuilder setTelefon(String telefon){
        this.calatorie.setTelefon(telefon);
        return this;
    }

    public CalatorieInAsteptareBuilder setVarsta(String varsta){
        this.calatorie.setVarsta(varsta);
        return this;
    }

    public CalatorieInAsteptareBuilder setIdUtilizator(int id){
        this.calatorie.setIdUtilizator(id);
        return this;
    }

    @Override
    public CalatorieInAsteptare build() {
        return calatorie;
    }
}
