package com.example.silviu086.licenta;

/**
 * Created by Silviu086 on 02.06.2016.
 */
public class HolderPasageri {
    String id;
    String email;
    String nume;
    String data;

    public HolderPasageri(String id, String email, String nume, String data) {
        this.id = id;
        this.email = email;
        this.nume = nume;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNume() {
        return nume;
    }

    public String getData() {
        return data;
    }
}
