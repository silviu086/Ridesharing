package com.example.silviu086.licenta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Silviu086 on 08.07.2016.
 */
public class MesajeTopic implements Serializable {
    private int id;
    private int idCalatorie;
    private int idExpeditor;
    private int idDestinatar;
    private String numeExpeditor;
    private String numeDestinatar;
    private List<Mesaj> listaMesaje;

    public MesajeTopic(int id, int idCalatorie, int idExpeditor, int idDestinatar, String numeExpeditor, String numeDestinatar) {
        this.id = id;
        this.idCalatorie = idCalatorie;
        this.idExpeditor = idExpeditor;
        this.idDestinatar = idDestinatar;
        this.numeExpeditor = numeExpeditor;
        this.numeDestinatar = numeDestinatar;
        this.listaMesaje = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public int getIdCalatorie() {
        return idCalatorie;
    }

    public int getIdExpeditor() {
        return idExpeditor;
    }

    public int getIdDestinatar() {
        return idDestinatar;
    }

    public List<Mesaj> getListaMesaje() {
        return listaMesaje;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdCalatorie(int idCalatorie) {
        this.idCalatorie = idCalatorie;
    }

    public void setIdExpeditor(int idExpeditor) {
        this.idExpeditor = idExpeditor;
    }

    public void setIdDestinatar(int idDestinatar) {
        this.idDestinatar = idDestinatar;
    }

    public void addMesaj(Mesaj j){
        listaMesaje.add(j);
    }

    public String getNumeExpeditor() {
        return numeExpeditor;
    }

    public String getNumeDestinatar() {
        return numeDestinatar;
    }

    public void setNumeExpeditor(String numeExpeditor) {
        this.numeExpeditor = numeExpeditor;
    }

    public void setNumeDestinatar(String numeDestinatar) {
        this.numeDestinatar = numeDestinatar;
    }
}
