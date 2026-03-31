package com.pao.laboratory06.exercise3;

import java.util.ArrayList;
import java.util.List;

public class PersoanaJuridica extends Persoana implements PlataOnlineSMS {
    private List<String> smsTrimise;
    private String username;
    private String parola;
    private double sold;

    public PersoanaJuridica() {
        super();
        this.smsTrimise = new ArrayList<>();
        this.sold = 0;
    }

    public PersoanaJuridica(String nume, String prenume, String telefon,
                            String username, String parola, double sold) {
        super(nume, prenume, telefon);
        this.smsTrimise = new ArrayList<>();
        this.username = username;
        this.parola = parola;
        this.sold = sold;
    }

    public List<String> getSmsTrimise() {
        return smsTrimise;
    }

    public String getUsername() {
        return username;
    }

    public String getParola() {
        return parola;
    }

    public double getSoldIntern() {
        return sold;
    }

    @Override
    public void autentificare(String user, String parola) {
        if (user == null || user.trim().isEmpty() || parola == null || parola.trim().isEmpty()) {
            throw new IllegalArgumentException("User sau parola nu pot fi null/goale.");
        }

        if (!user.equals(this.username) || !parola.equals(this.parola)) {
            throw new IllegalArgumentException("Credențiale invalide.");
        }

        System.out.println("Autentificare reușită pentru persoana juridică " + getNume());
    }

    @Override
    public double consultareSold() {
        return sold;
    }

    @Override
    public boolean efectuarePlata(double suma) {
        if (suma <= 0) {
            throw new IllegalArgumentException("Suma trebuie să fie pozitivă.");
        }

        if (suma > sold) {
            return false;
        }

        sold -= suma;
        return true;
    }

    @Override
    public boolean trimiteSMS(String mesaj) {
        if (mesaj == null || mesaj.trim().isEmpty()) {
            return false;
        }

        if (!areTelefonValid()) {
            return false;
        }

        smsTrimise.add(mesaj);
        return true;
    }

    @Override
    public String toString() {
        return "PersoanaJuridica{" +
                "nume='" + getNume() + '\'' +
                ", prenume='" + getPrenume() + '\'' +
                ", telefon='" + getTelefon() + '\'' +
                ", sold=" + sold +
                ", smsTrimise=" + smsTrimise +
                '}';
    }
}