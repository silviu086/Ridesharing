package com.example.silviu086.licenta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Silviu086 on 02.06.2016.
 */
public class CalatoriiHolder {
    public List<CalatorieAdaugata> calatoriiAdaugate;
    public List<CalatorieInAsteptare> calatoriiInAsteptare;
    public List<CalatorieConfirmata> calatoriiConfirmate;

    public CalatoriiHolder(){
        calatoriiAdaugate = new ArrayList<>();
        calatoriiInAsteptare = new ArrayList<>();
        calatoriiConfirmate = new ArrayList<>();
    }
}
