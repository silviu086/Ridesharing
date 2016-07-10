package com.example.silviu086.licenta;

/**
 * Created by Silviu086 on 24.03.2016.
 */
public class AccountBuilder implements IBuilder{

    private Account account;

    public AccountBuilder() {
        this.account = new Account();
    }

    public AccountBuilder setId(int id){
        this.account.setId(id);
        return this;
    }

    public AccountBuilder setEmail(String email) {
        account.setEmail(email);
        return this;
    }

    public AccountBuilder setParola(String parola) {
        this.account.setParola(parola);
        return this;
    }

    public AccountBuilder setDateCreated(String date){
        this.account.setDateCreated(date);
        return this;
    }

    public AccountBuilder setNume(String nume) {
        this.account.setNume(nume);
        return this;
    }

    public AccountBuilder setTelefon(String telefon) {
        this.account.setTelefon(telefon);
        return this;
    }

    public AccountBuilder setVarsta(int varsta) {
        this.account.setVarsta(varsta);
        return this;
    }

    public AccountBuilder setMarcaMasina(String marcaMasina) {
        this.account.setMarcaMasina(marcaMasina);
        return this;
    }

    public AccountBuilder setModelMasina(String modelMasina) {
        this.account.setModelMasina(modelMasina);
        return this;
    }

    public AccountBuilder setAnFabricatie(int an) {
        this.account.setAnFabricatie(an);
        return this;
    }

    public AccountBuilder setExperientaAuto(String experientaAuto) {
        this.account.setExperientaAuto(experientaAuto);
        return this;
    }

    public AccountBuilder haveProfilPhoto(boolean aBoolean) {
        this.account.setHaveProfilPhoto(aBoolean);
        return this;
    }

    @Override
    public Account build() {
        return account;
    }
}
